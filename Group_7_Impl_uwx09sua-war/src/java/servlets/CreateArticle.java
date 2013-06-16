/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import ejb.StatefulBeanRemote;
import entities.Article;
import entities.Tags;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
/**
 *
 * @author azamatibragimov
 */
@WebServlet(name = "CreateArticle", urlPatterns = {"/CreateArticle"})
public class CreateArticle extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Users user = null;
            String email =null;
            HttpSession session = request.getSession(true);
            if (session.getAttribute("emailLogged") != null) {
                email = (String) session.getAttribute("emailLogged");
            }
            StatefulBeanRemote statefulb = (StatefulBeanRemote)session.getAttribute("user");
            user = statefulb.getUser(email);
            Date date = new Date();
            String name = request.getParameter("article_name");
            String content = request.getParameter("article_content");
            
            
            Long subtag = Long.parseLong(request.getParameter("a_sub_tag"));
            Long subsupertag = Long.parseLong(request.getParameter("a_subsuper_tag"));
            Long supertag = Long.parseLong(request.getParameter("a_super_tag"));
            
            Timestamp doc = new Timestamp(date.getTime());
            Tags subtagDB = null;
            Tags supertagDB = null;
            Tags subsupertagDB = null;

                out.println(request.getParameter("a_super_tag")); 
            if (subtag!=null && subsupertag!=null && supertag!=null) {
                subtagDB = statelessb.getTagsDB(subtag, new Long(2));
                supertagDB = statelessb.getTagsDB(supertag, new Long(0));
                subsupertagDB = statelessb.getTagsDB(subsupertag, new Long(1));
            }
            if (subtagDB == null) {
                request.setAttribute("notag", true);
                //out.println("Error");
            } else {



                Article article = new Article();
                article.setName(name);
                article.setContent(content);
                article.setTag(subtagDB);
                article.setParentTag(subsupertagDB);
                article.setParentTag2(supertagDB);
                article.setDate_created(doc);
                article.setAuthor(user);
                Long id = null;

                
                
                
                    id = statelessb.createArticle(article);
                    if (id != null) {

                        request.setAttribute("user", article);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    } else {
                        request.setAttribute("failArticle", true);
                        request.getRequestDispatcher("article.jsp").forward(request, response);
                    }
                
            }
        } catch (Exception e) {
            out.println("Error: " + e);
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
