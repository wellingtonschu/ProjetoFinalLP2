/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifcriodosul.servlet;

import br.edu.ifcriodosul.arduino.arduino;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class LoginServlet extends HttpServlet {

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
    //arduino inp = new arduino(); // INSTANCIANDO CLASSE ENTRADA

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
        em = emf.createEntityManager();
        try {

            String acao = "";
            String destino = "login.jsp";

            //testa se existe a acao
            if (request.getParameter("acao") != null) {
                acao = request.getParameter("acao");
            }
            if (acao.equalsIgnoreCase("logar")) {
                destino = login(request, response);
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
            }
            request.getRequestDispatcher(destino).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        String saida = "login.jsp";

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        int loginNum = 0;

        Query q = em.createQuery("FROM Usuario WHERE login = :login AND senha = :senha");
        q.setParameter("login", login);
        q.setParameter("senha", senha);
        loginNum = q.getResultList().size();

        if (loginNum >= 1) {
            saida = "index.jsp";
            session.setAttribute("logado", login);
            /*if(jaIniciou == false){
            inp.initialize();
            jaIniciou = true;
            }
            inp.enviarDados("0"); */
            
            
        } else {
            //por mensagem de erro
            saida = "login.jsp?erro=1";
        }

        return saida;

    }

    protected String listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";
        //JPAQL
        List<Usuario> usuarios = em.createQuery("from Usuario").getResultList();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("usuarios", usuarios);
        saida = "usuario_list.jsp";

        return saida;

    }

    protected String selecionar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Usuario u = em.find(Usuario.class, id);

        //repassar para pagina (com Dispatcher)
        request.setAttribute("usuario", u);
        saida = "usuario_list.jsp";
        return saida;

    }

    protected String inserir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "usuario_list.jsp";
        //criar objeto
        Usuario u = new Usuario();
        //preencher objeto

        /*u.setDescricao(request.getParameter("descricao"));
        u.setNome(request.getParameter("nome"));
        u.setPrecoCompra(Float.parseFloat(request.getParameter("precoCompra")));
        u.setPrecoVenda(Float.parseFloat(request.getParameter("precoVenda")));
        u.setQtdMinima(Integer.parseInt(request.getParameter("qtdMinima")));
        u.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
         */
        //salvar
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return saida;

    }

    protected String alterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "usuario_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Usuario u = em.find(Usuario.class, id);

        //preencher objeto
        /*
        p.setDescricao(request.getParameter("descricao"));
        p.setNome(request.getParameter("nome"));
        p.setPrecoCompra(Float.parseFloat(request.getParameter("precoCompra")));
        p.setPrecoVenda(Float.parseFloat(request.getParameter("precoVenda")));
        p.setQtdMinima(Integer.parseInt(request.getParameter("qtdMinima")));
        p.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
         */
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(u);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        return saida;

    }

    protected String remover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "usuario_list.jsp";
        //criar objeto

        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        Usuario u = em.find(Usuario.class, id);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(u);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

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
