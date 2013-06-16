/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    @EJB
    private StatelessBeanRemote statelessb;
    
    @EJB(name="StatefulBeanRemote",beanInterface=StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;

   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {  
            HttpSession session = request.getSession(true);   
            Date date = new Date();
           final  InitialContext context = new InitialContext();
           statefulb = (StatefulBeanRemote)context.lookup("java:comp/env/StatefulBeanRemote");
            
           String email = request.getParameter("email");
            String password = request.getParameter("password");
            Timestamp logintime = new Timestamp(date.getTime());

            Users user = new Users();        
            user.setEmail(email);
            user.setPassword(password);
            user.setLastlogin(logintime);       
            
            boolean log;          
     if(!email.equals("") && !password.equals("")) {
                log = statelessb.login(user);

            } else {
                log=false;               
            }
            if(log==true){
                               
                String emailLogged = user.getEmail();                         
                session.setAttribute("user", statefulb);
                 session.setAttribute("stateless", statelessb);
                
                
                Users loggedUser = (Users)statefulb.getUser(emailLogged);
                session.setAttribute("emailLogged", emailLogged);
                session.setAttribute("loggedUser", loggedUser);
                RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
                view.forward(request, response);
            }else{
                request.setAttribute("login", false);
                RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
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
        try {
                    try {
                        processRequest(request, response);
                    } catch (NamingException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
                    try {
                        processRequest(request, response);
                    } catch (NamingException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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

