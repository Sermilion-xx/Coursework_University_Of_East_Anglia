/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import ejb.BasketRemote;
import ejb.StatelessBeanRemote;
import entities.Article;
import entities.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "GenerateAndDownload", urlPatterns = {"/GenerateAndDownload"})
public class GenerateAndDownload extends HttpServlet {
    @EJB(name="BasketRemote",beanInterface=BasketRemote.class)
    private BasketRemote basket;

    @EJB
    private StatelessBeanRemote statelessb;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doPost(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("application/pdf");
            Document document = new Document();
            HttpSession session = request.getSession(true);
            if(request.getSession().getAttribute("basket")!=null) {
                BasketRemote basket = (BasketRemote) session.getAttribute("basket");
            try {
                
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                
                
                ArrayList<Article> basketItems = basket.getBasket();
                    
                for(int i=0; i<basketItems.size(); i++) {
                    document.add(new Paragraph(basketItems.get(i).getName()));
                    document.add(new Paragraph(basketItems.get(i).getDate_created().toString()));
                    document.add(new Paragraph("by " + basketItems.get(i).getAuthor().getName()));
                    document.add(new Paragraph(basketItems.get(i).getContent()));
                    if(i<(basketItems.size()-1)) document.newPage();
                }
                session.setAttribute("basket", null);  
                
                document.close();
            }
            catch (DocumentException e) {
                e.printStackTrace();
            }
            }
        
    }

    
}
