package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.database.Cursor

class MedicacioDatabaseManager: DatabaseManager() {

    fun getformaPresInt(formaPres: String): Int {
        loadDatabase()
        var cursor = db!!.rawQuery("SELECT id FROM formaPres WHERE nom = '$formaPres'", null)
        var id = -1
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        return id
    }
    fun insertDataMedicacio(nom: String, marca: String, formaPres: String, efecteSecundari: String): Boolean {
        loadDatabase()
        val id = getformaPresInt(formaPres)
        val values = ContentValues()
        values.put("nom", nom)
        values.put("marca", marca)
        values.put("formaPres", id) // forma presentacio
        values.put("efecteSecundari", efecteSecundari)
        val result = db!!.insert("medicament", null, values)
        return result !=-1L
    }
    fun getMedicamentCursor(): Cursor {
        loadDatabase()
        return db!!.rawQuery("SELECT * FROM medicament", null)
    }
    fun obtenerMedicamentos(): MutableList<String> {
        val listaMedicamentos = mutableListOf<String>()
        loadDatabase()

        val cursor = db!!.rawQuery("SELECT nom, marca FROM medicament", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0) // Obtener el nombre del medicamento
                val marca = cursor.getString(1) // Obtener la marca del medicamento
                listaMedicamentos.add("$nombre, $marca") // Formatear y agregar a la lista
            } while (cursor.moveToNext())
        }
        cursor.close()
        db!!.close()
        return listaMedicamentos
    }

    fun getmedicacioNom(idmed: Int): String {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT nom FROM medicament WHERE id = $idmed", null)
        var nom = ""
        if (cursor.moveToFirst()) {
            nom = cursor.getString(0)
        }
        cursor.close()
        return nom
    }
    fun getmedicacioMarca(idmed: Int): String {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT marca FROM medicament WHERE id = $idmed", null)
        var marca = ""
        if (cursor.moveToFirst()) {
            marca = cursor.getString(0)
        }
        cursor.close()
        return marca

    }

    fun getmedicacioEfsec(idmed: Int): String {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT efecteSecundari FROM medicament WHERE id = $idmed", null)
        var efecteSecundari = ""
        if (cursor.moveToFirst()) {
            efecteSecundari = cursor.getString(0)
        }
        cursor.close()
        return efecteSecundari

    }
}