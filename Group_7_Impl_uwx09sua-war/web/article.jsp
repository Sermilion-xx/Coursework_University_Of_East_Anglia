<%-- 
    Document   : article
    Created on : Mar 14, 2013, 8:57:28 AM
    Author     : azamatibragimov
--%>


<%@page import="ejb.BasketRemote"%>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Article</title>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
        <link href="layout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <script type="text/javascript" src="js/validations.js"></script>
        <script type="text/javascript" src="js/comments.js"></script> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <%Users user = null;
        Long userID = null;
        Long idA = null;
        String uID = null;
        Long artID=null;
        if (session.getAttribute("loggedUser") != null) {
            user = (Users) session.getAttribute("loggedUser");
            userID = user.getId();
            Long usrID = user.getId();
            uID = String.valueOf(usrID);
            artID = usrID;


        }
        Article art = null;
        if (session.getAttribute("ArticleToDisplay") != null) {
            art = (Article) session.getAttribute("ArticleToDisplay");
            String str = String.valueOf(art.getId());
            idA = Long.parseLong(str);
        }
    %>

    <body onload="showComments(<%=idA%>,<%=uID%>)">        
        <%
            List<Tags> supertags = null;
            List<Tags> subsupertags = null;
            List<Tags> subtags = null;
            String ratedArticle = "";

            if (session.getAttribute("user") != null) {
                StatefulBeanRemote statefulb = (StatefulBeanRemote) session.getAttribute("user");

                supertags = (List<Tags>) statefulb.getAllTags(new Long(0));
                subsupertags = (List<Tags>) statefulb.getAllTags(new Long(1));
                subtags = (List<Tags>) statefulb.getAllTags(new Long(2));
                
                ratedArticle= (String) statefulb.getArticleRatingByUser(artID, idA);
                
            }%>


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
        
                            <%if (session.getAttribute("loggedUser") != null) {%>
                            <div class="loginForm">
                                <div id="createTag"  class="hidden">
                                    <h1>Create Tag</h1>
                                    <form name="CreateTag" action="CreateTag" method="post" onsubmit="return validateTagForm();">
                                        Tag name: <input type="text" name="t_tag_name"><div id="t_tag_name_div"></div>
                                        Description: <input type="text" name="t_tag_desc"><div id="t_tag_desc_div"></div>

                                        <select id="t_tag_type_id" name="t_tag_type" onChange="Tagselect();">
                                            <option value="Select type">Select type</option>
                                            <option value="Supertag">Supertag</option>
                                            <option value="Subsupertag">Subsupertag</option>
                                            <option value="Subtag">Subtag</option>
                                        </select><div id="t_tag_type_div"></div>



                                        Supertag:<select id="t_super_tag" name="t_super_tag" style="visibility:hidden;">
                                            <option value="-1">Main Category</option>
                                            <c:forEach var="item1" items="<%=supertags%>">
                                                <option value="${item1.getId()}">${item1.getName()}</option>
                                            </c:forEach>
                                        </select><br/><div id="t_super_tag_div"></div>


                                        Subsuper <select id="t_subsuper_tag" name="t_subsuper_tag" style="visibility:hidden;">
                                            <option value="-1">Select Next Category</option>
                                        </select> <br/><div id="t_subsuper_tag_div"></div>

                                      


                                        
                                        

                                        <p><input type="submit" name="Submit"></p>
                                    </form>
                                    <nav>
                                        <a  href="#" id="hideTagLink"/>Create article</a>
                                    </nav>
                                </div>

                                <div id="createArticle" >
                                    <h1>Create Article</h1>
                                    <form action="CreateArticle" name ="CreateArticle" method="post" onsubmit="return validateArticleForm(); ">
                                        Article name:<input   type="text" name="article_name"><div id="article_name"></div><br/>
                                        Content:<textarea rows="5" cols="47"  name="article_content"></textarea><div id="article_content"></div><br/>

                                        <select class="super_tagSelect"  id="a_super_tag" name="a_super_tag">
                                            <option>Main Category</option>
                                            <c:forEach var="item" items="<%=supertags%>">
                                                <option value="${item.getId()}">${item.getName()}</option>
                                            </c:forEach>
                                        </select>


                                        <select class="subsuper_tagSelect" id="a_subsuper_tag" name="a_subsuper_tag">
                                            <option selected value="-1">Select Next Category</option>
                                        </select> 

                                        <select class="sub_tagSelect" id="a_sub_tag" name="a_sub_tag">
                                            <option selected value="-1">Select Next Category</option>
                                        </select> 


                                        <div id="a_sub_tag_div"></div>
                                        <p><input type="submit" name="Create Article"/></p>
                                    </form>
                                    <a href="#" id="hideArticleLink">Create a tag</a>
                                </div>



                            </div>

                            <%}%>



                            <div class="browseArticles" id="browseArticles">                               
                                <% Article article = null;
                                    if (session.getAttribute("ArticleToDisplay") != null) {
                                        article = (Article) session.getAttribute("ArticleToDisplay");
                                %>
                                <h1><%out.println(article.getName() + "</br>");%></h1>
                                <p><%out.println(article.getContent() + "</br>");%></p>
                                <p><%out.println("Created on: " + article.getDate_created() + "</br>");%></p>
                                <p><%out.println("Last modified on: " + article.getDate_modified() + "</br>");%></p>
                                <p><%out.println("Tag: " + article.getSubTag().getName() + "</br>");%></p>
                               <% if(user!=null){%>
                                Author: <%out.println(String.format("<a href =\"ProfilePage?anotherUserId=%d\">%s</a>",article.getAuthor().getId(), article.getAuthor().getName()));
                                }else
                                   out.println("Author: " + article.getAuthor().getName() + "</br>");
                                   %>
                                

                                <p><%out.println("Rating: " + article.getRating() + "</br>");%></p> 

                                <form id="addToFavForm" action="AddToFavourites" method="post">
                                    <input type="hidden" name="fav_id" value="<%=article.getId()%>"/>
                                </form>
                                <form id="removeFromFavForm" action="RemoveFromFavForm" method="post">
                                    <input type="hidden" name="fav_id" value="<%=article.getId()%>"/>
                                </form>

                                <form id="lockUnlock" action="LockUnlockArticle" method="post">
                                    <input type="hidden" name="art_id" value="<%=article.getId()%>"/>
                                </form>
                                
                                 <form id="addToBasket" action="AddToBasket" method="post">
                                    <input type="hidden" name="aitem" value="<%=article.getId()%>"/>
                                </form>
                                <form id="removeFromBasket" action="RemoveFromBasket" method="post">
                                    <input type="hidden" name="ritem" value="<%=article.getId()%>"/>
                                    <input type="hidden" name="url" value="article"/>
                                </form>
                                
                                <% if (session.getAttribute("basket") != null) { %>
                                    <% BasketRemote basket = (BasketRemote) session.getAttribute("basket"); %>
                                <!-- basket part -->
                                <% if (!basket.inTheBasket(article.getId())) {%>
                                <a href="javascript:void(0);" onclick="document.getElementById('addToBasket').submit();">Add to Basket</a><br/>
                                <%}else{%>
                                <a href="javascript:void(0);" onclick="document.getElementById('removeFromBasket').submit();">Remove From Basket</a><br/>
                                <%}%>
                                <%}else{%>
                                <a href="javascript:void(0);" onclick="document.getElementById('addToBasket').submit();">Add to Basket</a><br/>
                                <%}%>
                                <!-- basket part ends here -->

                                <%if (session.getAttribute("loggedUser") != null) {
                                        if (article.isLocked() == false || user.getType().equals("admin")) {%>                                 
                                <a href="#" id="hidebrowseArticle">Edit Article &raquo;</a>                                                                 
                                <%}
                                if (!article.isFavourite()) {%>
                                <a href="javascript:void(0);" onclick="document.getElementById('addToFavForm').submit();">Add To My Favorites </a><br/>

                                <%} else {%>
                                <a href="javascript:void(0);" onclick="document.getElementById('removeFromFavForm').submit();">Remove From My Favorites </a><br/>
                                <%} %>
                                
                                <!-- Rate article part is here -->
                                <form id="RateArticleUP" action="RateArticle" method="post">
                                    <input type="hidden" name="ratea" value="up"/>
                                    <input type="hidden" name="articlea" value="<%=article.getId()%>"/>
                                    <input type="hidden" name="usera" value="<%= user.getId() %>" />
                                </form>
                                <form id="RateArticleDOWN" action="RateArticle" method="post">
                                    <input type="hidden" name="ratea" value="down"/>
                                    <input type="hidden" name="articlea" value="<%=article.getId()%>"/>
                                    <input type="hidden" name="usera" value="<%= user.getId() %>" />
                                </form>
                                    
                                    <%
                                    String  NewRatedArticle = ratedArticle.trim();
                                    
                                    
                                    if (NewRatedArticle.toString().equals("[up]")) {%>
                                       <a href="javascript:void(0);" onclick="document.getElementById('RateArticleDOWN').submit();">Rate DOWN</a><br/>
                                    
                                       <% }else if(NewRatedArticle.equals("[down]"))  {%>
                                        <a href="javascript:void(0);" onclick="document.getElementById('RateArticleUP').submit();">Rate UP</a>
                                    
                                    <%} else { %>
                                        <a href="javascript:void(0);" onclick="document.getElementById('RateArticleUP').submit();">Rate UP</a>
                                        <a href="javascript:void(0);" onclick="document.getElementById('RateArticleDOWN').submit();">Rate DOWN</a><br/>
                                    <% } %>
                                    <!-- and it ends here -->
                                   
                                   <%}%>
                                <%if (user != null) {
                                        if (user.getType().equals("admin")) {
                                            if (article.isLocked() == false) {%>
                                <a href="javascript:void(0);" onclick="document.getElementById('lockUnlock').submit();">Lock Article</a><br/>
                                <%} else {%>
                                <a href="javascript:void(0);" onclick="document.getElementById('lockUnlock').submit();">Unlock Article</a><br/>
                                <%}
                                        }
                                    }%>

                                <a href="history.jsp">Article History</a>                   



                                <div id="showComments"> </div>                            
                                <%if (user != null) {%>
                                <form action="AddComment" method="post"> 

                                    <textarea rows="4" cols="100" name="commentText"></textarea>
                                    <input type="hidden" name="articleID" value="<%=article.getId()%>"/>
                                    <input type="hidden" name="userID" value="<%=userID%>"/>
                                    <br/>
                                    <input type="submit" value="Add Comment" >  

                                </form>
                                <%}%>          
                            </div>

                            <div id="editArticle" class="hidden">
                                <h1>Edit Article</h1>
                                <form action="EditArticle" name ="EditArticle" method="post">
                                    Article name:<input   type="text" name="edit_article_name" value="<%out.println(article.getName());%>"><br/><div id="article_name"></div><br/>
                                    Content:<textarea rows="5" cols="47"  name="edit_article_content" ><%out.println(article.getContent());%></textarea><br/><div id="article_content"></div><br/>

                                    <div id="edit_super_tag_div"></div>
     <%---------------------------------------------------------------------------------------------------------------%>                               
                                    <select class="super_tagSelect"   id="e_super_tag" name="e_super_tag">
                                            <option value="-1">Main Category</option>
                                            <c:forEach var="item" items="<%=supertags%>">
                                                <option value="${item.getId()}">${item.getName()}</option>
                                            </c:forEach>
                                        </select>


                                        <select  class="subsuper_tagSelect" id="e_subsuper_tag" name="e_subsuper_tag">
                                            <option selected value="-1">Select Next Category</option>
                                        </select> 

                                        <select class="sub_tagSelect" id="e_sub_tag" name="e_sub_tag">
                                            <option selected value="-1">Select Next Category</option>
                                        </select> 

 <%---------------------------------------------------------------------------------------------------------------%> 
                                    <div id="edit_sub_tag_div"></div>
                                    <input type="hidden" name="ArticleToEdit" value="<%out.println(article);%>" />                                                                    
                                    <p><input type="submit" value="Save" name="Edit Article"/></p>
                                    <a href="#" id="showbrowseArticle">&laquo; Back </a>
                                </form> 
                            </div>
                                    
                                    <div id="show_history_div"></div>
                            <%}%>  
                            -
                        </div>
                    </section>
                </div>
            </div>
        </div>     

    </body>
</html>
