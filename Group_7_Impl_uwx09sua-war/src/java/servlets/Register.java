/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author azamatibragimov
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @EJB
    private StatelessBeanRemote db;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Long id = null;
            Users user = new Users();

            request.setAttribute("wrongpassword", "match");
            request.setAttribute("fulldata", "true");

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String repassword = request.getParameter("re-password");
            String dob = request.getParameter("dob");
            String type="user";
            Double rating =0.0;

            if (!name.equals("") && !email.equals("") && !password.equals("") && !repassword.equals("") && !dob.equals("")) {
                if (!password.equals(repassword)) {
                    request.setAttribute("wrongpassword", "nomatch");
                    RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
                    view.forward(request, response);
                } else {
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setDob(dob);
                    user.setRating(0.0);
                    user.setType("user");
                    id = db.registerUser(user);                   
                    request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
                    
                    if (id != null) {
                        request.setAttribute("regDone", true);
                    } else {
                        request.setAttribute("regDone", false);
                    }
                }
            }else{
                
                request.setAttribute("emptyData", true);
                request.getRequestDispatcher("registration.jsp").forward(request, response);
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
