function showHistoryById(id){
    
    $.ajax({
            url : "ShowArticleHistory",
            dataType : 'json',
            type: 'GET',
            data: {
                articleID:id
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert(textStatus);
            },
            success : function(data){
               
                $('#showhistory').html(''); 
                for(var i=0;i<data.length;i++){
                    outputToDiv(data,i);
                }             
               
              
            }

        });
    
    function outputToDiv(data1,i) {
        
            
   
            $('#showhistory').prepend(                
                 "Content:<p>"+data1[i].content+"</p>"+
               "<p> Date modified: "+data1[i].type.substring(4, data1[i].type.length)+"</p>"
                );
        }
        return false;
    
}