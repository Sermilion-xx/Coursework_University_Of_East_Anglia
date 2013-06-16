/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Favourites;
import entities.UserRating;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
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
@WebServlet(name = "RateUser", urlPatterns = {"/RateUser"})
public class RateUser extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;
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
            throws ServletException, IOException {
        // boolean rated = false;
        HttpSession session = request.getSession();
        Users loggedUser = (Users) session.getAttribute("loggedUser");

        String ratingString = request.getParameter("rating");
        String userId = request.getParameter("rated_user");
        String canRate = request.getParameter("canRate");

        long id = Long.parseLong(userId);
        Users user = statelessb.getUserById(id);
        
        if (ratingString != null && !ratingString.equals("Please select rating")) {

            int rating = Integer.parseInt(ratingString);




            UserRating check = statelessb.checkRating(user.getId(), loggedUser.getId());
            if (check == null) {
                statelessb.updateUserRating(rating, user);

                UserRating ur = new UserRating(id, loggedUser.getId());

                statelessb.addToUserRating(ur);
            }



        }


        List<Favourites> fav = statelessb.getFavourites(user);




        boolean f = false;
        session.setAttribute("favList", fav);

        request.setAttribute("user", user);

        request.setAttribute("loggedUser", loggedUser);
        request.setAttribute("canRate", f);

        response.setHeader("Refresh", "2");
        request.getRequestDispatcher("profile.jsp").forward(request, response);



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
        processRequest(request, response);
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
        processRequest(request, response);
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
