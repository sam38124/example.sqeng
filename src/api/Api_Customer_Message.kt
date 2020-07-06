package com.example.sqeng.api

import com.example.sqeng.PublicBeans
import com.example.sqeng.dataBase.MysqlConnect
import com.example.sqeng.model.message
import com.google.gson.Gson
import io.ktor.http.ContentType.Application.Json

class Api_Customer_Message(var tableName: String) {
    var sql = MysqlConnect(PublicBeans.customerRoot)

    init {
        sql.stmt.executeUpdate(
            "CREATE TABLE if not exists `$tableName` (\n" +
                    "        `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "        `admin` varchar(45) NOT NULL,\n" +
                    "        `file` varchar(500) NOT NULL DEFAULT 'nodata',\n" +
                    "        `message` varchar(5000) NOT NULL,\n" +
                    "        `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "        PRIMARY KEY (`id`)\n" +
                    "        ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
        )
    }

    fun getMessage(id: String): String {
        var last = ""
        if (id != "-1") {
            last = "and `customermessage`.`$tableName`.id<$id"
        }
        val rs = sql.stmt.executeQuery(
            "select `customermessage`.`$tableName`.*,`sqcheck`.`users`.* from `customermessage`.`$tableName`,`sqcheck`.`users`" +
                    "where `sqcheck`.`users`.`serial`=`customermessage`.`$tableName`.`admin` $last order by `customermessage`.`$tableName`.id desc limit 0,50"
        )
        val daraArray = ArrayList<message>()
        while (rs.next()) {
            daraArray.add(
                message(
                    rs.getString("id"), rs.getString("admin"), rs.getString("message")
                    , rs.getString("file"), rs.getString("time")
                    , rs.getString("head"), rs.getString("middlename")
                )
            )
        }
        val a = Gson().toJson(daraArray)
        sql.close()
        return a
    }
    fun getMessageItem(id: String): ArrayList<message> {
        var last = ""
        if (id != "-1") {
            last = "and `customermessage`.`$tableName`.id<$id"
        }
        val rs = sql.stmt.executeQuery(
            "select `customermessage`.`$tableName`.*,`sqcheck`.`users`.* from `customermessage`.`$tableName`,`sqcheck`.`users`" +
                    "where `sqcheck`.`users`.`serial`=`customermessage`.`$tableName`.`admin` $last order by `customermessage`.`$tableName`.id desc limit 0,50"
        )
        val daraArray = ArrayList<message>()
        while (rs.next()) {
            daraArray.add(
                message(
                    rs.getString("id"), rs.getString("admin"), rs.getString("message")
                    , rs.getString("file"), rs.getString("time")
                    , rs.getString("head"), rs.getString("middlename")
                )
            )
        }
        sql.close()
        daraArray.reverse()
        return daraArray
    }
    fun getTopMessage(id: String): String {
        var last = ""
        if (id != "-1") {
            last = "and `customermessage`.`$tableName`.id>$id"
        }
        val sq="select `customermessage`.`$tableName`.*,`sqcheck`.`users`.* from `customermessage`.`$tableName`,`sqcheck`.`users`" +
                "where `sqcheck`.`users`.`serial`=`customermessage`.`$tableName`.`admin` $last order by `customermessage`.`$tableName`.id desc limit 0,50"
        val rs = sql.stmt.executeQuery(sq)
        val daraArray = ArrayList<message>()
        while (rs.next()) {
            daraArray.add(
                message(
                    rs.getString("id"), rs.getString("admin"), rs.getString("message")
                    , rs.getString("file"), rs.getString("time")
                    , rs.getString("head"), rs.getString("middlename")
                )
            )
        }
        val a = Gson().toJson(
            daraArray
        )
        sql.close()
        return a
    }

    fun insertMessage(serial: String, message: String, file: String): String {
        try {
            val re =
                sql.stmt.executeUpdate("insert into `customermessage`.`$tableName` (admin,message,file) values ('$serial','$message','$file')")
            sql.close()
            return "true"
        } catch (e: Exception) {
            e.printStackTrace()
            return "false"
        }
    }
}