/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import entities.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Orkhan Yusubov 4852419
 */
@WebServlet(name = "DeleteComment", urlPatterns = {"/DeleteComment"})
public class DeleteComment extends HttpServlet {
    @EJB
    private StatelessBeanRemote statelessb;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            
            String cID = request.getParameter("commentIdDel");
            int idComment = Integer.parseInt(cID);
             Comment comment =null;
             if(cID!=null){
                   comment = statelessb.getCommentByID(idComment); 
             }
             if(comment!=null){   
                 boolean checkDelete=false;
                 checkDelete =statelessb.removeComment(comment);  
                                   
            if (checkDelete) {
                        request.setAttribute("user", comment);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    } else {
                        request.setAttribute("failComment", true);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    }
             }
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteComment</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteComment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
