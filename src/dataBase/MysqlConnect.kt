package com.example.sqeng.dataBase

import com.example.sqeng.PublicBeans
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class MysqlConnect(var root:String=PublicBeans.dataRoot) {
    var conn: Connection
    var stmt: Statement
    init {
        Class.forName("com.mysql.jdbc.Driver")
        conn = DriverManager.getConnection(root, PublicBeans.dataUSER, PublicBeans.dataPASS)
        stmt = conn.createStatement()
    }
    fun close() {
        conn.close()
        stmt.close()
    }
}

interface callback {
    fun callback(rs: ResultSet)
}

fun String.mySqlQuery(callback: callback){
    val a= MysqlConnect()
    val rs=a.stmt.executeQuery(this)
    while (rs.next()){
        callback.callback(rs)
    }
    a.close()
}