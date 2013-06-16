<%-- 
    Document   : profile
    Created on : Mar 17, 2013, 10:41:48 PM
    Author     : Sermilion
--%>
<%@page import="entities.Favourites"%>
<%@page import="ejb.StatefulBeanRemote"%>
<%@page import="entities.Users"%>
<%@page import="entities.Article"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Tags"%>
<%@page import="ejb.StatefulBeanRemote"%>
<%@page import="java.util.List"%>
<%@page import="ejb.StatelessBeanRemote"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%StatefulBeanRemote userRemote = (StatefulBeanRemote) session.getAttribute("user");%>
        <%String emailLogged = (String) session.getAttribute("emailLogged");%>
        <%
        Users loggedUser = (Users) session.getAttribute("loggedUser");
       Users user = (Users) session.getAttribute("userToDisplay");
        %> 
        <title><%out.println(user.getName());%></title>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
        <link href="layout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/profile.js"></script>
        <script type="text/javascript" src="js/validations.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

            
            
            
            
                    <%------------------%>
        <script language="javascript">

            
            
            
            
            function validateForm()
            {
              
                var y = document.forms["myForm"]["rating"].selectedIndex;
                
                if(y==0){
                    
                    alert("Select rating please");
                    return false;
                }
                if(y!=0){
                    
                  //  window.location.reload();
                    return true;
                }
                
               
               
            }

        </script>

        <%------------------%>
            
            
            
            
    </head>
    <body>      
        <div class="wrapper row1">
            <header id="header" class="clear">
                <hgroup>
                    <h1>Wiki Encyclopedia</h1>            
                </hgroup>
                <form id="searchForm" action="SearchArticle" method="post">
                    <input type="text" name="title">
                    <input type="submit" value="Search">
                </form>
                <nav>           
                    <ul> 
                        <%if (session.getAttribute("user") != null) {%>
                        <li><a href="article.jsp">Create Article</a></li>
                        <li><a href="index.jsp">Home</a></li>
                        <li> <form action="Logout" method="post" name="logout">
                                Logout <input type="submit" name="Logout" value="Logout"/>
                            </form></li>
                            <%} else {%>
                        <li><a href="registration.jsp">Registration</a></li>
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
                            <%if (session.getAttribute("emailLogged") == null) {%>     
                            <div class="loginForm">

                                <form action="Login" method="post">
                                    <p>Email: <input type="text" name="email"></p>
                                    <p>Password: <input type="password" name="password"></p>
                                    <p><input type="submit" value="Login"></p>
                                </form>

                                <%if (request.getAttribute("login") != null) {%>
                                <%if ((Boolean) request.getAttribute("login") == false) {%>
                                <%out.println("Wrong email/password");
                                        }
                                    }%>
                            </div>
                            <%}%>
                            <div id="homepage">
                                <section id="latest"> 
                                    <div class="mainBodyWrapper">
                                        <div class="loginForm">
                                            <h1>Favourites</h1>

                                            <form id="RemoveUpdated" action="#" method="post">
                                                <input type="hidden" id="user_id_for_ajax" value="<%=user.getId()%>"/>
                                            </form>

                                            <%List<Favourites> fav = (List<Favourites>) session.getAttribute("favList");
                                            %>

                                            <c:forEach var="item" items="<%= fav%>">
                                                <c:set var="art_id" value="${item.getFavArticle().getId()}"/>
                                                <div id="articleUpdated">
                                                    <c:choose>
                                                        <c:when test="${item.getUpdated()==true}">
                                                            <c:out value="Updated">Updated</c:out>
                                                        </c:when>
                                                    </c:choose>
                                                </div>

                                                <a href="<c:url value='SeeArticle?art_id=${item.getFavArticle().getId()}'/>"><c:out value="${item.getFavArticle().getName()}"/></a><br/>
                                            </c:forEach>
                                        </div>
                                            <%if(user==loggedUser){
                                                out.println(String.format("<p>Welcome, "
                                                            + "%s "
                                                            + "</p>", loggedUser.getName()));
                                                                                               
                                            }%> 

                                        <div class="browseProfile" id="browseProfile">
                                            <h1>Profile Page</h1>
                                            ID: <%out.println(user.getId());%><br>
                                            Name: <%out.println(user.getName());%><br>
                                            Rating: <%out.println(user.getRating());%><br>
                                            Date of birth:<%out.println(user.getDob());%><br>
                                            Last logged in: <%out.println(user.getLastlogin());%><br>
                                           <%if(loggedUser.getId()==user.getId()){%> 
                                            <a href="#" id="hidebrowseProfile">Edit Profile &raquo;</a>
                                            <%}%>
                                            <%if(loggedUser.getId()!=user.getId()){
                                            if(loggedUser.getType().equals("admin")){
                                                    if(user.getType().equals("banned")){
                                                        out.println(String.format("<form action=\"RemoveBan?user_id=%d\" method=\"POST\" >"
                                                                + "<input type=\"submit\" value=\"Remove Ban\"/> </form>", user.getId()));

                                                    } else {

                                                        out.println(String.format("<form action=\"BanUser?user_id=%d\" method=\"POST\">"
                                                                + "<input type=\"submit\" value=\"Ban\"/></form>",user.getId()));
                                                    }
                                                } else {
                                                if(request.getAttribute("canRate")!=null){
                                                
                                                    Object canRateS = request.getAttribute("canRate");
                                                    boolean canRate = (Boolean) request.getAttribute("canRate");
                                                   
                                                    if (canRateS!=null && canRate == true) {
                                                        out.println(String.format(" <form name=\"myForm\" action=\"RateUser?rated_user=%d&amp;canRate=false\"   onsubmit=\"return validateForm()\"  method=\"POST\"   >"
                                                                + "<select name=\"rating\">"
                                                                + "<option value=\"%s\">Please select rating</option>"
                                                                + "<option value=\"%d\">1</option>"
                                                                + "<option value=\"%d\">-1</option>"
                                                                + "</select><input type=\"submit\" value=\"Rate\"/></form>",user.getId(), "Please select rating", 1, -1));
                                                    }
                                                }
                                               }
                                             }
                                            %>

                                            
                                            
                                        </div>

                                        <div id="editProfile" class="hidden">
                                            <h1>Edit My Info</h1>
                                            <form action="EditProfile" name ="EditProfile" method="post">
                                                Name:<input type="text" name="edit_my_name" value="<%out.println(user.getName());%>"><br/><div id="my_name"></div><br/>
                                                Date Of Birth:<input type="text" name="edit_my_dob" value="<%out.println(user.getDob());%>"><br/><div id="my_dob"></div><br/>
                                                <input type="hidden" name="UserToEdit" value="<%out.println(user);%>" />                                                                    
                                                <p><input type="submit" value="Save" name="Edit Profile"/></p>                                              
                                            </form>
                                            <a href="#" id="showbrowseProfile">&laquo; Back</a>
                                        </div>
                                                
                                    </div>
                            </div>
                    </section>
                </div>
            </div>
        </div>      
    </body>
</html>
