package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor

class AllergiaDatabaseManager: DatabaseManager() {

    fun getAlergiaCursor(dni: String, alergicPosition: Int): Cursor {
        loadDatabase()
        return db!!.rawQuery("SELECT * FROM alergies WHERE dni = '$dni' AND alergicPosition = $alergicPosition", null)




    }


    //TODO POT PETAR PER IDMED DE LALERGIA
    fun insertDataAlergia(id_medicament: Int?, alergicPosition: Int, descripcio: String, dni: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("id_medicament", id_medicament)
        values.put("alergicPosition", alergicPosition)
        values.put("descripcio", descripcio)
        values.put("dni", dni)
        val result = db!!.insert("alergies", null, values)
        return result !=-1L

    }
    fun genAlergiaStack(dni: String){
        insertDataAlergia(1, 1, "", dni)
        insertDataAlergia(1, 2, "", dni)
        insertDataAlergia(1, 3, "", dni)
        insertDataAlergia(1, 4, "", dni)
        insertDataAlergia(1, 5, "", dni)


    }


    fun updateAlergia(id_medicament: Int?, alergicPosition: Int, descripcio: String, dni: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("id_medicament", id_medicament)

        values.put("descripcio", descripcio)

       // val result = db!!.update("alergies", values, "dni = '$dni' AND alergicPosition = $alergicPosition", null)
        //return result != 0
        val whereClause = "dni = ? AND alergicPosition = ?"
        val whereArgs = arrayOf(dni, alergicPosition.toString())

        val result = db!!.update("alergies", values, whereClause, whereArgs)

        return result > 0

    }




}