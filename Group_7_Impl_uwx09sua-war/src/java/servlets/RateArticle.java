/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Article;
import entities.ArticleRating;
import entities.Favourites;
import entities.UserRating;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author DinaraBerdysheva
 */
@WebServlet(name = "RateArticle", urlPatterns = {"/RateArticle"})
public class RateArticle extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;
    
    @EJB(name="StatefulBeanRemote",beanInterface=StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;
    //  @EJB
    // private StatefulBeanRemote statefulb;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        // boolean rated = false;
        final  InitialContext context = new InitialContext();
        statefulb = (StatefulBeanRemote)context.lookup("java:comp/env/StatefulBeanRemote");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Long userid = Long.parseLong(request.getParameter("usera"));
        Long articleid = Long.parseLong(request.getParameter("articlea"));
        String rate = request.getParameter("ratea");
       
        ArticleRating ar = new ArticleRating();
        ar.setArticleId(articleid);
        ar.setUserId(userid);
        ar.setRated(rate);
        String artRate = statefulb.getArticleRatingByUser(userid, articleid);
        //ar.setRated(artRate);
        statelessb.addArticleRating(ar);
        int id = (int) (long) articleid;
        Article article = (Article) statelessb.getArticleByID(id);
        String rate1= rate.trim();


        if(rate1.equals("up")){
            if(artRate.equals("[down]")){
                ar.setRated("middle");
                statelessb.addArticleRating(ar);
            }
            article.setRating(article.getRating()+1);
            session.setAttribute("ArticleToDisplay", article);
        }
        if(rate1.equals("down")){
            if(artRate.equals("[up]")){
                ar.setRated("middle");
                statelessb.addArticleRating(ar);
            }
            article.setRating(article.getRating()-1);
            session.setAttribute("ArticleToDisplay", article);
        }
        
        statelessb.fireEditArticleEvent(article, statelessb, null);
        
        request.getRequestDispatcher("article.jsp").forward(request, response);



    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(RateArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
            Logger.getLogger(RateArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
