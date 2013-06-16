<%-- 
    Document   : registration
    Created on : Mar 9, 2013, 7:09:40 PM
    Author     : azamatibragimov
--%>

<%@page import="ejb.BasketRemote"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Home</title>
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
        
        <h1>Registration page</h1>
        <form action="Register" method="post">
           <p>Full name: <input type="text" name="name"></p>
           <p>Email: <input type="text" name="email"></p>
           <p>Password: <input type="password" name="password"></p>        
           <p>Re-Enter Password: <input type="text" name="re-password"></p>
           
           <%String pass= "";
           if(request.getAttribute("wrongpassword")!=null){%>
                <%pass=(String)request.getAttribute("wrongpassword");%>
           <%if(pass.equals("nomatch")){%>
           <%out.println("Passwords do not match.");%>
           <%}}%>
           
           <p>Date Of Birth: <input type="text" name="dob"></p>          
           <p><input type="submit" value="Register"></p>
           
           
           <%if(request.getAttribute("emptyData")!=null){%>
                <%Boolean fulldata=(Boolean)request.getAttribute("emptyData");%>
                <%if(fulldata.equals(true)){%>
           <%out.println("Enter all requered fields.");%>
           <%request.setAttribute("emptyData", false);%>
           <%}}%>
           
           <%Boolean reg= null;
           if(request.getAttribute("regDone")!=null){%>
                <%reg=(Boolean)request.getAttribute("regDone");%>
           <%if(reg==false){%>
           <%out.println("Failed To Register.");%>
           <%request.setAttribute("regDone", null);%>
           <%}else{
                    out.println("Failed To Register.");
                    request.setAttribute("regDone", null);
                                       }
           }%>
           <a href="index.jsp">Home</a>
        </form>
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
