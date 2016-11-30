/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifcriodosul.servlet;

import br.edu.ifcriodosul.conceitual.Localizacao;
import br.edu.ifcriodosul.conceitual.Sensor;
import br.edu.ifcriodosul.conceitual.Usuario;
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


/**
 *
 * @author Alex
 */
public class SensorServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
        em = emf.createEntityManager();
        try {

            String acao = "listar";
            String destino = "sensor_list.jsp";

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
            } else if (acao.equalsIgnoreCase("novoRegistro")) {
                destino = novoRegistro(request, response);
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
        List<Sensor> sensores = em.createQuery("FROM Sensor").getResultList();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("sensores", sensores);
        saida = "sensor_list.jsp";

        return saida;

    }
    
    //FAZ QUE BUSQUE TODOS AS LOCALIZAÇÕES E COLOQUE NA LISTA localizacoes PARA QUE DEPOIS POSSA SER UTILIZADA NO COMBOBOX
    protected String novoRegistro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";

         List<Localizacao> locais = em.createQuery("FROM Localizacao").getResultList();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("locais", locais);
        
        saida = "sensor.jsp";
        return saida;
    }

    protected String selecionar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Sensor s = em.find(Sensor.class, id);

        //repassar para pagina (com Dispatcher)
        request.setAttribute("sensor", s);
        saida = "sensor_list.jsp";
        return saida;

    }

    protected String inserir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "sensor_list.jsp";

        //criar objeto
        Sensor s = new Sensor();
        Localizacao l = new Localizacao();
        
        l.setId(Long.parseLong(request.getParameter("localizacao")));

        //preencher objeto
        s.setLocalizacao(l);
        s.setNome(request.getParameter("nome"));

        //salvar
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(s);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return saida;

    }

    protected String alterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "sensor_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        
        Sensor s = em.find(Sensor.class, id);
        Localizacao l = new Localizacao();

        //preencher objeto
        s.setLocalizacao(l);
        s.setNome(request.getParameter("nome"));

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(s);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        return saida;

    }

    protected String remover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "sensor_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Sensor l = em.find(Sensor.class, id);

        int valida = 0;

        /*Query q = em.createQuery("FROM Usuario WHERE localizacao_id = :id");
        q.setParameter("id", id);
        valida = q.getResultList().size();*/
        
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
            saida = "sensor_list.jsp?erro=1";
        }
        saida = "sensor_list.jsp?erro=1";

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
