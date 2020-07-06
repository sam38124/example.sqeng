package com.example.sqeng.api

import com.example.sqeng.dataBase.MysqlConnect
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

object Api_UserInfo {
    fun getUserInfo(a: String): String {
        return try {
            val sql = MysqlConnect()
            val rs = sql.stmt.executeQuery("select * from `users` where serial='$a'")
            rs.next()
            val  map=mutableMapOf<String,String>()
            map["state"]=rs.getString("state")
            map["phone"]=rs.getString("phone")
            map["age"]=rs.getString("age")
            map["gender"]=rs.getString("gender")
            map["senestive"]=rs.getString("senestive")
            map["firstname"]=rs.getString("firstname")
            map["lastname"]=rs.getString("lastname")
            map["middlename"]=rs.getString("middlename")
            sql.close()
            if(map.isEmpty()){
                return "false"
            }
            return Gson().toJson(map).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "false";
        }
    }

    fun setUserInfo(a: String,json:String): String {
        return try {
            val map: Map<String, String> =
                Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
            val sql = MysqlConnect()
            sql.stmt.executeUpdate("update users set state='${map["state"]}',phone='${map["phone"]}',age='${map["age"]}'" +
                    ",gender='${map["gender"]}',senestive='${map["senestive"]}',firstname='${map["firstname"]}',lastname='${map["lastname"]}'" +
                    ",middlename='${map["middlename"]}' where serial='$a'")
            sql.close()
            return "true"
        } catch (e: Exception) {
            e.printStackTrace()
            "false";
        }
    }
}