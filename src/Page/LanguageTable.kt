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

object LanguageTable {

    fun css(): String {
        return CSSBuilder().apply {
            body {
                margin = "0"
                backgroundColor = Color.white
            }
            rule(".title") {
                marginLeft = 10.px
                color = Color("#3d7e9a")
                fontSize = 30.px
            }
            rule(".note") {
                marginLeft = 10.px
                marginTop = 10.px
                color = Color("#3d7e9a")
                fontSize = 20.px
            }
            rule(".table") {
            }
            rule(".tabletit") {
                fontSize = 30.px
                textAlign = TextAlign.center
                color = Color("#323643")
            }
            body {
                margin = "0"
                backgroundColor = Color.gray
                backgroundImage = Image("url(\"/static-resources/background.jpg\")")
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
                h1(classes = "title") { +"更改多國語言設定" }
                h1(classes = "note") { +"note:" }
                h1(classes = "note") { +"所有的多國語言皆在此頁進行修改，id不得修改以及不可以兩個人同步進行更改，並且更改完後必須按下'確認更改'" }
                form {
                    id = "postform"
                    action = "/updateLanguage"
                    method = FormMethod.post
                    table("table") {
                        tr {
                            td {
                                h2(classes = "tabletit") { +"ID" }
                            }
                            td {
                                h2(classes = "tabletit") { +"EN" }
                            }
                            td {
                                h2(classes = "tabletit") { +"TW" }
                            }
                            td {
                                h2(classes = "tabletit") { +"ZH" }
                            }
                            td {
                                h2(classes = "tabletit") { +"JA" }
                            }
                            td {
                                h2(classes = "tabletit") { +"FR" }
                            }
                            td {
                                h2(classes = "tabletit") { +"ES" }
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
                                    input(InputType.text, null, null, "${i.id}|en", "input") {
                                        value = i.em
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|tw", "input") {
                                        value = i.tw
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|zh", "input") {
                                        value = i.zh
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|ja", "input") {
                                        value = i.ja
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|fr", "input") {
                                        value = i.fr
                                    }
                                }
                                td {
                                    input(InputType.text, null, null, "${i.id}|es", "input") {
                                        value = i.es
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

    fun getTd(): ArrayList<lanid> {
        val empty = ArrayList<lanid>()
        "select * from `lan` order by name asc".sqLiteQuery(object : callback {
            override fun callback(rs: ResultSet) {
                empty.add(
                    lanid(
                        "${rs.getString(1)}", "${rs.getString(2)}", "${rs.getString(3)}"
                        , "${rs.getString(4)}", "${rs.getString(5)}", "${rs.getString(6)}"
                        , "${rs.getString(7)}"
                    )
                )
            }
        })
        return empty
    }

    fun getid(): ArrayList<lanid> {
        val empty = ArrayList<lanid>()
        "select `name` from `lan` order by `name` asc".sqLiteQuery(object : callback {
            override fun callback(rs: ResultSet) {
                empty.add(
                    lanid(
                        "${rs.getString(1)}", "", "", "", "", "", ""
                    )
                )
            }
        })
        return empty
    }
    class lanid(var id:String,var em:String,var tw:String,var zh:String,var ja:String,var fr:String,var es:String)

    suspend fun updateResult(call: ApplicationCall) {
        val text = call.receive<Parameters>()
        for (i in getid()) {
            val sql="INSERT OR REPLACE INTO `lan` (name,en,tw,zh,ja,fr,es) " +
                    "VALUES(${i.id},'${text["${i.id}|en"]!!.replace("'","''")}','${text["${i.id}|tw"]!!.replace("'","''")}','${text["${i.id}|zh"]!!.replace("'","''")}','${text["${i.id}|ja"]!!.replace("'","''")}'" +
                    ",'${text["${i.id}|fr"]!!.replace("'","''")}','${text["${i.id}|es"]!!.replace("'","''")}');"
            println(sql)
            (sql).sqLiteUpdate()
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
