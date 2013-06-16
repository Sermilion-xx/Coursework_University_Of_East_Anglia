/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import entities.Article;
import entities.Comment;
import entities.Favourites;
import entities.Tags;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Orkhan Yusubov 4852419
 */

@WebServlet(name = "AddComment", urlPatterns = {"/AddComment"})
public class AddComment extends HttpServlet {
    @EJB
    private StatelessBeanRemote statelessb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String comment = request.getParameter("commentText");
            String articleIDstr = request.getParameter("articleID");
            String userIDstr= request.getParameter("userID");
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
 
            out.println(articleIDstr);
            if(articleIDstr!=null && articleIDstr.length()>0 && comment.length()>0 && !articleIDstr.equals("") && comment!=null && !comment.equals("")){
            Integer articleID = Integer.parseInt(articleIDstr);
            Integer userID = Integer.parseInt(userIDstr);
            
            Article articleObj = null;
            Users user = null;
          if (articleID!=null && userID!=null) {
                articleObj = statelessb.getArticleByID(articleID);
                user = statelessb.getUserByID(userID);
            }
            if (articleObj == null || user ==null) {
                request.setAttribute("noarticle", true);
                //out.println("Error");
            } else {           
                    Comment cmnt = new Comment();
                        cmnt.setArticleID(articleObj);
                        cmnt.setUserID(user);
                        cmnt.setContent(comment);
                        cmnt.setDate(sqlDate);
                        Long id =null;
                        id = statelessb.AddComment(cmnt);  
                        
          if (id != null) {

                        request.setAttribute("user", cmnt);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    } else {
                        request.setAttribute("failComment", true);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    }
           
            }
            } else {
                        request.setAttribute("failComment", true);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    } 
            
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
