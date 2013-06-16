/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatefulBeanRemote;
import ejb.StatelessBeanRemote;
import entities.Tags;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "CreateTag", urlPatterns = {"/CreateTag"})
public class CreateTag extends HttpServlet {
   
    @EJB
    private StatelessBeanRemote statelessb;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
   HttpSession session = request.getSession(true);
           
           String name = request.getParameter("t_tag_name");
           String desc = request.getParameter("t_tag_desc");           
           String tagtype= request.getParameter("t_tag_type");
           String Subsup = request.getParameter("t_subsuper_tag"); 
           Long super_tag_param = Long.parseLong(request.getParameter("t_super_tag"));
           
           Long subsuper_tag_param=null;
           if(!Subsup.equals("Select Next Category")) {
                subsuper_tag_param=Long.parseLong(Subsup);
            }
           
           int tag_type = -1;
           Tags supertag=null;
           Tags subsupertags=null;
           Tags  tag = new Tags();
           
           tag.setDescription(desc);
           tag.setName(name);
           
           if(tagtype.equals("Supertag")){ 
               tag.setType(0);    
           }
           else if(tagtype.equals("Subsupertag")){
               tag.setType(1);
               supertag = statelessb.getTagsDB(super_tag_param,new Long(0));
           }else if(tagtype.equals("Subtag")){
               tag.setType(2);
               supertag = statelessb.getTagsDB(super_tag_param,new Long(0));
               subsupertags = statelessb.getTagsDB(subsuper_tag_param,new Long(1));
           }
                      
           if(tag.getType()==1){             

              tag.setParentTag2(supertag);
           }else if(tag.getType()==2){
              tag.setParentTag(subsupertags);
              tag.setParentTag2(supertag);
           }
//           out.println(tagtype);
//           out.println(tag.getType());
//           out.println(tag.getParentTag().getName());
//           out.println(tag.getParentTag2().getName());
//           
//           out.println(super_tag_param);
//           out.println(subsuper_tag_param);
           boolean newtag = statelessb.createTag(tag);
           if(newtag==true){
                session.setAttribute("createTag",null);
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(CreateTag.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(CreateTag.class.getName()).log(Level.SEVERE, null, ex);
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
