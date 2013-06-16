/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.BasketRemote;
import ejb.StatelessBeanRemote;
import entities.Article;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author azamatibragimov
 */
@WebServlet(name = "RemoveFromBasket", urlPatterns = {"/RemoveFromBasket"})
public class RemoveFromBasket extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;
    
    @EJB(name="BasketRemote",beanInterface=BasketRemote.class)
    private BasketRemote basket;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            
            Long articleid = Long.parseLong(request.getParameter("ritem"));
            Article article = statelessb.getArticleById(articleid);
            
            BasketRemote basket = (BasketRemote) session.getAttribute("basket");
            basket.removeArticle(article);
            
            
            if(basket.getBasket().isEmpty()) session.setAttribute("basket", null);
            
            if(request.getParameter("url").equalsIgnoreCase("basket")) {
            request.setAttribute("url", null);
            request.getRequestDispatcher("basket.jsp").forward(request, response);
            }
            else {
                request.setAttribute("url", null);
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
