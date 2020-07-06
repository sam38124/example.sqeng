package com.example.sqeng.api

import com.example.sqeng.dataBase.MysqlConnect
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object Api_Image {
    fun insertImage(image:ByteArray): String {
        try {
            val fil=File("imageFile")
            fil.mkdir()
            val name="_${SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(Date())}".replace(" ","").replace("-","").replace(":","_")
            val file= File("imageFile/$name")
            val output=FileOutputStream(file)
            output.write(image)
            output.close()
            return file.absolutePath
//            val sql = MysqlConnect()
//            var s = "0"
//            val re = sql.stmt.executeQuery("select count(1) from users where serial='$serial'")
//            re.next()
//            s = re.getString("count(1)")
//            if (s == "1") {
//
//                return file.absolutePath
//            } else {
//                return "false"
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "false"
        }
    }

}