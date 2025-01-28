package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.projectebiocorpalbertcelery.ui.home.ConnectionClass
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


public class DatabaseManager {
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null


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
    //Medicament
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
        values.put("formaPres", id)
        values.put("efecteSecundari", efecteSecundari)
        val result = db!!.insert("medicament", null, values)
        return result !=-1L
    }
    //Malaltia
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
    fun getNextMalaltiaPacients(dni: String, idMalaltia: Int):Cursor? {
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
    //Tractament malaltia
    /*fun getMalaltiaTractDays(idTractamentMalaltia: Int): Int {
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


    }*/
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


    //Medicament
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
    fun convertStringToInt(input: String): Int? {
        return try {
            input.toInt() // Intenta convertir la cadena a Int
        } catch (e: NumberFormatException) {
            // Maneja la excepción si la conversión falla
            null // O puedes devolver un valor por defecto, como 0
        }
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
    //Allergia
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

        val result = db!!.update("alergies", values, "dni = '$dni' AND alergicPosition = $alergicPosition", null)
        return result != 0
    }



    fun getDatabyDNIPacient(dni: String): Cursor {
        loadDatabase()
        return db!!.rawQuery("SELECT * FROM pacient WHERE dni = '$dni'", null)

    }
    //Malaltia
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
    //Hospitalitzacio
    fun insertDataHospitalitzacio(dataInici: String, dataFi: String, nomHosp: String, MotiusHosp: Int, dni: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("dataInici", dataInici)
        values.put("dataFi", dataFi)
        values.put("nomHosp", nomHosp)
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
    fun getTotalDays(ddMMyyyyinici: String, ddMMyyyyfi: String):String{
        loadDatabase()
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date1 = sdf.parse(ddMMyyyyinici)
        val date2 = sdf.parse(ddMMyyyyfi)
        val diffMillis = date2.time - date1.time
        val diffDays = (diffMillis / (24 * 60 * 60 * 1000)).toInt()
        val diffMonths = diffDays / 30
        var stringMonth = ""
        if (diffMonths > 1) {
            stringMonth = buildString {
                append(diffMonths)
                append(" mesos")
            }
        } else if (diffMonths == 1) {
            stringMonth = buildString {
                append(diffMonths)
                append(" mes")
            }
        } else {
            stringMonth = ""
        }
        val restidays = diffDays % 30
        var stringDaysMonth = ""
        if (restidays >0) {
            if (diffMonths > 0) {
                if (restidays > 1) {
                    stringDaysMonth = buildString {
                        append(stringMonth)
                        append("i ")
                        append(restidays)
                        append(" dies")
                    }
                }else {
                    stringDaysMonth = buildString {
                        append(stringMonth)
                        append("i ")
                        append(restidays)
                        append(" dia")
                    }
                }


            }else{
                if (restidays > 1) {
                    stringDaysMonth = buildString {
                        append(restidays)
                        append(" dies")
                    }
                } else{
                    stringDaysMonth = buildString {
                        append(restidays)
                        append(" dia")
                    }
                }
            }
        } else if (diffMonths == 0) {
            stringDaysMonth = "0 dies"

        }
        return stringDaysMonth




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
    //Tractament Hospitalitzacio
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
        values.put("medid", medid)
        values.put("medid2", medid2)
        values.put("medid3", medid3)
        values.put("medid4", medid4)
        values.put("iniciT", iniciT)
        values.put("fiT", fiT)
        val result = db!!.insert("tractamentHosp", null, values)
        return result != -1L
    }
    //Login
    fun userPasswordExists(username: String, password: String): Boolean {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT * FROM user WHERE Username = ? AND Password = ?", arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun saveUserPass(username: String, password: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("Username", username)
        values.put("Password", password)
        val result = db!!.insert("user", null, values)
        return result != -1L
    }


}