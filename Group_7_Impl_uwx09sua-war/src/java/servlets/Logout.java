package servlets;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import ejb.StatefulBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Scat
 */
@WebServlet(name = "Logout", urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {

    @EJB(name = "StatefulBeanRemote", beanInterface = StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        InitialContext context = null;
        try {
            request.getSession().invalidate();
            HttpSession session = request.getSession(true);
            context = new InitialContext();
            statefulb = (StatefulBeanRemote) context.lookup("java:comp/env/StatefulBeanRemote");
            
           session.setAttribute("guest", statefulb);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
                view.forward(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, null, ex);
        }





    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

    }
}