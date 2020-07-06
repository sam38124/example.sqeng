$(document).ready(function(){
    $("button").click(function(){
        showDialog(true)
       document.getElementById("postform").submit()
    });
});
function showDialog(visible) {
    document.getElementById("load").hidden = !visible;
}