package com.example.sqeng

import com.example.sqeng.dataBase.MysqlConnect

object PublicBeans {
    //資料庫帳號
    val dataUSER = "eva92061"
    //資料庫密碼
    val dataPASS = "sam28520"
    //資料庫路徑
    val dataRoot= "jdbc:mysql://180.177.242.55:3306/sqcheck?autoReconnect=true&useSSL=false&serverTimezone=UTC"
    //客服資料庫
    val customerRoot= "jdbc:mysql://180.177.242.55:3306/customermessage?autoReconnect=true&useSSL=false&serverTimezone=UTC"
     //SMS_KEY
    val api_key= "99e6f868"
    //sms_Screat"
    val smsSecret= "Zpgz7eHnGf7l3g9r"
    //sqLite路徑
    val sqlLiteRoute :String get() {
        val sql=MysqlConnect()
        val re=sql.stmt.executeQuery("select * from setting where name='language'")
        re.next()
        return "jdbc:sqlite:${re.getString("route")}"
    }
    //多國語言版本
    val lanVersion :String get() {
        val sql=MysqlConnect()
        val re=sql.stmt.executeQuery("select * from setting where name='language'")
        re.next()
        return re.getString("version")
    }
}