/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import entities.Article;
import entities.Favourites;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "AddToFavourites", urlPatterns = {"/AddToFavourites"})
public class AddToFavourites extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;
    


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            Long favid = Long.parseLong(request.getParameter("fav_id"));
            Users user = (Users) session.getAttribute("loggedUser");
            Article article = statelessb.getArticleById(favid);
            Favourites fav = new Favourites();
            //Article article = new Article();
            fav.setFavArticle(article);
            fav.setUser(user);
            if (statelessb.addToFavourites(fav)) {
                article.setIsFavourite(true);
                session.setAttribute("ArticleToDisplay",article);
                request.setAttribute("FavArticle", article);
                List<Favourites> favs = statelessb.getFavourites(user);
                session.setAttribute("favList",favs);
                RequestDispatcher view = request.getRequestDispatcher("article.jsp");
                view.forward(request, response);
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
