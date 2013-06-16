function showComments(id,userID){
    
    $.ajax({
            url : "ShowAllComments",
            dataType : 'json',
            type: 'GET',
            data: {
                articleID:id
            },
//            error : function(jqXHR, textStatus, errorThrown) {
//                alert(textStatus);
//            },
            success : function(data){
                $('#showComments').html(''); 
                for(var i=0;i<data.length;i++){
                    outputToDiv(data,i);
                }
            }

        });
    
        function outputToDiv(data1,i) {
            
            $str="";
            if(data1[i].user.id==userID){                
                $str="<input type=submit value=\"Delete Comment\">";
            }
   
            $('#showComments').prepend(                
               "<form action=\"DeleteComment\">"+                   
                   "<fieldset>"+
                    "<legend>Comment:</legend>"+
                "<strong>"+data1[i].user.name+"</strong>"+
                "<p>"+data1[i].content+"</p>"+
                "<p>"+data1[i].date+"</p>"+ $str+
                 "<input type=\"hidden\" name=\"commentIdDel\" value="+data1[i].id+">"+
                 "</fieldset>"+
                "</form>"
                );
        }
        return false;
    
}