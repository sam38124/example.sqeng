package com.example.sqeng.dataBase


import com.example.sqeng.PublicBeans
import com.jianzhi.taskhandler.TaskHandler
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class SqLiteConnector {
    var conn: Connection
    var stmt: Statement
    init {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(PublicBeans.sqlLiteRoute)
        stmt = conn.createStatement()
    }
    fun close() {
        conn.close()
        stmt.close()
    }
}

fun String.sqLiteUpdate(){
    val a=SqLiteConnector()
    a.stmt.executeUpdate(this)
    a.close()
}

fun String.sqLiteQuery(callback:callback){
    val a=SqLiteConnector()
    val rs=a.stmt.executeQuery(this)
    while (rs.next()){
        callback.callback(rs)
    }
    a.close()
}