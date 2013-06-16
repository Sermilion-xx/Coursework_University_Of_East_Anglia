
function Tagselect(){                    
    var b = document.getElementById("t_tag_type_id").value;
    if(b=='Subsupertag'){                           
        document.getElementById('t_super_tag').style.visibility = 'visible';
        document.getElementById('t_subsuper_tag').style.visibility = 'hidden';
        
    }else
    if(b=='Subtag'){
        document.getElementById('t_super_tag').style.visibility = 'visible';
        document.getElementById('t_subsuper_tag').style.visibility = 'visible';       
    }else
    if(b=='Supertag'){
        document.getElementById('t_super_tag').style.visibility = 'hidden';
        document.getElementById('t_subsuper_tag').style.visibility = 'hidden';
    }                 
}

function ArticleTagselect(){
    document.getElementById('a_subsuper_tag').style.visibility = 'visible';
    document.getElementById('a_sub_tag').style.visibility = 'visible';
}

function validateArticleForm(){
var article=document.forms["CreateArticle"]["article_name"].value;
var content=document.forms["CreateArticle"]["article_content"].value;
var supertag = document.forms["CreateArticle"]["a_super_tag"].value;
var subsupertag = document.forms["CreateArticle"]["a_subsuper_tag"].value;
var subtag = document.forms["CreateArticle"]["a_sub_tag"].value;
  if(article==null || article==""){
      document.getElementById('article_name').innerHTML = "Please enter the name for the article.";
      return false;
  }else{
      document.getElementById('article_name').innerHTML="";
  }
  if(content==null || content==""){
     document.getElementById('article_content').innerHTML = "Please enter the content for the article.";
     return false; 
  }else if(content.length<50){
      document.getElementById('article_content').innerHTML = "The article must consist more then 50 charachters.";
  return false;
  }else{
      document.getElementById('article_content').innerHTML="";
  }
  if(supertag==null || supertag=="" || supertag=="Select supertag"){
     document.getElementById('a_super_tag_div').innerHTML = "Please select the  main category for your article.";
     return false; 
  }else{
      document.getElementById('a_super_tag_div').innerHTML="";
  }
  if(subsupertag==null || subsupertag=="" || subsupertag=="Select subsupertag"){
     document.getElementById('a_subsuper_tag_div').innerHTML = "Please select the subcategory for your article.";
     return false; 
  }else{
      document.getElementById('a_subsuper_tag_div').innerHTML="";
  }
  if(subtag==null || subtag=="" || subtag=="Select subtag"){
     document.getElementById('a_sub_tag_div').innerHTML = "Please select the category for your article.";
     return false; 
  }else{
      document.getElementById('a_sub_tag_div').innerHTML="";
  }
  
  return true;
}

function validateTagForm(){
var tagname=document.forms["CreateTag"]["t_tag_name"].value;
var desc=document.forms["CreateTag"]["t_tag_desc"].value;
var tagtype = document.forms["CreateTag"]["t_tag_type"].value;
var supert_tag_type = document.forms["CreateTag"]["t_super_tag"].value;
var subsupert_tag_type = document.forms["CreateTag"]["t_subsuper_tag"].value;

if(tagname==null || tagname==""){
     document.getElementById('t_tag_name_div').innerHTML = "Please choose a name for the new tag.";
     return false; 
  }else{
      document.getElementById('t_tag_name_div').innerHTML="";
  }
  
  if(desc==null || desc==""){
     document.getElementById('t_tag_desc_div').innerHTML = "Please enter description for the tag.";
     return false; 
  }else{
      document.getElementById('t_tag_desc_div').innerHTML="";
  }
  
  if(tagtype==null || tagtype=="" || tagtype=="Select type" ){
     document.getElementById('t_tag_type_div').innerHTML = "Please select the type of your tag.";   
     return false; 
  }else{     
      document.getElementById('t_tag_type_div').innerHTML="";
  }

if((supert_tag_type==null || supert_tag_type=="" || supert_tag_type=="-1") && tagtype!='Supertag'){  
     document.getElementById('t_super_tag_div').innerHTML = "Please select supertag.";
     return false; 
  }else{
     
      document.getElementById('t_super_tag_div').innerHTML="";
  }
  
//  if((subsupert_tag_type==null || subsupert_tag_type=="" || subsupert_tag_type=="-1") && tagtype!='Subsupertag'){
//    document.getElementById('t_subsuper_tag_div').innerHTML = "Please select subsupertag.";
//     return false; 
//  }else{
//      document.getElementById('t_subsuper_tag_div').innerHTML="";
//
//  }
//  
  
}