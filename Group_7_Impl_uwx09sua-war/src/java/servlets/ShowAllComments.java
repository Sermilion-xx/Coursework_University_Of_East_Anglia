/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import ejb.StatefulBeanRemote;
import entities.Article;
import entities.Comment;
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
@WebServlet(name = "ShowAllComments", urlPatterns = {"/ShowAllComments"})
public class ShowAllComments extends HttpServlet {
    
    @EJB(name="StatefulBeanRemote",beanInterface=StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try { 
            
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
        try {
            
            final InitialContext context = new InitialContext();
            statefulb = (StatefulBeanRemote) context.lookup("java:comp/env/StatefulBeanRemote");

            List<Comment> comments = null;
             String article_id = request.getParameter("articleID");
            int id = Integer.parseInt(article_id);
           comments = statefulb.getCommentsByID(id);
           Gson gson = new Gson();
           String json = gson.toJson(comments);
             
            HttpSession session = request.getSession(true);
            session.setAttribute("json_articles", comments);            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json); 
            
        } catch (NamingException ex) {
            Logger.getLogger(LoadArticlesByTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
           
             
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(LoadArticlesByTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
