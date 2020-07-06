package com.example.sqeng.Page

import com.example.sqeng.dataBase.MysqlConnect
import com.example.sqeng.ranDomKey
import com.google.gson.Gson
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.ContentType.Text.Html
import kotlinx.css.*
import kotlinx.css.br
import kotlinx.css.form
import kotlinx.css.h1
import kotlinx.css.h2
import kotlinx.css.input
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.document
import kotlinx.html.stream.HTMLStreamBuilder
import org.apache.http.client.methods.RequestBuilder.head
import org.intellij.lang.annotations.Language
import java.awt.Button
import java.awt.SystemColor.window

object SignPage {
    fun css(): String {

        return CSSBuilder().apply {
            rule(".centerlabel") {
                marginTop = 10.px
                marginBottom = 5.px
                fontSize = 30.px

            }
            rule(".signin") {
                height = 70.px
                width = 100.pct
                marginTop = 10.px
                backgroundColor = Color("#323643")
                color = Color.white
                fontSize = 30.px
            }
            rule(".left") {
                width = 50.vw
                height = 50.vh
                marginLeft = 50.vw

            }
            rule(".adminform") {
                width = 90.pct
                height = 100.px
                marginLeft = 5.pct
            }
            rule(".admin") {
                width = 100.pct
                height = 40.px
                fontSize = 30.px
                marginTop = 5.px
            }
            rule(".password") {
                width = 100.pct
                height = 40.px
                fontSize = 30.px
                marginTop = 5.px
            }
            rule(".center") {
                width = 600.px
                height = 400.px
                position = Position.relative
                marginLeft = 50.vw - 300.px
                marginTop = 50.vh - 200.px
                borderRadius = 10.px
                backgroundColor = Color.white
                borderWidth = 5.px
                borderStyle = BorderStyle.solid
                borderColor = Color.black
            }
            rule(".title") {
                marginLeft = 10.px
                marginTop = 1.px
                marginBottom = 5.px
                fontSize = 50.px
                color = Color("#323643")
                textAlign = TextAlign.center
            }
            body {
                margin = "0"
                backgroundColor = Color.gray
                backgroundImage = Image("url(\"/static-resources/topbar.png\")")
            }
            p {
                fontSize = 2.em
            }
            rule("p.myclass") {
                color = Color.blue
            }

        }.toString() + ".loading {\n" +
                "    border: 3px solid #3a3;\n" +
                "    border-right: 3px solid #fff;\n" +
                "    border-bottom: 3px solid #fff;\n" +
                "    height: 50px;\n" +
                "    width: 50px;\n" +
                "    border-radius: 50%;\n" +
                "    -webkit-animation: loading 1s infinite linear;\n" +
                "    -moz-animation: loading 1s infinite linear;\n" +
                "    -o-animation: loading 1s infinite linear;\n" +
                "    animation: loading 1s infinite linear;\n" +
                "}\n" +
                "@-webkit-keyframes loading {\n" +
                "    from {\n" +
                "        -webkit-transform: rotate(0deg);\n" +
                "    }\n" +
                "    to {\n" +
                "        -webkit-transform: rotate(360deg);\n" +
                "    }\n" +
                "}\n" +
                "@-moz-keyframes loading {\n" +
                "    from {\n" +
                "        -moz-transform: rotate(0deg);\n" +
                "    }\n" +
                "    to {\n" +
                "        -moz-transform: rotate(360deg);\n" +
                "    }\n" +
                "}\n" +
                "@-o-keyframes loading {\n" +
                "    from {\n" +
                "        -o-transform: rotate(0deg);\n" +
                "    }\n" +
                "    to {\n" +
                "        -o-transform: rotate(360deg);\n" +
                "    }\n" +
                "}\n" +
                "@keyframes loading {\n" +
                "    from {\n" +
                "        transform: rotate(0deg);\n" +
                "    }\n" +
                "    to {\n" +
                "        transform: rotate(360deg);\n" +
                "    }\n" +
                "}\n" +
                ".abgne-loading-20140104-2 {\n" +
                "    position: relative;\n" +
                "    height: 100px;\n" +
                "    width: 100px;\n" +
                "}\n" +
                ".abgne-loading-20140104-2 .loading {\n" +
                "    border: 6px solid #168;\n" +
                "    border-right: 6px solid #fff;\n" +
                "    border-bottom: 6px solid #fff;\n" +
                "    height: 100%;\n" +
                "    width: 100%;\n" +
                "    border-radius: 50%;\n" +
                "    -webkit-animation: loading 1s infinite linear;\n" +
                "    -moz-animation: loading 1s infinite linear;\n" +
                "    -ms-animation: loading 1s infinite linear;\n" +
                "    -o-animation: loading 1s infinite linear;\n" +
                "    animation: loading 1s infinite linear;\n" +
                "}\n" +
                ".abgne-loading-20140104-2 .word {\n" +
                "    color: #168;\n" +
                "    position: absolute;\n" +
                "    top: 0;\n" +
                "    left: 0;\n" +
                "    display: inline-block;\n" +
                "    text-align: center;\n" +
                "    font-size: 72px;\n" +
                "    line-height: 72px;\n" +
                "    font-family: 微軟正黑體, arial;\n" +
                "    margin: 18px 0 0 20px;\n" +
                "    padding: 0;\n" +
                "}" + "\n"
    }

    suspend fun html(call: ApplicationCall) {
        var results = ""
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/styles.css?type=SignPage", type = "text/css")
                link(rel = "stylesheet", href = "/static-resources/dialog.css", type = "text/css")
                script(src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js") {}
                script(src = "/static-resources/signScript.js") {}
            }.toString()
            println("head:")
            body {
                div(classes = "center") {
                    h1(classes = "title") { +"SQCHECK後台管理系統" }
                    div( "adminform") {
                        h2(classes = "centerlabel") { +"請輸入帳號" }
                        input(InputType.text, null, null, "admin", "admin")
                        br
                        h2(classes = "centerlabel") { +"請輸入密碼" }
                        input(InputType.password, null, null, "password", "password")
                        br
                        button  (classes = "signin") {
                            + "登入"
                        }
                    }

                }
                div(classes = "loadindview") {
                    id = "load"
                    div(classes = "backwhite") {}
                    div(classes = "cssload-loader") {
                        +"請稍候..."
                    }
                }


                script {
                    +"""${'$'}(document).ready(function (){
                        showDialog(false);
});"""
                }
            }
        }

    }
    fun getSignToken(admin:String,password:String):String{
        val sql=MysqlConnect()
        val re=sql.stmt.executeQuery("select count(1) from `manager` where `account`='$admin' and `password`='$password'")
        re.next()
        val result=re.getString("count(1)")
        val  map=mutableMapOf<String,String>()
        map["result"]=result
        map["key"]=if(result=="1") 10.ranDomKey() else "false"
        sql.close()
        return Gson().toJson(map)
    }
}