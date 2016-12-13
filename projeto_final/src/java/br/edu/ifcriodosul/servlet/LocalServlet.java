/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifcriodosul.servlet;

import br.edu.ifcriodosul.arduino.Arduino;
import br.edu.ifcriodosul.conceitual.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class LocalServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private EntityManager em = null;
    private boolean jaIniciou = false;
    
    Arduino ard = new Arduino();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
        em = emf.createEntityManager();
        try {

            String acao = "listar";
            String destino = "localizacao_list.jsp";

            //testa se existe a acao
            if (request.getParameter("acao") != null) {
                acao = request.getParameter("acao");
            }
            if (acao.equalsIgnoreCase("listar")) {
                destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("inserir")) {
                destino = inserir(request, response);
                destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("alterar")) {
                destino = alterar(request, response);
                destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("remover")) {
                destino = remover(request, response);
                destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("selecionar")) {
                destino = selecionar(request, response);
            } else if (acao.equalsIgnoreCase("cancelar")) {
                destino = listar(request, response);
            }
            request.getRequestDispatcher(destino).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";
        //JPAQL
        List<Localizacao> locais = em.createQuery("FROM Localizacao").getResultList();
         int quant = locais.size();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("locais", locais);
        request.setAttribute("tamanhoLista" , quant);  
        saida = "localizacao_list.jsp";
        
        if (jaIniciou == false) {
            ard.initialize();
            jaIniciou = true;
        }

        return saida;

    }
    
   
    protected String selecionar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Localizacao l = em.find(Localizacao.class, id);

        //repassar para pagina (com Dispatcher)
        request.setAttribute("localizacao", l);
        saida = "localizacao.jsp";
        return saida;

    }

    protected String inserir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "localizacao_list.jsp";

        //criar objeto
        Localizacao l = new Localizacao();

        //preencher objeto
        l.setIdArduino(request.getParameter("idArduino"));
        l.setLatitude(request.getParameter("latitude"));
        l.setLongitude(request.getParameter("longitude"));

        //salvar
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(l);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return saida;

    }

    protected String alterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "localizacao_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Localizacao l = em.find(Localizacao.class, id);

        //preencher objeto
        l.setIdArduino(request.getParameter("idArduino"));
        l.setLatitude(request.getParameter("latitude"));
        l.setLongitude(request.getParameter("longitude"));

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(l);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        return saida;

    }

    protected String remover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "localizacao_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Localizacao l = em.find(Localizacao.class, id);

        int valida = 0;

        Query q = em.createQuery("FROM Usuario WHERE localizacao_id = :id");
        q.setParameter("id", id);
        valida = q.getResultList().size();
        
        EntityTransaction tx = em.getTransaction();

        if (valida == 0) {            
            try {
                tx.begin();
                em.remove(l);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            }
        } else {
            request.setAttribute("erro", "1");
            saida = "localizacao_list.jsp?erro=1";
        }
        saida = "localizacao_list.jsp?erro=1";

        return saida;

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
