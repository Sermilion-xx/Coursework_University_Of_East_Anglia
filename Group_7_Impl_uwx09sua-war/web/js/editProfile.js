$(document).ready(function(){
    var array = jsVar;
//    $.each(array,function(i,v){
//        alert(i+" "+v);
//    });
    $(".sscategories").live('click',function(){
        var cid=$(this).attr('href');
        var act = 'getsc';
        $(".sscategories").css('text-decoration','none');
        $(this).css('text-decoration','underline');
        $("#rightCategoryChooser").html("<h3>Category</h3>");
        $.ajax({
            type: "POST",
            url: "servlet/editprofile.php",
            data:"act=" + act+"&&cid="+cid,
            success: function(html) {
                    $("#centerCategoryChooser").html("<h3>Super Category</h3>"+html);
            },
            fail: function(html) {
                alert('Server Error');
            }
        });
        return false;
    });

    $(".scategories").live('click',function(){
        var cid=$(this).attr('href');
        var act = 'getc';
        $(".scategories").css('text-decoration','none');
        $(this).css('text-decoration','underline');
        $.ajax({
            type: "POST",
            url: "servlet/editprofile.php",
            data:"act=" + act+"&&cid="+cid,
            success: function(html) {
                    $("#rightCategoryChooser").html("<h3>Category</h3>"+html);
            },
            fail: function(html) {
                alert('Server Error');
            }
        });
        return false;
    });
    
    $(".categories").live('click',function(){
        var name = $(this).text();
        var cid = $(this).attr('href');
        var text = $("#currentSubs").text();
        if(!array){
            $("#currentSubs").html("<a href='"+cid+"' class='delete'>"+name+"</a><br>");
            array = [];
            array.push({
                'cid':cid,
                'name':name
            })
        }
        else{
            var error=false;
            $.each(array,function(i,v){
                if(v.cid==cid){
                    error=1;
                }
            });
            if(!error){
                array.push({
                    'cid':cid,
                    'name':name
                })
                $("#currentSubs").append("<a href='"+cid+"' class='delete'>"+name+"</a><br>");
            }
            else{
                alert('you already subscribed for this category');
            }
        }
        return false;
    });

    $('.delete').live('click',function(){
        var cid = $(this).text();
        var removeMe;
        $.each(array,function(i,v){
            if( v.name == cid ) {
               removeMe = i;
            }
         });
        array.splice(removeMe,1);
        $(this).hide();
        return false;
    });

    $("#save").click(function(){
        var act = 'save';
        var temp="";
        $.each(array,function(i,v){
            temp += v.cid+",";
        });
        $.ajax({
            type: "POST",
            url: "servlet/editprofile.php",
            data:"act=" + act+"&&temp="+temp,
            success: function(html) {
                    alert(html);
            },
            fail: function(html) {
                alert('Server Error');
            }
        });
        return false;
    });


});