package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.projectebiocorpalbertcelery.ui.home.ConnectionClass
import java.io.File


public class DatabaseManager {
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null

    private var cursor: Cursor? = null
    private var currentIndex = 0
fun loadDatabase(){
    myFile = ConnectionClass.myFile
    db = SQLiteDatabase.openOrCreateDatabase(myFile!!.absolutePath, null,null)
}
    fun obtenerNombres(dataCamp: String): MutableList<String> {
        loadDatabase()
        val nombres: MutableList<String> = mutableListOf()

        var cursor = db!!.rawQuery((dataCamp), null)

        if (cursor.moveToFirst()) {
            do {
                nombres.add(cursor.getString(0)) // Obtener el nombre
            } while (cursor.moveToNext())
        }
        cursor.close()

        return nombres
    }
    fun saveData(data: String, tableName: String) {
        loadDatabase()
        db!!.execSQL("INSERT INTO $tableName (nom) VALUES ('$data')")

    }
    fun insertDataPacient(dni: String, nom: String, cognom1: String, cognom2: String, dataNaixement: String, ciutatN: String, ciutatR: String, carrer: String, codiPostal: Int): Boolean {
        loadDatabase()

        val values = ContentValues()
        values.put("dni", dni)
        values.put("nom", nom)
        values.put("cognom1", cognom1)
        values.put("cognom2", cognom2)
        values.put("naixement", dataNaixement)
        values.put("ciutatN", ciutatN)
        values.put("ciutatR", ciutatR)
        values.put("carrer", carrer)
        values.put("codPost", codiPostal)

        val result = db!!.insert("pacient", null, values)

        return result !=-1L
    }
    fun convertStringToInt(input: String): Int? {
        return try {
            input.toInt() // Intenta convertir la cadena a Int
        } catch (e: NumberFormatException) {
            // Maneja la excepción si la conversión falla
            null // O puedes devolver un valor por defecto, como 0
        }
    }


    fun openDatabase(requireContext: Context) {
        loadDatabase()


    }

}