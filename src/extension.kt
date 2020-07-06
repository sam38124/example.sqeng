package com.example.sqeng

import com.example.sqeng.dataBase.callback
import com.example.sqeng.dataBase.sqLiteQuery
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respondOutputStream
import io.ktor.response.respondText
import java.io.*
import java.net.URLEncoder
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.text.ParseException
import java.util.*


fun Int.ranDomNumber( count:Int):String{
    var a=""
    while(a.length<this){
        a+=(arrayOf("0","1","2","3","4","5","6","7","8","9").random())
    }
    return a
}
fun Int.ranDomKey():String{
    var a=""
    while(a.length<this){
        a+=(arrayOf("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"
        ,"q","r","s","t","u","v","w","x","y","z").random())
    }
    return a
}

fun String.fixLan(a:String):String{
    return try {
        var f=""
        "select $a from lan where name=$this".sqLiteQuery(object :callback{
            override fun callback(rs: ResultSet) {
                f=rs.getString(a)
            }
        })
        f
    } catch (e: SQLException) {
        println(e.message)
        this
    }
}

//將String轉換成HexString
fun String.toHex(): String {
    val sb = StringBuilder()
    for (b in this.toByteArray()) {
        sb.append(String.format("%02X", b))
    }
    return sb.toString()
}

//将utf-8的汉字转换成unicode格式汉字码
fun String.stringToUnicode(): String? {
    var str = this
    str = str ?: ""
    var tmp: String
    val sb = StringBuffer(1000)
    var c: Char
    var i: Int
    var j: Int
    sb.setLength(0)
    i = 0
    while (i < str.length) {
        c = str[i]
        sb.append("\\\\u")
        j = c.toInt() ushr 8 //取出高8位
        tmp = Integer.toHexString(j)
        if (tmp.length == 1) sb.append("0")
        sb.append(tmp)
        j = c.toInt() and 0xFF //取出低8位
        tmp = Integer.toHexString(j)
        if (tmp.length == 1) sb.append("0")
        sb.append(tmp)
        i++
    }
    return String(sb)
}

//将unicode的汉字码转换成utf-8格式的汉字
fun String.unicodeToString(): String? {
    val string = StringBuffer()
    val hex = this.replace("\\\\u", "\\u").split("\\u").toTypedArray()
    for (i in 1 until hex.size) { //        System.out.println(hex[i].length());
        if (hex[i].length > 4) {
            string.append(hex[i].substring(4))
        }
        val data = hex[i].substring(0, 4).toInt(16)
        // 追加成string
        string.append(data.toChar())
    }
    return if (hex.size <= 1) this else string.toString()
}
//將Byte轉換成HexString
fun ByteArray.toHex(): String {
    val sb = StringBuilder()
    for (b in this) {
        sb.append(String.format("%02X", b))
    }
    return sb.toString()
}