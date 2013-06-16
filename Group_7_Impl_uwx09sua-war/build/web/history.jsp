<%-- 
    Document   : history
    Created on : Apr 14, 2013, 9:04:31 PM
    Author     : Orkhan Yusubov 4852419
--%>
<%@page import="entities.Users"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="com.google.gson.stream.JsonReader"%>
<%@page import="java.io.StringReader"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.google.gson.JsonParser"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.ProcessBuilder.Redirect.Type"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="entities.Article"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Tags"%>
<%@page import="ejb.StatefulBeanRemote"%>
<%@page import="java.util.List"%>
<%@page import="ejb.StatelessBeanRemote"%>
<%@page import="entities.Users"%>
<%@page import="entities.Article"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="layout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/history.js"> </script>
        <title>JSP Page</title>
    </head>
    <%
    
    //:::::Orkhan ::::::
      Article art=null;
        if(session.getAttribute("ArticleToDisplay")!=null){
              art = (Article)session.getAttribute("ArticleToDisplay");
        }                                                            
         Users userSS = (Users)session.getAttribute("loggedUser");
                                    Long usrID= userSS.getId();
     String str = String.valueOf(art.getId());
     String uID = String.valueOf(usrID);
    int idA = Integer.parseInt(str);
%>
<body onload="showHistoryById(<%=idA%>)">
 
        <%Users user = (Users) session.getAttribute("loggedUser");
           
            if (session.getAttribute("user") != null) {
                StatefulBeanRemote statefulb = (StatefulBeanRemote) session.getAttribute("user");

             
            }%>
       
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
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="ProfilePage">My Profile</a></li>
                        <li> <form action="Logout" method="post" name="logout">
                                Logout <input type="submit" name="Logout" value="Logout"/>
                            </form></li>
                            <%} else {%>
                        <li><a href="registration.jsp">Registration</a></li>
                        <li><a href="index.jsp">Home</a></li>
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
                            <%if (session.getAttribute("loggedUser") != null) {%>
                       

                            <%}%>



                            <div class="browseArticles" id="browseArticles"> 
                                <%if(session.getAttribute("statelessb")!=null){
                                    StatelessBeanRemote statelessb = (StatelessBeanRemote)session.getAttribute("statelessb");
                                
}%>
                                
                             <div class="showhistory" id="showhistory"></div>
                               
           </div>

                            <div id="editArticle" class="hidden">
                              
                              
                            </div>
                              
                        </div>
                    </section>
                </div>
            </div>
        </div>     

    </body>
</html>
