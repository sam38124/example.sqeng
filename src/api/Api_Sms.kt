package com.example.sqeng.api

import com.example.sqeng.dataBase.MysqlConnect
import com.example.sqeng.PublicBeans
import com.example.sqeng.fixLan
import com.example.sqeng.ranDomKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jianzhi.taskhandler.TaskHandler
import com.jianzhi.taskhandler.runner
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset

object Api_Sms {
    var item = mutableMapOf<String, String>()
    fun sendMessage(phone: String?, language: String = "en"): String {
        try {
            var string = ""
            if (phone == null) {
                return "102".fixLan(language)
            }
            TaskHandler.newInstance.runTaskTimer("sms$phone", 30.0, object : runner {
                override fun run() {
                    val readomKey = 7.ranDomKey()
                    val url = URL("https://rest.nexmo.com/sms/json")
                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.setRequestMethod("POST")
                    val urlParameters =
                        "api_secret=${PublicBeans.smsSecret}&api_key=${PublicBeans.api_key}&to=${phone}&text=" + URLEncoder.encode(
                            "103".fixLan(language) + readomKey,
                            "utf-8"
                        ) + "&from=Vonage APIs"
                    //            request.setCharacterEncoding(“UTF-8”);
                    //发送Post请求
                    conn.setDoOutput(true)
                    val wr = DataOutputStream(conn.getOutputStream())
                    wr.writeBytes(urlParameters)
                    wr.flush()
                    wr.close()
                    System.out.print("requst" + conn.getResponseCode())
                    if (conn.getResponseCode() == 200) {
                        val reader = BufferedReader(InputStreamReader(conn.getInputStream(), "utf-8"))
                        var line: String? = null
                        val strBuf = StringBuffer()
                        while (reader.readLine().also({ line = it }) != null) {
                            strBuf.append(line)
                        }
                        reader.close()
                        val json = strBuf.toString()
                        val gson = Gson()
                        val tutorialMap: Map<String, Any> =
                            gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
                        val se: Map<String, Any> = gson.fromJson(
                            ((tutorialMap["messages"]) as ArrayList<*>)[0].toString(),
                            object : TypeToken<Map<String, Any>>() {}.type
                        )
                        string = if ((se["status"] as Double) == 0.0) {
                            "true"
                        } else {
                            "false"
                        }
                        if (string == "true") {
                            item[phone] = readomKey
                        }
                    } else {
                        string = "false"
                    }
                }
            }, object : runner {
                override fun run() {
                    string = "101".fixLan(language)
                }
            })
            return string
        } catch (e: Exception) {
            e.printStackTrace()
            return "false"
        }
    }

    fun checkSMS(
        phone: String,
        code: String,
        state: String,
        gender: String,
        age: String,
        senstive: String,
        serial: String
    ): String {
        return if (item[phone] == code || code == "guester") {
            try {
                val sql = MysqlConnect()
                sql.stmt.executeLargeUpdate(
                    "insert into users (serial," +
                            "state,phone,age,gender,senestive) values ('$serial','$state','$phone','$age','$gender','$senstive')"
                )
                sql.close()
                "true"
            } catch (e: Exception) {
                e.printStackTrace()
                "false"
            }
        } else {
            "false"
        }
    }

}