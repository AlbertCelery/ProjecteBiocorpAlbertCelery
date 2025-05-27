package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MalaltiaDatabaseManager: DatabaseManager() {
    fun insertDataMalaltia(nom: String, dni: String, descripcio: String, sintomes: String, iniciMalaltia: String, fiMalaltia: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("nom", nom)
        values.put("dni", dni)
        values.put("descripcio", descripcio)
        values.put("sintomas", sintomes)
        values.put("iniciMalaltia", iniciMalaltia)
        values.put("fiMalaltia", fiMalaltia)
        val result = db!!.insert("malaltia", null, values)
        return result !=-1L
    }
    fun getPacientLastMalatiaId(dni: String): Int? {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT id FROM malaltia WHERE dni = '$dni' ORDER BY id DESC LIMIT 1", null)

        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
    }

    fun getPacientFirstMalatiaId(dni: String): Int? {
        loadDatabase()
        var cursor = db?.rawQuery("SELECT id FROM malaltia WHERE dni = '$dni' ORDER BY id ASC LIMIT 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
    }
    fun getNextMalaltiaPacients(dni: String, idMalaltia: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM malaltia WHERE dni = '$dni' AND id > $idMalaltia LIMIT 1", null)


    }
    fun getPreviousMalaltiaPacients(dni: String, idMalaltia: Int): Cursor? {
        loadDatabase()
        return db?.rawQuery("SELECT * FROM malaltia WHERE dni = '$dni' AND id < $idMalaltia ORDER BY id DESC LIMIT 1", null)

    }
    fun getTotalDaysMalaltia(idMalaltia: Int): Int {
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT fiMalaltia, iniciMalaltia FROM malaltia WHERE id = $idMalaltia", null)
        var fiMalaltia = ""
        var iniciMalaltia = ""
        if (cursor.moveToFirst()) {
            fiMalaltia = cursor.getString(0)
            iniciMalaltia = cursor.getString(1)
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val datafi = sdf.parse(fiMalaltia)
            val datainici = sdf.parse(iniciMalaltia)
            val diffMillis = datafi.time - datainici.time
            val diffDays = diffMillis / (24 * 60 * 60 * 1000)
            return diffDays.toInt()
        }
        cursor.close()
        return 0
    }
    fun getMalaltiaNom(idmalaltia: Int):String{
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT nom FROM malaltia WHERE id = $idmalaltia", null)
        var nom = ""
        if (cursor.moveToFirst()) {
            nom = cursor.getString(0)
        }
        cursor.close()
        return nom

    }
    fun getMalaltiaDescripcio(idmalaltia: Int):String{
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT descripcio FROM malaltia WHERE id = $idmalaltia", null)
        var descripcio = ""
        if (cursor.moveToFirst()) {
            descripcio = cursor.getString(0)
        }
        cursor.close()
        return descripcio


    }
    fun getMalaltiaSintomes(idmalaltia: Int):String{
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT sintomes FROM malaltia WHERE id = $idmalaltia", null)
        var sintomes = ""
        if (cursor.moveToFirst()) {
            sintomes = cursor.getString(0)
        }
        cursor.close()
        return sintomes

    }
    fun getMalaltiaInici(idmalaltia: Int):String{
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT iniciMalaltia FROM malaltia WHERE id = $idmalaltia", null)
        var datainici = ""
        if (cursor.moveToFirst()) {
            datainici = cursor.getString(0)
        }
        cursor.close()
        return datainici

    }
    fun getMalaltiaFi(idmalaltia: Int):String {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT fiMalaltia FROM malaltia WHERE id = $idmalaltia", null)
        var datafi = ""
        if (cursor.moveToFirst()) {
            datafi = cursor.getString(0)
        }
        cursor.close()
        return datafi
    }


}