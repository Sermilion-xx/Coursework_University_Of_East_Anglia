<%-- 
    Document   : article
    Created on : Mar 14, 2013, 8:57:28 AM
    Author     : azamatibragimov
--%>

<%@page import="ejb.BasketRemote"%>
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
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
        <link href="layout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/index.js"></script>

        <script type="text/javascript" src="js/validations.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Article</title>
    </head>
    <body>      
        <%  List<String> supertagNames = new ArrayList();
            List<String> subsupertagNames = new ArrayList();
            List<String> subtagNames = new ArrayList();

            List<Tags> supertags = null;
            List<Tags> subsupertags = null;
            List<Tags> subtags = null;

            List<Article> articles = null;
            StatefulBeanRemote statefulb = null;

            if (session.getAttribute("guest") != null) {
                statefulb = (StatefulBeanRemote) session.getAttribute("guest");
            }
            if (session.getAttribute("user") != null) {
                statefulb = (StatefulBeanRemote) session.getAttribute("user");
            }
            if (statefulb != null) {
                supertags = statefulb.getAllTags(new Long(0));
                subsupertags = statefulb.getAllTags(new Long(1));
                subtags = statefulb.getAllTags(new Long(2));

                for (int i = 0; i < subsupertags.size(); i++) {
                    subsupertagNames.add(subsupertags.get(i).getName());
                }

                for (int i = 0; i < subtags.size(); i++) {
                    subtagNames.add(subtags.get(i).getName());
                }


        %>


        <%}%>
        <c:set var="select_sub_tag"  value='${param.b_sub_tag}'> </c:set>
        <c:set var="supertagList"  value='<%= supertagNames%>'> </c:set>
        <c:set var="subsupertagList"  value='<%= subsupertagNames%>'> </c:set>
        <c:set var="subtagList"  value='<%= subtagNames%>'> </c:set>
        <c:set var="articles"  value='<%= articles%>'> </c:set>

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
                            <div class="browseArticles">
                                <h1>Browse Articles</h1>


                                <select class="loadArticlesHome"  id="b_super_tag" name="b_super_tag">
                                    <option>Main Category</option>
                                    <c:forEach var="item" items="<%=supertags%>">
                                        <option value="${item.getId()}">${item.getName()}</option>
                                    </c:forEach>
                                </select>
                                                                                                      
                                    
                                    <select class="loadArticlesHome" id="b_subsuper_tag" name="b_subsuper_tag">
                                        <option selected>Select Next Category</option>
                                    </select> 
                                    
                                    <select class="loadArticlesHome" id="b_sub_tag" name="b_sub_tag">
                                        <option selected>Select Next Category</option>
                                    </select> 
                                    
                                    
                                    
                                    
                                    
                                    
                                    <br/>
                                    <%if (request.getAttribute("searchResult") != null) {
                                            List<Article> list = (List<Article>) request.getAttribute("searchResult");
                                    %>
                                    <c:forEach var="item" items="<%= list%>">
                                        <a href="<c:url value='SeeArticle?art_id=${item.getId()}'/>"><c:out value="${item.getName()}"/></a><br/>
                                    </c:forEach>
                                    <%request.setAttribute("searchResult", null);%>
                                    <%}%>

                                    <%if (request.getAttribute("NoResult") != null && request.getAttribute("searchResult") == null) {%>                                          
                                    <%if ((Boolean) request.getAttribute("NoResult") == true)%>
                                    <p><%out.println("Nothing was found");%></p>
                                    <%request.setAttribute("NoResult", null);
                                    }%>

                            </div>

                        </div>
                    </section>
                    <section id="services" class="last clear"> 
                        <div id="showArticles"></div>
                    </section>
                    <!-- / services area -->
                </div>
                <!-- / content body -->
            </div>
        </div>        
    </body>
</html>
