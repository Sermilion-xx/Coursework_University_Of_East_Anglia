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
        
         <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
        <link href="layout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <script type="text/javascript" src="js/validations.js"></script>
    </head>
    <body>
        <div class="wrapper row1">
            <header id="header" class="clear">
                <a href="index.jsp"> 
                <hgroup>
                    <h1>Wiki Encyclopedia</h1>            
                </hgroup>
                    </a>
                <form id="searchForm" action="SearchArticle" method="post">
                    <input type="text" name="title">
                    <input type="submit" value="Search">
                </form>
                <nav>           
                    <ul> 
                        <% int size=0; %>
                        <% if(request.getSession().getAttribute("basket")!=null) { %> 
                            <% BasketRemote basket = (BasketRemote) request.getSession().getAttribute("basket");  %>
                            <% size = basket.getSize(); %>
                        <% } %>
                        <%if (session.getAttribute("user") != null) {%>
                        <li><a href="article.jsp">Create Article</a></li>
                        <li><a href="ProfilePage">My Profile</a></li>
                        <li><a href="basket.jsp">Basket (<% out.print(size); %>) </a></li>
                        <li> <form action="Logout" method="post" name="logout">
                                Logout <input type="submit" name="Logout" value="Logout"/>
                            </form></li>
                            <%} else {%>
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="registration.jsp">Registration</a></li>
                        <li><a href="basket.jsp">Basket (<% out.print(size); %>) </a></li>
                        <%}%>

                    </ul>
                </nav>
            </header> 
        </div>

        <div class="wrapper row2">
            <div id="container" class="clear">
                <!-- content body -->
                <section id="shout">
                    <p><b>Disclaimer:</b> <i>This website is not an wiki encyclopedia. This is a study project that is part of a
                            Computing Science module taught at the University of East Anglia, Norwich, UK. If you
                            have any questions, please contact the module coordinator, Joost Noppen, at
                            j.noppen@uea.ac.uk</i></p>
                </section>
                <div id="homepage">
                    <section id="latest"> 
                        <div class="mainBodyWrapper">
        
        <h1>Basket View</h1>
        <% if(session.getAttribute("basket")!=null) { %>
        <% BasketRemote basket = (BasketRemote) session.getAttribute("basket"); %>
        <% for (int i=0; i<basket.getBasket().size(); i++) { %>
        <p>
        <% out.print((i+1) + ". " + basket.getBasket().get(i).getName() + " by " + 
                basket.getBasket().get(i).getAuthor().getName());%>
           <a href="javascript:void(0);" onclick="document.getElementById('removeFromBasket').submit();">Remove From Basket</a></p>
           
           <form id="removeFromBasket" action="RemoveFromBasket" method="post">
               <input type="hidden" name="ritem" value="<% out.print(basket.getBasket().get(i).getId()); %>"/>
               <input type="hidden" name="url" value="basket"/>
           </form>
        <% } %>
            <br/>
           <a href="GenerateAndDownload" target="_blank">Generate and Download a PDF e-book</a>
           <% } else if(session.getAttribute("basket")==null) {%>
        <p> Basket is empty </p>
        <% } %>
       
       

                        </div>
                    </section>
                    <section id="services" class="last clear"> 
                        <div id="showArticles" > </div>
                    </section>
                    <!-- / services area -->
                </div>
                <!-- / content body -->
            </div>
        </div>        

    </body>
</html>
