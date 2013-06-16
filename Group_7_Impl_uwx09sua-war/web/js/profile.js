 $(document).ready(function(){ 
    $.ajax({
            url : "RemoveUpdatedFlag",
            type: 'GET',
//            error : function(jqXHR, textStatus, errorThrown) {
//                alert(textStatus);
//            },
            success : function(){
            }

        });
              
        
        
$(document).on("click","#hidebrowseProfile",function(){
 
    $( "#editProfile" ).show();
    $( "#browseProfile" ).hide();
});

$(document).on("click","#showbrowseProfile",function(){

    $( "#editProfile" ).hide();
    $( "#browseProfile" ).show();
});
        
        
    return false;    
        
});  