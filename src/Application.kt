package com.example.sqeng

import com.example.sqeng.Page.*
import com.example.sqeng.Page.SignPage.css
import com.example.sqeng.api.*
import com.example.sqeng.api.Api_Sms.checkSMS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.header
import io.ktor.response.respondOutputStream
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import kotlinx.css.*
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import kotlinx.html.*
import kotlinx.html.dom.document
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URLEncoder


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(Apache) {
    }

    routing {
        static("static-resources") {
            resources("css")
            resources("javaScript")
            resources("image")
        }
        get("/") {

            call.respondText("18", contentType = ContentType.Text.Plain)
        }

        get("/Sign_page") {
            SignPage.html(call)
        }

        get("/styles.css") {
            when (call.parameters["type"]) {
                "SignPage" -> call.respondText(SignPage.css(), contentType = ContentType.Text.CSS)
                "TreatTable" -> call.respondText(TreatTable.css(), contentType = ContentType.Text.CSS)
                "Page_Main" -> call.respondText(Page_Main.css(), contentType = ContentType.Text.CSS)
                "Page_Customer_Message"->call.respondText(Page_Customer_Message.css(), contentType = ContentType.Text.CSS)
                "Page_Customer_Message_Detail"->call.respondText(Page_Customer_Message_Detail.css(), contentType = ContentType.Text.CSS)
            }
        }
        //發送SMS
        get("/sms") {
            call.respondText("true", contentType = ContentType.Text.Plain)
//            if(call.parameters["lan"]==null){
//                call.respondText(sendMessage(call.parameters["phone"]), contentType = ContentType.Text.Plain)
//            }else{
//                call.respondText(sendMessage(call.parameters["phone"],call.parameters["lan"]!!), contentType = ContentType.Text.Plain)
//            }
        }
        get("/checkSMS") {
            if (call.parameters["phone"] != null && call.parameters["code"] != null) {
                call.respondText(
                    checkSMS(
                        call.parameters["phone"]!!,
                        call.parameters["code"]!!,
                        call.parameters["state"]!!,
                        call.parameters["gender"]!!
                        ,
                        call.parameters["age"]!!,
                        call.parameters["senstive"]!!,
                        call.parameters["serial"]!!
                    ), contentType = ContentType.Text.Plain
                )
            } else {
                call.respondText("false", contentType = ContentType.Text.Plain)
            }
        }
        post("/Validate") {
            println("init")
            val text = call.receive<Parameters>()
            call.respondText(
                Api_Validate.validateUser(text["serial"]!!),
                contentType = ContentType.Text.Plain
            )
        }
        get("/UserInfo") {
            call.respondText(
                Api_UserInfo.getUserInfo(call.parameters["serial"]!!),
                contentType = ContentType.Text.Plain
            )
        }
        get("/SetUserInfo") {
            call.respondText(
                Api_UserInfo.setUserInfo(call.parameters["serial"]!!, call.parameters["json"]!!),
                contentType = ContentType.Text.Plain
            )
        }
        get("/getlan") {
            call.respondText(Api_Get_Langusge.getLanguage(), contentType = ContentType.Text.Plain)
        }
        get("/lanVersion") {
            call.respondText(PublicBeans.lanVersion, contentType = ContentType.Text.Plain)
        }
        get("/Api_Customer_Message") {
            call.respondText(
                Api_Customer_Message(call.parameters["serial"]!!).getMessage(call.parameters["id"]!!),
                contentType = ContentType.Text.Plain
            )
        }
        get("/Api_Top_Message") {
            call.respondText(
                Api_Customer_Message(call.parameters["serial"]!!).getTopMessage(call.parameters["id"]!!),
                contentType = ContentType.Text.Plain
            )
        }
        post("/UploadImage") {
//            println(call.parameters["serial"])
            val text = call.receive<Parameters>()
            println(text["file"]!!)
            call.respondText(Api_Image.insertImage(call.receiveStream().readBytes()),
                contentType = ContentType.Text.Plain
            )
        }
        get("/getFile") {
            val filename = call.parameters["path"]
            val file = File("$filename")
            if (file.exists()) { //檢驗檔案是否存在
                try {
                    call.response.header(
                        "Content-Disposition",
                        "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"")
                    call.respondOutputStream(contentType = ContentType.Text.Plain, status = HttpStatusCode.OK,
                        producer = {
                            val output: OutputStream = this
                            val `in`: InputStream = FileInputStream(file)
                            val b = ByteArray(2048)
                            var len: Int
                            while (`in`.read(b).also { len = it } > 0) {
                                output.write(b, 0, len)
                            }
                            `in`.close()
                            output.flush()
                            output.close() //關閉串流
                        })
                } catch (ex: Exception) {
                    call.respondText("false" + ex.printStackTrace())
                }
            } else {
                call.respondText("false no file")
            }
        }
        post("/InsertMessage") {
            val text = call.receive<Parameters>()
            call.respondText(
                Api_Customer_Message(text["tableName"]!!).insertMessage(
                    text["serial"]!!,
                    text["message"]!!,
                    text["file"]!!
                ),
                contentType = ContentType.Text.Plain
            )
        }
        get("/treatTable") {
            TreatTable.html(call)
        }
        post("/updateTreat"){
           TreatTable.updateResult(call)
        }
        post("/updateLanguage"){
            LanguageTable.updateResult(call)
        }
        post("/ManagerSign"){
            val text = call.receive<Parameters>()
            call.respondText(SignPage.getSignToken(text["admin"]!!,text["password"]!!),contentType = ContentType.Text.Plain)
        }
        get("/Page_Main"){
            Page_Main.html(call)
        }
        get("/LanguageTable"){
            LanguageTable.html(call)
        }
        get("/Page_Customer_Message"){
            Page_Customer_Message.html(call)
        }
        get("/Page_Customer_Message_Detail"){
            Page_Customer_Message_Detail.html(call)
        }
        post("/uploadFile"){

        }
    }
}

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
//    return CSSBuilder().apply(builder).toString()
}
