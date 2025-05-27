package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.text.ParseException
 class PacientDatabaseManager: DatabaseManager() {

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
    fun getDatabyDNIPacient(dni: String): Cursor {
       loadDatabase()
       return db!!.rawQuery("SELECT * FROM pacient WHERE dni = '$dni'", null)

    }
    fun getAgePacient(naixement: String): Int {
       loadDatabase()
       val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

       return try {
          val naixementDate = sdf.parse(naixement) ?: return 0
          val fechaActual = Calendar.getInstance().time
          val calendarioNacimiento = Calendar.getInstance().apply { time = naixementDate }
          val calendarioActual = Calendar.getInstance().apply { time = fechaActual }
          var edad = calendarioActual.get(Calendar.YEAR) - calendarioNacimiento.get(Calendar.YEAR)
          if (calendarioActual.get(Calendar.MONTH) < calendarioNacimiento.get(Calendar.MONTH) ||
             (calendarioActual.get(Calendar.MONTH) == calendarioNacimiento.get(Calendar.MONTH) &&
                     calendarioActual.get(Calendar.DAY_OF_MONTH) < calendarioNacimiento.get(Calendar.DAY_OF_MONTH))) {
             edad--
          }
          edad
       } catch (e: ParseException) {
          //Error de Format
          println("Error: Formato de fecha incorrecto. ${e.message}")
          -1 // Retornar -1 en cas de error
       } catch (e: Exception) {
          // Qualsevol altre error
          println("Error inesperado: ${e.message}")
          -1 // retornar en cas de error
       }
    }
}
