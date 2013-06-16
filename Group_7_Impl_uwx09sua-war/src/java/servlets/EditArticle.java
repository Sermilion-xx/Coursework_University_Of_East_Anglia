/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.StatelessBeanRemote;
import entities.Article;
import entities.History;
import entities.Tags;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
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
 * @author Sermilion
 */
@WebServlet(name = "EditArticle", urlPatterns = {"/EditArticle"})
public class EditArticle extends HttpServlet {

    @EJB
    private StatelessBeanRemote statelessb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Date date = new Date();
            HttpSession session = request.getSession(true);
            Users user = (Users) session.getAttribute("loggedUser");

            String editname = request.getParameter("edit_article_name");
            String editcontent = request.getParameter("edit_article_content");

            String subtag = request.getParameter("e_sub_tag");

            Article article = (Article) session.getAttribute("ArticleToDisplay");
            Article aaa = article;
//------------------------------History--------------------------------//            
            History hist = new History();
            hist.setArticle(article);
            hist.setUser(user);
            Timestamp time = new Timestamp(date.getTime());
            hist.setDate(time);
            hist.setContent(article.getContent());
            String type = null;

            if (editname != null && !editname.equals("") && !editname.equals(article.getName())) {
                type += "Name/";
            }
            String a = editcontent.trim();
            String b = article.getContent().trim();
            String content = article.getContent();
            if (editcontent != null && !editcontent.equals("") && !editcontent.trim().equals(article.getContent().trim())) {
                type += "Content/";
            }
            if (((subtag != null && !subtag.equals("") && !subtag.equals("-1")))) {
                type += "Tag/";
            }
            hist.setType(type);
            
//---------------------------------------------------------------------// 
            Tags superTag = null;
            Tags subsuperTag = null;
            Tags subTag = null;
            if (editname != null && !editname.equals("")) {
                article.setName(editname);
            }
            if (editcontent != null && !editcontent.equals("")) {
                article.setContent(editcontent);
            }
            if ((editname != null && !editname.equals("")) || editcontent != null && !editcontent.equals("")) {
                Timestamp dlm = new Timestamp(date.getTime());
                article.setDate_modified(dlm);
            }

            Tags superta=null;
            Tags subsupertags=null;
            if (subtag != null && !subtag.equals("") && !subtag.equals("-1")) {
                Long subtag_id = Long.parseLong(subtag);
                subTag = statelessb.getTagsDB(subtag_id, new Long(2));
                
                subsupertags = subTag.getParentTag();
                superta=subTag.getParentTag2();
                article.setTag(subTag);
                article.setParentTag(subsupertags);
                article.setParentTag2(superta);
            }
//            out.println(article.getSubTag());
//            out.println(article.getParentTag());
//            out.println(article.getParentTag2());
//----------------------------------Observer Pattern Code----------------------------------------// 
            Article updatedArticle = article;
if(type!=null){
    statelessb.archiveArticle(hist);
}
                updatedArticle = statelessb.fireEditArticleEvent(article, statelessb, user);
                updatedArticle.setIsFavourite(article.isFavourite());
                session.setAttribute("ArticleToDisplay", updatedArticle);
                RequestDispatcher view = request.getRequestDispatcher("article.jsp");
                view.forward(request, response);

//----------------------------------Observer Pattern Code----------------------------------------//


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
