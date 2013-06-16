/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import ejb.StatelessBeanRemote;
import entities.Tags;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
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
@WebServlet(name = "LoadTags", urlPatterns = {"/LoadTags"})
public class LoadTags extends HttpServlet {
    
    @EJB
    private StatelessBeanRemote statelessb;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            HttpSession session = request.getSession(true); 
            Long id = Long.parseLong(request.getParameter("b_super_tag"));
            Long type = Long.parseLong(request.getParameter("type"));
            List<Tags> tags = statelessb.loadTags(id, type);
            session.setAttribute("loadedSubsuperTags", tags);
            String a="<option selected='-1'>Select Next Category</option><br/>";
            
            for(int i=0;i<tags.size();i++){
                a+="<option value='"+tags.get(i).getId()+"'>"+tags.get(i).getName()+"</option><br/>";                
            }
            
            
            
            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(a);  

        } finally {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
         
   
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
  
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
