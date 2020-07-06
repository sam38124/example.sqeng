package com.example.sqeng.Page

import com.example.sqeng.api.Api_Customer_Message
import com.example.sqeng.unicodeToString
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.html.*

object Page_Customer_Message_Detail {
    fun css(): String {
        return CSSBuilder().apply {
            rule(".name") {
                marginTop = 10.px
                marginBottom = 5.px
                color = Color.blue
            }
            rule(".you") {
                position = Position.relative
                float = Float.left
                display = Display.inlineBlock
                marginTop = 10.px
                maxWidth = 80.vw
                wordWrap = WordWrap.breakWord
                backgroundColor = Color.white
                padding = "10px"
                marginRight = 20.pct
                borderRadius = LinearDimension("10px")
            }
            rule(".me") {
                position = Position.relative
                float = Float.right
                display = Display.inlineBlock
                marginTop = 10.px
                marginRight = 10.px
                wordWrap = WordWrap.breakWord
                maxWidth = 80.vw
                backgroundColor = Color.blue
                padding = "10px"
                borderRadius = LinearDimension("10px")
            }
            rule(".content") {
                wordWrap = WordWrap.breakWord
                color = Color.black
                marginTop = 5.px
                fontSize = 20.px
            }
            rule(".contentme") {
                wordWrap = WordWrap.breakWord
                color = Color.white
                fontSize = 20.px
            }
            rule(".block") {
                width = 100.pct
                display = Display.inlineBlock
                position = Position.relative
            }
            rule(".list") {
                marginLeft = 10.px
                marginBottom = 120.px
                overflow = Overflow.auto
                position = Position.absolute
            }
            rule(".bottom") {
                overflow = Overflow.hidden;
                display = Display.inlineBlock
                height = 100.px
                marginLeft = 0.px
                width = 101.vw
                bottom = 0.vw
                backgroundColor = Color.white
                position = Position.fixed
            }
            body {
                margin = "0"
                backgroundImage = Image("url(\"/static-resources/linebk.jpeg\")")
            }
            rule(".camera") {
                marginTop = 20.px
                cursor = Cursor.pointer
                marginLeft = 10.px
                height = 60.px
                width = 60.px
            }
            rule(".send") {
                position = Position.absolute
                float = kotlinx.css.Float.right
                cursor = Cursor.pointer
                marginTop = 25.px
                right = 40.px
                height = 60.px
                width = 60.px
            }
            rule(".message") {
                position = Position.absolute
                marginTop = 20.px
                top = 0.px
                width = 100.vw - 200.px
                marginLeft = 10.px
                marginRight = 70.px
                borderColor = Color.gray
                borderWidth = 1.px
                fontSize = 17.px
                padding = "5px"
                height = 60.px
            }
            rule(".tableName") {
                position = Position.absolute
                visibility = Visibility.hidden
            }
            rule(".serial") {
                position = Position.absolute
                visibility = Visibility.hidden
            }
            rule(".file") {
                position = Position.absolute
                visibility = Visibility.hidden
            }
            rule(".maxid") {
                position = Position.absolute
                visibility = Visibility.hidden
            }
            rule(".image") {
                maxWidth = 100.pct
            }
            rule("#uploadFile") {
                position = Position.absolute
                visibility = Visibility.hidden
            }

        }.toString()
    }

    suspend fun html(call: ApplicationCall, customeradmin: String = "nodata", admin: String = "root") {
        val message = Api_Customer_Message(customeradmin).getMessageItem("-1")
        var maxid = ""
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "/static-resources/dialog.css", type = "text/css")
                link(rel = "stylesheet", href = "/styles.css?type=Page_Customer_Message_Detail", type = "text/css")
                script(src = "http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js") {}
                script(src = "/static-resources/messageScript.js") {}
            }
            body {

                div(classes = "list") {
                    id = "list"
                    for (i in message) {
                        if (maxid.isEmpty()) {
                            maxid = message[message.size - 1].id
                        }
                        if (admin == i.admin) {
                            div(classes = "block") {
                                div(classes = "me") {
                                    h1(classes = "contentme") {
                                        +i.message.unicodeToString()!!
                                    }
                                    if (i.file != "nodata") {
                                        img(classes = "image") {
                                            src = i.file
                                        }
                                    }
                                }
                                br { }
                            }
                        } else {
                            div(classes = "block") {
                                div(classes = "you") {
                                    h1(classes = "name") {
                                        +i.admin
                                    }
                                    h1(classes = "content") {
                                        +i.message.unicodeToString()!!
                                    }
                                    if (i.file != "nodata") {
                                        img(classes = "image") {
                                            src = i.file
                                        }
                                    }
                                }
                                br { }
                            }
                        }
                    }
                }
                input(classes = "tableName") { value = customeradmin }
                input(classes = "serial") { value = admin }
                input(classes = "file") { value = "nodata" }
                input(classes = "maxid", name = "maxid") {
                    id = "maxid"
                    value = maxid
                }

                div("bottom") {
                    img(classes = "camera") {
                        onClick = "selectImage.click()"
                        id = "camera"
                        src = "/static-resources/camera_blue.png"
                    }
                    input(classes = "message") {}
                    input(type = InputType.file, name = "myfile") {
                        accept = "image/gif, image/jpeg, image/png"
                        onChange = "upload(this)"
                        id = "selectImage"
                    }

                    img(classes = "send") { src = "/static-resources/send_blue.png" }
                }
                script(type = "text/javascript") {
                    defer = true
                    +"""${'$'}(document).ready(function (){
                        showDialog(false);
                        scroll();
});"""
                }
                div(classes = "loadindview") {
                    id = "load"
                    div(classes = "backwhite") {}
                    div(classes = "cssload-loader") {
                        +"請稍候..."
                    }
                }
            }
        }
    }
}