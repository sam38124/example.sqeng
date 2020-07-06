$(document).ready(function(){
    $("#treat").click(function(){
        $("#mainframe",parent.document.body).attr("src","/treatTable")
    });
    $("#setlan").click(function(){
        $("#mainframe",parent.document.body).attr("src","/LanguageTable")
    });
});
function showDialog(visible) {
    document.getElementById("load").hidden = !visible;
}