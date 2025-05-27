package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor

class TractHospitalDatabaseManager: DatabaseManager() {
    fun getFirstHospitalitzacioTract(idHospitalitzacio: Int): Int? {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT id FROM tractamentHosp WHERE idHospitalitzacio = $idHospitalitzacio ORDER BY id LIMIT 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }


    }
    fun getLastHospitalitzacioTract(idHospitalitzacio: Int): Int? {
        loadDatabase()
        var cursor =db?.rawQuery("SELECT id FROM tractamentHosp WHERE idHospitalitzacio = $idHospitalitzacio ORDER BY id DESC LIMIT 1", null)

        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
    }
    fun getPreviousHospitalitzacioTract(idHospitalitzacio: Int, idTractament: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM tractamentHosp WHERE idHospitalitzacio = $idHospitalitzacio AND id < $idTractament ORDER BY id LIMIT 1", null)
    }
    fun getNextHospitalitzacioTract(idHospitalitzacio: Int, idTractament: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM tractamentHosp WHERE idHospitalitzacio = $idHospitalitzacio AND id > $idTractament ORDER BY id LIMIT 1", null)
    }
    fun getmedidHosp(idTractamentHosp: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var medid = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid = cursor.getInt(0)
        }
        cursor?.close()
        return medid
    }
    fun getmedid2Hosp(idTractamentHosp: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid2 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var medid2 = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid2 = cursor.getInt(0)
        }
        cursor?.close()
        return medid2
    }
    fun getmedid3Hosp(idTractamentHosp: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid3 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var medid = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid = cursor.getInt(0)
        }
        cursor?.close()
        return medid
    }
    fun getmedid4Hosp(idTractamentHosp: Int): Int {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT medid4 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var medid = 0
        if (cursor != null && cursor.moveToFirst()) {
            medid = cursor.getInt(0)
        }
        cursor?.close()
        return medid
    }
    fun gethoramed1Hosp(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed1 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var horamed1 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed1 = cursor.getString(0)
        }
        cursor?.close()
        return horamed1
    }
    fun gethoramed2Hosp(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed2 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var horamed1 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed1 = cursor.getString(0)
        }
        cursor?.close()
        return horamed1
    }
    fun gethoramed3Hosp(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed3 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var horamed1 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed1 = cursor.getString(0)
        }
        cursor?.close()
        return horamed1
    }
    fun gethoramed4Hosp(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT horamed4 FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var horamed1 = ""
        if (cursor != null && cursor.moveToFirst()) {
            horamed1 = cursor.getString(0)
        }
        cursor?.close()
        return horamed1
    }
    fun getIniciHospTract(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT iniciT FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var iniciT = ""
        if (cursor != null && cursor.moveToFirst()) {
            iniciT = cursor.getString(0)
        }
        cursor?.close()
        return iniciT
    }
    fun getFiHospTract(idTractamentHosp: Int): String {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT fiT FROM tractamentHosp WHERE id = $idTractamentHosp", null)
        var fiT = ""
        if (cursor != null && cursor.moveToFirst()) {
            fiT = cursor.getString(0)
        }
        cursor?.close()
        return fiT
    }
    fun insertDataTractamentHosp(idHosp: Int, horamed1: String, horamed2: String, horamed3: String, horamed4: String, medid: Int, medid2: Int, medid3: Int, medid4: Int, iniciT: String, fiT: String):Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("idHospitalitzacio", idHosp)
        values.put("horamed1", horamed1)
        values.put("horamed2", horamed2)
        values.put("horamed3", horamed3)
        values.put("horamed4", horamed4)
        values.put("medid", medid) //id medicament 1
        values.put("medid2", medid2)
        values.put("medid3", medid3)
        values.put("medid4", medid4)
        values.put("iniciT", iniciT)
        values.put("fiT", fiT)
        val result = db!!.insert("tractamentHosp", null, values)
        return result != -1L
    }
}