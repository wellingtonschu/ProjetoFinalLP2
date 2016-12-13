/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifcriodosul.conceitual;

import br.edu.ifcriodosul.servlet.DataUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex Manoel Coelho <alexma_coelho@hotmail.com>
 */
public class PegaUltimoServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
        em = emf.createEntityManager();
        HttpSession session = request.getSession();
        try {
            List<Log> leituraL = em.createQuery("FROM Log ORDER BY id desc LIMIT 1").getResultList();
            session.setAttribute("pegaUltimos10", leituraL);
            
                out.print("<tr style='text-align: center'><td>" + leituraL.get(0).getLocalizacao().getIdArduino()+ "</td>"
                    + "<td>" + DataUtil.DiaMesAnoStringBarra(leituraL.get(0).getData()) + "</td>" 
                    + "<td>" + DataUtil.DateToStrHoraMinuto(leituraL.get(0).getHorario()) + "</td>"
                    + "<td>" + leituraL.get(0).getSituacao().getSituacao() + "</td>"
                    + "</tr>");
            
       
        } finally {
            out.close();
        }
    }
    
    protected String listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";
        //JPAQL
        List<Log> leituraL = em.createQuery("FROM Log ORDER BY id desc LIMIT 1").getResultList();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("ultimaLeitura", leituraL);
        saida = "index.jsp";

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
