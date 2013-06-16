/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Article;
import entities.Favourites;
import entities.UserRating;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
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
 *
 */
@WebServlet(name = "ProfilePage", urlPatterns = {"/ProfilePage"})
public class ProfilePage extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;
    @EJB
    private StatefulBeanRemote statefulb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(true);
        Users AnotherUser = null;
        boolean canRate = false;
        
        String anotherUserId = request.getParameter("anotherUserId");
        Users user=null;
        if(session.getAttribute("loggedUser")!=null){
        user= (Users) session.getAttribute("loggedUser");
        }
        if (anotherUserId != null) {
            Long AnotherUserId = Long.parseLong(anotherUserId);
            AnotherUser = statelessb.getUserById(AnotherUserId);
            List<Favourites> otherFav = statelessb.getFavourites(AnotherUser);
            session.setAttribute("favList", otherFav);
            session.setAttribute("userToDisplay", AnotherUser);                       
            UserRating check = statelessb.checkRating(AnotherUser.getId(), user.getId());
            if (check == null) {
                canRate = true;
            }else{
                canRate=false;
                
            }
            request.setAttribute("canRate", canRate);
            out.println(request.getParameter("canRate"));
        } 
        else {
            
            List<Favourites> fav = statelessb.getFavourites(user);
            session.setAttribute("userToDisplay", user);
            session.setAttribute("favList", fav);
           
        }
        RequestDispatcher view = request.getRequestDispatcher("profile.jsp");
        view.forward(request, response);
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
