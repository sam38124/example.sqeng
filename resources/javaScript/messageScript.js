function scroll() {
    window.scrollTo(0, document.getElementById("list").scrollHeight);
}

$(document).ready(function () {
    $("#camera").click(function () {
        // alert('典籍camear!!');
        // $("#uploadFile").submit();
    });
    $(".send").click(function () {
        if ($(".message").val() === '' && $(".file").val() === 'nodata') {
            alert('訊息不得為空!!');
            return
        }
        showDialog(true);
        $.post("/InsertMessage",
            {
                tableName: $(".tableName").val(),
                serial: $(".serial").val(),
                message: $(".message").val(),
                file: $(".file").val()
            },
            function (data, status) {
                showDialog(false);
                if (data === 'false') {
                    alert('傳送失敗');
                } else {
                    location.reload();
                }
            });
    });
});
const myVar = setInterval(myTimer, 3000);
var maxid = '100'

function myTimer() {
    topMessage();
}

function topMessage() {
    $.get("/Api_Top_Message",
        {
            serial: $(".tableName").val(),
            id: $(".maxid").val()
        },
        function (data, status) {

            var obj = jQuery.parseJSON(data);
            for (var x = 0; x < obj.length; x++) {
                document.getElementById("maxid").value = obj[0].id;
                console.log(obj[0].id);
                console.log(obj[0].admin);
                console.log(obj[0].message);
                console.log(obj[0].file);
                console.log(obj[0].time);
                console.log(obj[0].head);

            }

        });
}

function showDialog(visible) {
    document.getElementById("load").hidden = !visible;
}

document.onkeydown = function (e) {  //對整個頁面文件監聽
    var keyNum = window.event ? e.keyCode : e.which;  //獲取被按下的鍵值
//判斷如果使用者按下了回車鍵（keycody=13）
    if (keyNum == 13) {
        $(".send").click();
    }
//判斷如果使用者按下了空格鍵(keycode=32)，
}
function upload(e) {
    alert('上船檔案');
    var file = e.files[0];
    if (!file) {
        return;
    }

    // var file_data = file[0];   //取得上傳檔案屬性
    // var form_data = new FormData();  //建構new FormData()
    // form_data.append('file', file_data);  //吧物件加到file後面
    // showDialog(true);
    // $.ajax({
    //     url: '/UploadImage',
    //     cache: false,
    //     contentType: false,
    //     processData: false,
    //     data: form_data,     //data只能指定單一物件
    //     type: 'post',
    //     success: function(data){
    //         showDialog(false);
    //         alert( data );
    //     }});
    var reader = new FileReader();
    reader.onload = function() {
        showDialog(true);
        var arrayBuffer = this.result,
            array = new Uint8Array(arrayBuffer),
            binaryString = String.fromCharCode.apply(null, array);

        console.log(binaryString);
        $.post("/UploadImage",
            {

                file:'幹你娘'
            },
            function(data,status){
                showDialog(false);
                alert( data );
            });
    }
    reader.readAsArrayBuffer(file);
    //檔案上傳
    //...
    //檔案上傳


    //上傳後將檔案清除
    e.value = '';
}