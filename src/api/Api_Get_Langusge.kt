package com.example.sqeng.api

import com.example.sqeng.PublicBeans
import com.example.sqeng.toHex
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader


object Api_Get_Langusge {
    fun getLanguage(): String {
        val file = FileInputStream(PublicBeans.sqlLiteRoute.replace("jdbc:sqlite:",""))
        return file.readAllBytes().toHex()
    }
}