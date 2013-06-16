/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import ejb.StatefulBeanRemote;
import entities.Article;
import entities.Tags;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
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
 * @author Sermilion
 */
@WebServlet(name = "LoadArticlesByTag", urlPatterns = {"/LoadArticlesByTag"})
public class LoadArticlesByTag extends HttpServlet {

    @EJB(name = "StatefulBeanRemote", beanInterface = StatefulBeanRemote.class)
    private StatefulBeanRemote statefulb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            response.setHeader("Access-Control-Allow-Origin", "*");
            final InitialContext context = new InitialContext();
            statefulb = (StatefulBeanRemote) context.lookup("java:comp/env/StatefulBeanRemote");

            List<Article> articles = null;
            


            Long tag = Long.parseLong(request.getParameter("b_tag"));
            Long tag_type = Long.parseLong(request.getParameter("b_tag_type"));
ArrayList list = new ArrayList();
            articles = statefulb.getArticleByTag(tag,tag_type);

            Long id=null;
            String content=null;
            Timestamp doc=null;
            Timestamp dlm=null;
            Boolean hidden=false;
            Boolean locked = false;
            Boolean isFavourite=false;
            String name=null;
            Double rating =0.0;
            Boolean updated=false;
            Users author=null;
            Tags parent_tag=null;
            Tags parent_tag2=null;
            Tags sub_tag=null;
            
            String allArticles="";
            for(int i=0;i<articles.size();i++){
                String art="";
                art+=articles.get(i).getId()+"/";
                art+=articles.get(i).getContent()+"/";
                art+=articles.get(i).getDate_created()+"/";
                art+=articles.get(i).getDate_modified()+"/";
                art+=articles.get(i).isHidden()+"/";
                art+=articles.get(i).isLocked()+"/";
                art+=articles.get(i).isFavourite()+"/";
                art+=articles.get(i).getName()+"/";
                art+=articles.get(i).getRating()+"/";
                art+=articles.get(i).getUpdated()+"/";
                art+=articles.get(i).getAuthor().getName()+"/";
                art+=articles.get(i).getSubTag().getName()+"/";
                allArticles+=art+"#";
            }
             

//            Gson gson = new Gson();
//            String json = gson.toJson(articles);

            HttpSession session = request.getSession(true);
            session.setAttribute("json_articles", articles);
//out.println(articles);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(allArticles);

        } catch (NamingException ex) {
            Logger.getLogger(LoadArticlesByTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {



            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(LoadArticlesByTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
