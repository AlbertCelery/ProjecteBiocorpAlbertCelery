package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor

class HospitalDatabaseManager: DatabaseManager() {
    fun insertDataHospitalitzacio(dataInici: String, dataFi: String, nomHosp: String, MotiusHosp: Int, dni: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("dataInici", dataInici)
        values.put("dataFi", dataFi)
        values.put("nomHosp", nomHosp)// nom Hospital
        values.put("MotiusHosp", MotiusHosp)
        values.put("dni", dni)
        val result = db!!.insert("hospitalitzacio", null, values)
        return result !=-1L
    }
    fun getIniciHospitalit(idHospitalitzacio:Int):String{
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT dataInici FROM hospitalitzacio WHERE id = $idHospitalitzacio", null)
        var dataInici = ""
        if (cursor.moveToFirst()) {
            dataInici = cursor.getString(0)
        }
        cursor.close()
        return dataInici
    }
    fun getFiHospitalit(idHospitalitzacio:Int):String{
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT dataFi FROM hospitalitzacio WHERE id = $idHospitalitzacio", null)
        var dataFi = ""
        if (cursor.moveToFirst()) {
            dataFi = cursor.getString(0)
        }
        cursor.close()
        return dataFi
    }

    fun getnomHosp(idHospitalitzacio:Int):String{
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT nomHosp FROM hospitalitzacio WHERE id = $idHospitalitzacio", null)
        var nomHosp = ""
        if (cursor.moveToFirst()) {
            nomHosp = cursor.getString(0)
        }
        cursor.close()
        return nomHosp
    }
    fun getMotiusHosp(idHospitalitzacio:Int):Int{
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT MotiusHosp FROM hospitalitzacio WHERE id = $idHospitalitzacio", null)
        var MotiusHosp= 0
        if (cursor.moveToFirst()) {
            MotiusHosp = cursor.getInt(0)
        }
        cursor.close()
        return MotiusHosp

    }
    fun getFirstHospitalitzacio(dni: String): Int? {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT id FROM hospitalitzacio WHERE dni = '$dni' ORDER BY id LIMIT 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }


    }
    fun getLastHospitalitzacio(dni: String): Int? {
        loadDatabase()
        var cursor =db?.rawQuery("SELECT id FROM hospitalitzacio WHERE dni = '$dni' ORDER BY id DESC LIMIT 1", null)

        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
    }
    fun getPreviousHospitalitzacio(dni: String, idHospitalitzacio: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM hospitalitzacio WHERE dni = '$dni' AND id < $idHospitalitzacio ORDER BY id LIMIT 1", null)
    }
    fun getNextHospitalitzacio(dni: String, idHospitalitzacio: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM hospitalitzacio WHERE dni = '$dni' AND id > $idHospitalitzacio ORDER BY id LIMIT 1", null)
    }
}