package com.example.sqeng.Page

import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.*

object Page_Main {
    fun css(): String {
        return CSSBuilder().apply {
            rule(".left") {
                backgroundColor = Color("#4CAF50")
                position = Position.fixed
                width = 250.px
                height = 100.vh
            }
            body {
                margin = "0"
                backgroundColor = Color.gray
                backgroundImage = Image("url(\"/static-resources/topbar.png\")")
            }
            rule(".leftItem") {
                cursor = Cursor.pointer
                marginTop = 15.px
                textAlign = TextAlign.center
                width = 90.pct
                marginLeft = 5.pct
                color = Color.white
                fontSize = 20.px
                backgroundColor = Color("none")
            }
            rule(".divider") {
                backgroundColor = Color.white
                height = 1.px
                width = 90.pct
                marginLeft = 5.pct
            }
            rule(".frame"){
                position = Position.absolute
                backgroundColor = Color.white
                marginLeft = 250.px
                height = 100.vh
                width = 100.vw - 250.px
            }
            body {
                margin = "0"
            }
        }.toString()
    }

    suspend fun html(call: ApplicationCall) {
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/styles.css?type=Page_Main", type = "text/css")
                link(rel = "stylesheet", href = "/static-resources/dialog.css", type = "text/css")
                script(src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js") {}
                script(src = "/static-resources/pageScript.js"){}
            }
            body {
                div(classes = "left") {
                    h1(classes = "leftItem") {
                        id="treat"
                        +"Treatment設定"
                    }
                    div(classes = "divider")
                    h1(classes = "leftItem") {
                        id="setlan"
                        +"多國語言設定"
                    }
                    div(classes = "divider")
                    h1(classes = "leftItem") {
                        +"Instruction設定"
                    }
                    div(classes = "divider")
                    h1(classes = "leftItem") {
                        +"線上客服"
                    }
                    div(classes = "divider")
                }
                iframe(classes = "frame"){
                    id="mainframe"
                    src = "/treatTable"
                }
            }
        }
    }
}