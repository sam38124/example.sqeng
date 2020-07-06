package com.example.sqeng.api

import com.example.sqeng.dataBase.MysqlConnect

object Api_Validate {
    fun validateUser(a: String): String {
        return try {
            val sql = MysqlConnect()
            val rs = sql.stmt.executeQuery("select count(1) from users where serial='$a'")
            rs.next()
            val result = rs.getString("count(1)")
            sql.close()
            if(result=="1") "true" else "false"
        } catch (e: Exception) {
            e.printStackTrace()
            "false";
        }
    }
}