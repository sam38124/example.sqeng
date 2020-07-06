

function init() {
    document.getElementById('okButton')
        .addEventListener('click', function() {
            document.getElementById('welcome').hidden = !document.getElementById('welcome').hidden;
        }, false);
}
$(document).ready(function(){
    $(".signin").click(function(){
        showDialog(true);
        $.post("/ManagerSign",
            {
                admin:$(".admin").val(),
                password:$(".password").val()
            },
            function(data,status){
                var obj = jQuery.parseJSON( data );
                showDialog(false);
                alert( obj.result );
            });
    });
});
function showDialog(visible) {
    document.getElementById("load").hidden = !visible;
}