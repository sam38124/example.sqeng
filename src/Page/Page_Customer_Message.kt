package com.example.sqeng.Page

import com.example.sqeng.api.Api_Customer_Message
import com.example.sqeng.dataBase.MysqlConnect
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import kotlinx.css.*
import kotlinx.html.*

object Page_Customer_Message {
    fun css(): String {
        return CSSBuilder().apply {
            body {
                margin = "0"
            }
            rule(".title") {
                marginLeft = 12.px
                color = Color.black
                fontSize = 30.px
            }
            rule(".tit"){
                fontSize = 30.px
                color = Color("#3F51B5")
                fontStyle = FontStyle("bold")
            }
            rule(".item") {
                cursor = Cursor.pointer
                marginTop = 10.px
                borderRadius = LinearDimension("2px")
                backgroundColor = Color.black
                borderRadius = 10.px
                backgroundColor = Color.white
                borderWidth = 3.px
                borderStyle = BorderStyle.solid
                borderColor = Color("#3d7e9a")
                height = 150.px
                marginLeft = 15.px
                padding = "1px"
                width = 100.vw - 40.px
            }
            rule(".name") {
                marginLeft = 10.px
                marginTop = 10.px
                marginBottom = 0.px
                fontSize = 30.px
                color = Color("#3F51B5")
                fontStyle = FontStyle("bold")

            }
            rule(".reader0") {
                marginLeft = 10.px
                marginTop = 10.px
                marginBottom = 0.px
                fontSize = 30.px
                color = Color.red
                fontStyle = FontStyle("bold")
            }
            rule(".reader1") {
                marginLeft = 10.px
                marginTop = 10.px
                marginBottom = 0.px
                fontSize = 30.px
                color = Color.red
                fontStyle = FontStyle("bold")
            }
            rule(".content") {
                overflow = Overflow.hidden
                marginLeft = 10.px
                marginTop = 10.px
                fontSize = 25.px
                width = 100.pct
                height = 40.px
                color = Color.black
                fontStyle = FontStyle("bold")
                whiteSpace = WhiteSpace.nowrap
                textOverflow = TextOverflow.ellipsis

            }
            rule(".time") {
                alignContent = Align.flexEnd
                textAlign = TextAlign.end
                color = Color.gray
                fontSize = 15.px
                marginBottom = 10.px
                marginRight = 10.px
            }
        }.toString()
    }

    suspend fun html(call: ApplicationCall) {
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/styles.css?type=Page_Customer_Message", type = "text/css")
                script(src = "http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js") {}
            }
            body {
                h1(classes = "title") { +"回覆提問" }
                for(i in getMessage()){
                    div(classes = "item") {
                        div {
                            table {
                                tr {
                                    td {
                                        if(i.reader=="1"){
                                            h1(classes = "reader1") {
                                                +"-已讀-"
                                            }
                                        }else{
                                            h1(classes = "reader0") {
                                                +"-未讀-"
                                            }
                                        }
                                    }
                                    td {
                                        h1(classes = "name") {
                                            +"來自${i.admin}的提問"
                                        }
                                    }
                                }
                            }
                        }

                        h1(classes = "content") {
                            +i.tit
                        }
                        h1(classes = "time") {
                            +i.time
                        }
                    }
                }

            }
        }
    }

    fun getMessage(): ArrayList<messageItem> {
        val empty = ArrayList<messageItem>()
        val sql = MysqlConnect()
        val re = sql.stmt.executeQuery("select  * from message order by id desc limit 0,50")
        while (re.next()) {
            empty.add(
                messageItem(
                    re.getString("id"),
                    re.getString("admin"),
                    re.getString("tit"),
                    re.getString("reader"),
                    re.getString("time")
                )
            )
        }
        sql.close()
        return empty
    }
    suspend fun messageHtml(call: ApplicationCall) {
//        Api_Customer_Message(call.parameters["admin"]!!).getMessage("-1")
    }
}

class messageItem(var id: String, var admin: String, var tit: String, var reader: String,var time:String)
class message(var id:String,var admin:String,var message:String,var file:String,var time:String,var head:String,var pick:String)