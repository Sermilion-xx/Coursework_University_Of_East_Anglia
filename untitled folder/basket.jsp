<%-- 
    Document   : basket
    Created on : Mar 8, 2013, 11:56:32 AM
    Author     : azamatibragimov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ejb.*"%>
<%@page import="javax.naming.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Basket</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% BasketRemote basket = (BasketRemote) session.getAttribute("basket"); %>
        <h1>Basket View</h1>
        <% for (int i=0; i<basket.getBasket().size(); i++) { %>
        <% out.print((i+1) + ". " + basket.getBasket().get(i).getSubject() + " by " + 
                basket.getBasket().get(i).getAuthor() +
                " <form action=\"RemoveArticle\" method=\"post\">" +
                "<input type=\"submit\" value=\"Remove\">" +
                "<input type=\"text\" type=\"hidden\" name=\"remove\" value=\"" + i + "\">" +
                "</form>"
                
        ); %> 
        <% } %>
        <a href="anotherStatefulShipView.jsp">Generate and Download a PDF e-book</a>
    </body>
</html>
