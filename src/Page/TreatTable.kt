package com.example.sqeng.Page

import ch.qos.logback.core.html.CssBuilder
import com.example.sqeng.dataBase.MysqlConnect
import com.example.sqeng.dataBase.callback
import com.example.sqeng.dataBase.sqLiteQuery
import com.example.sqeng.dataBase.sqLiteUpdate
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.Parameters
import io.ktor.request.receive
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.*
import java.sql.ResultSet
import java.util.*
import kotlin.collections.ArrayList

object TreatTable {

    fun css(): String {
        return CSSBuilder().apply {
            body {
                margin = "0"
                backgroundColor = Color.white
            }
            rule(".title") {
                marginLeft = 10.px
                color = Color.red
                fontSize = 30.px
            }
            rule(".note") {
                marginLeft = 10.px
                marginTop = 10.px
                color = Color.black
                fontSize = 20.px
            }
            rule(".table") {
            }
            body {
                margin = "0"
                backgroundColor = Color.gray
                backgroundImage = Image("url(\"/static-resources/background.jpg\")")
            }
            rule(".tabletit") {
                fontSize = 30.px
                textAlign = TextAlign.center
                color = Color("#323643")
            }

            rule("td") {
                borderColor = Color("#cccccc")
                borderStyle = BorderStyle.solid
                borderWidth = 3.px
                width = (100 / 7).vw
            }
            rule(".input") {
                alignContent = Align.center
                height = 50.px
                width = 98.pct
                fontSize = 20.px
                textAlign = TextAlign.center
            }
            rule(".idinput") {
                height = 50.px
                width = 98.pct
                fontSize = 20.px
                lineHeight = LineHeight("50px")
                color = Color.gray
                textAlign = TextAlign.center
            }
            button{
                height = 80.px
                color = Color.red
                marginTop = 20.px
                marginLeft = 1.pct
                width = 98.pct
                alignContent = Align.center
                textAlign = TextAlign.center
                fontSize = 30.px
            }
        }.toString()
    }

    suspend fun html(call: ApplicationCall) {
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/styles.css?type=TreatTable", type = "text/css")
                script(src = "https://www.w3school.com.cn/jquery/jquery-1.11.1.min.js") {}
                script(src = "http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js") {}
                script(src = "/static-resources/treatScript.js") {}
                link(rel = "stylesheet", href = "/static-resources/dialog.css", type = "text/css")
            }
            body {
                h1(classes = "title") { +"TreatCommand參數設定" }
                h1(classes = "note") { +"note:" }
                h1(classes = "note") { +"0x為16進制表示法，未加0x的則為10進制表示，id不可更改以及不可以兩個人同步進行更改，並且更改完後必須按下'確認更改'" }
                form {
                    id = "postform"
                    action = "/updateTreat"
                    method = FormMethod.post
                    table("table") {
                        tr {
                            td {
                                h2(classes = "tabletit") { +"Treatment ID" }
                            }
                            td {
                                h2(classes = "tabletit") { +"Duration" }
                            }
                            td {
                                h2(classes = "tabletit") { +"B Voltage" }
                            }
                            td {
                                h2(classes = "tabletit") { +"B Pulse" }
                            }
                            td {
                                h2(classes = "tabletit") { +"Mcolor LED" }
                            }
                            td {
                                h2(classes = "tabletit") { +"Mcolor Mode" }
                            }
                            td {
                                h2(classes = "tabletit") { +"Enable Instruction B Type" }
                            }
                        }
                        for (i in getTd()) {
                            tr {
                                td {
                                    h1("idinput") {
                                        + i.id
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|duration", "input") {
                                        value = i.dur
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|b_voltage", "input") {
                                        value = i.b_vol
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|b_pulse", "input") {
                                        value = i.b_pluse
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|m_color_led", "input") {
                                        value = i.m_red
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|m_color_mode", "input") {
                                        value = i.m_mode
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|enable_instruction", "input") {
                                        value = i.btype
                                    }
                                }
                            }
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
                button { +"確認更改" }
                script {
                    +"""${'$'}(document).ready(function (){
                        showDialog(false);
});"""
                }
            }

        }
    }

    fun getTd(): ArrayList<treatItem> {
        val empty = ArrayList<treatItem>()
        "select * from `treatment` order by id asc".sqLiteQuery(object : callback {
            override fun callback(rs: ResultSet) {
                empty.add(
                    treatItem(
                        "${rs.getString(1)}", "${rs.getString(2)}", "${rs.getString(3)}"
                        , "${rs.getString(4)}", "${rs.getString(5)}", "${rs.getString(6)}"
                        , "${rs.getString(7)}"
                    )
                )
            }
        })
        return empty
    }

    fun getid(): ArrayList<treatItem> {
        val empty = ArrayList<treatItem>()
        "select `id` from `treatment` order by id asc".sqLiteQuery(object : callback {
            override fun callback(rs: ResultSet) {
                empty.add(
                    treatItem(
                        "${rs.getString(1)}", "", "", "", "", "", ""
                    )
                )
            }
        })
        return empty
    }

    suspend fun updateResult(call: ApplicationCall) {
        val text = call.receive<Parameters>()
        for (i in getid()) {
            ("INSERT OR REPLACE INTO `treatment` (id,duration,b_voltage,b_pulse,m_color_led,m_color_mode,enable_instruction) " +
                    "VALUES('${text[i.id]}','${text["${i.id}|duration"]}','${text["${i.id}|b_voltage"]}','${text["${i.id}|b_pulse"]}','${text["${i.id}|m_color_led"]}'" +
                    ",'${text["${i.id}|m_color_mode"]}','${text["${i.id}|enable_instruction"]}');").sqLiteUpdate()
        }
        val sql=MysqlConnect()
        sql.stmt.executeUpdate("update setting set version='${Date().time}' where name='language'")
        sql.close()
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/styles.css?type=TreatTable", type = "text/css")
            }
            body {
                h1(classes = "title") { +"設定完成" }
            }
        }
    }
}

class treatItem(
    var id: String,
    var dur: String,
    var b_vol: String,
    var b_pluse: String,
    var m_red: String,
    var m_mode: String,
    var btype: String
)