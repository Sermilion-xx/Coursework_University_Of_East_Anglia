$(document).ready(function(){ 
    
 
    function outputToDiv(data1,i) {
        var id = data1[i].id;
        var name = data1[i].name;
        var content = data1[i].content;
        var doc = data1[i].date_created;
        var dlm = data1[i].date_modified;
        var hidden = data1[i].hidden;
        var locked = data1[i].locked;
        var rating = data1[i].rating;
        var author = data1[i].author;
        var subtag = data1[i].sub_tag;
        
        $('#showArticles').prepend(                
            "<article class='one_quarter'>"+
            "<figure><img src='images/demo/32x32.gif' width='32' height='32' alt=''></figure>"+
            "<strong>"+data1[i].name+"</strong>"+
            "<p>"+data1[i].content.substring(0,50)+"</p>"+
            "<p class='more'><a href='SeeArticle?art_id="+id+"&art_name="+name+"&art_content="+content+"&art_doc="+doc+"&art_dlm="+dlm+"&art_hidden="+hidden+"&art_locked="+locked+"&art_author="+author.name+"&art_rating="+rating+"&art_subtag="+subtag.tag_name+"'>Read More &raquo;</a></p>"
                
            );
    }
   
    $(".loadArticlesHome").change(function(){ 
        var b_type=null;
        var option_value = $(this).children('option:selected').val();
       var currentId = $(this).attr('id');
if(currentId=="b_super_tag"){
    b_type=0;
}else if(currentId=="b_subsuper_tag"){
    b_type=1;
}else if(currentId=="b_sub_tag"){
    b_type=2;
}
        $.ajax({
            url : "LoadArticlesByTag",
            type: 'GET',
            datatype:'json',
            data: {
                b_tag:option_value,
                b_tag_type:b_type
            },
            success : function(data){               
              
                var allArticles=data.split('#');
                $('#showArticles').html('');
                for(var i=0;i<allArticles.length;i++){
                    var splitArticle=allArticles[i].split('/');
                
                    var id = splitArticle[0];
                    var name = splitArticle[7];
                    var content = splitArticle[1];
                    var doc = splitArticle[2];
                    var dlm = splitArticle[3];
                    var hidden = splitArticle[4];
                    var locked = splitArticle[6];
                    var rating = splitArticle[8];
                    var author = splitArticle[10];
                    var subtag = splitArticle[13];
                    
                    $('#showArticles').prepend(                
                        "<article class='one_quarter'>"+
                        "<figure><img src='images/demo/32x32.gif' width='32' height='32' alt=''></figure>"+
                        "<strong>"+splitArticle[7]+"</strong>"+
                        "<p>"+splitArticle[1].substring(0,150)+"</p>"+
                        "<p class='more'><a href='SeeArticle?art_id="+id+"&art_name="+name+"&art_content="+content+"&art_doc="+doc+"&art_dlm="+dlm+"&art_hidden="+hidden+"&art_locked="+locked+"&art_author="+author+"&art_rating="+rating+"&art_subtag="+subtag+"'>Read More &raquo;</a></p>"
                
                        );                    
                }
            }

        });
            
        return false;
        
    });
        
       
    
    $("#b_super_tag").change(function(){ 
        $('#b_sub_tag').html("<option selected value='-1'>Select Next Category</option>"); 
        var option_value = $(this).children('option:selected').val();
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:0
            },
            success : function(data){
                $('#b_subsuper_tag').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    $("#b_subsuper_tag").change(function(){ 
        var option_value = $(this).children('option:selected').val();
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:1
            },
            success : function(data){
                $('#b_sub_tag').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    //---------------------------------------------------------------------------     
  
    $(".super_tagSelect").change(function(){ 
        $('#a_sub_tag').html("<option selected value='-1'>Select Next CategoryAAA</option>"); 
        var option_value = $(this).children('option:selected').val();
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:0
            },
            success : function(data){
                $('.subsuper_tagSelect').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    $(".subsuper_tagSelect").change(function(){ 
        var option_value = $(this).children('option:selected').val();
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:1
            },
            success : function(data){
                $('.sub_tagSelect').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    //-----------------------------------------------------------------------------   
    
    $("#t_super_tag").change(function(){ 

        var option_value = $(this).children('option:selected').val();
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:0
            },
            success : function(data){
                $('#t_subsuper_tag').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    
    $("#t_subsuper_tag").change(function(){ 
        $.ajax({
            url : "LoadTags",
            type: 'GET',
            
            data: {
                b_super_tag:option_value,
                type:1
            },
            success : function(data){
                $('#t_sub_tag').html(data); 
            }           
        }); 
        return false;   
  
    }); 
    
    //-----------------------------------------------------------------------------    
    
    
    
    
    
    //    
    $(document).on("click","#hideArticleLink",function(){
 
        $( "#createTag" ).show();
        $( "#createArticle" ).hide();
    });

    $(document).on("click","#hideTagLink",function(){

        $( "#createTag" ).hide();
        $( "#createArticle" ).show();
    });

    $(document).on("click","#hidebrowseArticle",function(){
 
        $( "#editArticle" ).show();
        $( "#browseArticles" ).hide();
    });

    $(document).on("click","#showbrowseArticle",function(){

        $( "#editArticle" ).hide();
        $( "#browseArticles" ).show();
    });

    
});