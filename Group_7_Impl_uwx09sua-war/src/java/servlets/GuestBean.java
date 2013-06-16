/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Article;
import entities.Tags;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sermilion
 */
@WebServlet(name = "index.jsp", urlPatterns = {"/index.jsp"})
public class GuestBean extends HttpServlet {

@EJB(name="StatefulBeanRemote",beanInterface=StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;
@EJB
StatelessBeanRemote statelessb = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           
        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
           HttpSession session = request.getSession(true); 
           
           if(session.getAttribute("guest")==null){ 
           final  InitialContext context = new InitialContext();
           statefulb = (StatefulBeanRemote)context.lookup("java:comp/env/StatefulBeanRemote");
           session.setAttribute("guest", statefulb);
           }
           RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");          
           view.forward(request, response);
          
            
        } catch (NamingException ex) {
            Logger.getLogger(GuestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(GuestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
