package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class TractMalaltiaDatabaseManager: DatabaseManager() {
    fun getMalaltiaTractDays(idTractamentMalaltia: Int): Int {
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT fiT, iniciT FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var fiT = ""
        var iniciT = ""
        if (cursor.moveToFirst()) {
            fiT = cursor.getString(0)
            iniciT = cursor.getString(1)
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val datafi = sdf.parse(fiT)
            val datainici = sdf.parse(iniciT)
            val diffMillis = datafi.time - datainici.time
            val diffDays = diffMillis / (24 * 60 * 60 * 1000)
            return diffDays.toInt()
        }
        cursor.close()
        return 0


    }
    fun getFirstMalaltiaTractament(idMalaltia: Int): Int? {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT id FROM tractamentMalalt WHERE idMalaltia = $idMalaltia ORDER BY id LIMIT 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }


    }
    fun getLastMalaltiaTractament(idMalaltia: Int): Int? {
        loadDatabase()
        var cursor =db?.rawQuery("SELECT * FROM tractamentMalalt WHERE idMalaltia = $idMalaltia  ORDER BY id DESC LIMIT 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
    }
    fun getPreviousMalaltiaTractament(idMalaltia: Int, idTractament: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM tractamentMalalt WHERE idMalaltia = $idMalaltia AND id < $idTractament ORDER BY id LIMIT 1", null)
    }
    fun getNextMalaltiaTractament(idMalaltia: Int, idTractament: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM tractamentMalalt WHERE idMalaltia = $idMalaltia AND id > $idTractament ORDER BY id LIMIT 1", null)
    }
    fun insertDataTractementMalaltia(idMalaltia: Int, horamed1: String, horamed2: String, horamed3: String, horamed4: String, medid: Int, medid2: Int, medid3: Int, medid4: Int, iniciT: String, fiT: String):Boolean{
        loadDatabase()
        val values = ContentValues()
        values.put("idMalaltia", idMalaltia)
        values.put("horamed1", horamed1)
        values.put("horamed2", horamed2)
        values.put("horamed3", horamed3)
        values.put("horamed4", horamed4)
        values.put("medid", medid)
        values.put("medid2", medid2)
        values.put("medid3", medid3)
        values.put("medid4", medid4)
        values.put("iniciT", iniciT)
        values.put("fiT", fiT)
        val result = db!!.insert("tractamentMalalt", null, values)
        return result !=-1L

    }
    fun gethoramed1Malaltia(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed1 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var horamed1 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed1 = cursor.getString(0)
        }
        cursor?.close()
        return horamed1
    }
    fun gethoramed2Malaltia(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed2 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var horamed2 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed2 = cursor.getString(0)
        }
        cursor?.close()
        return horamed2
    }
    fun gethoramed3Malaltia(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed3 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var horamed3 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed3 = cursor.getString(0)
        }
        cursor?.close()
        return horamed3
    }
    fun gethoramed4Malaltia(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed4 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var horamed4 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed4 = cursor.getString(0)
        }
        cursor?.close()
        return horamed4
    }
    fun getmedidMalaltia(idTractamentMalaltia: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var medid = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid = cursor.getInt(0)
        }
        cursor?.close()
        return medid
    }
    fun getmedid2Malaltia(idTractamentMalaltia: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid2 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var medid2 = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid2 = cursor.getInt(0)
        }
        cursor?.close()
        return medid2
    }
    fun getmedid3Malaltia(idTractamentMalaltia: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid3 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var medid3 = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid3 = cursor.getInt(0)
        }
        cursor?.close()
        return medid3
    }
    fun getmedid4Malaltia(idTractamentMalaltia: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid4 FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var medid4 = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid4 = cursor.getInt(0)
        }
        cursor?.close()
        return medid4
    }
    fun getIniciMalaltiaTract(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT iniciT FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var iniciT = ""
        if (cursor != null && cursor.moveToFirst()) {
            iniciT = cursor.getString(0)
        }
        cursor?.close()
        return iniciT
    }
    fun getFiMalaltiaTract(idTractamentMalaltia: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT fiT FROM tractamentMalalt WHERE id = $idTractamentMalaltia", null)
        var fiT = ""
        if (cursor != null && cursor.moveToFirst()) {
            fiT = cursor.getString(0)
        }
        cursor?.close()
        return fiT
    }
}