package com.example.projectebiocorpalbertcelery.ui.home

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null
    /*private var cursor= DatabaseManager()
    private var currentIndex = 0*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }
    private fun createDatabase(){
        try {

            val folder =
                File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()+"/Database")
            if(!folder.exists()){
                folder.mkdir()
            }

            myFile = File(folder,"MyDB")


            ConnectionClass.myFile = myFile

            //obrim o creem la nostra bbdd SQLite
            db = SQLiteDatabase.openOrCreateDatabase(myFile!!.absolutePath, null,null)

            //Si hi ha algun problema llencem una excepció
        } catch (ex:Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
    /**   //COMPLETAR TAULES
     * Malaltia : id, nom, descripcio (inclou hospitalitzacio: si, no)-->si sí ajuntar les que si amb hospitalitzacio
     * Hospitalitzacio: excloure les malalties i colocaro tot a malalties
     * **/

    private fun createTables(){
                //TODO: Crear id per a la taula pacients!!!!!!
        var createTable = "CREATE TABLE IF NOT EXISTS pacient (dni TEXT PRIMARY KEY, nom TEXT, cognom1 TEXT, cognom2 TEXT, naixement TEXT, ciutatN TEXT, ciutatR TEXT, carrer TEXT, codPost INTEGER)"
        db!!.execSQL(createTable)//TODO: MOSTRAR EDAT PER CALCUL
        var createTable2 = "CREATE TABLE IF NOT EXISTS hospitalitzacio (id INTEGER PRIMARY KEY AUTOINCREMENT, dataInici TEXT, dataFi TEXT, nomHosp TEXT, MotiusHosp INTEGER, dni TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni), FOREIGN KEY(MotiusHosp) REFERENCES motivhospit(id))"
        db!!.execSQL(createTable2)//TODO: motiu posar o no i com // MOSTRAR TEMPS TOTAL PER CALCUL
        var createTable25 = "CREATE TABLE IF NOT EXISTS tractamentHosp (id INTEGER PRIMARY KEY AUTOINCREMENT, id_hospitalitzacio INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT," +
                " medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id)," +
                " FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(id_hospitalitzacio) REFERENCES hospitalitzacio(id))"
        db!!.execSQL(createTable25)//TODO:Calcular temps total
        var createTable3 = "CREATE TABLE IF NOT EXISTS medicament (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, marca TEXT, formaPres INTEGER, efecteSecundari TEXT, FOREIGN KEY(formaPres) REFERENCES formapres(id))"
        db!!.execSQL(createTable3)//arreglar tractament
        var createTable4 = "CREATE TABLE IF NOT EXISTS tractamentMalalt (id INTEGER PRIMARY KEY AUTOINCREMENT, idMalaltia INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT, medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id), FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(idMalaltia) REFERENCES malaltia(id))"
        db!!.execSQL(createTable4)//TODO: Calcular temps total
        var createTable5 = "CREATE TABLE IF NOT EXISTS allergies (id INTEGER PRIMARY KEY AUTOINCREMENT, descripcio TEXT, id_medicament INTEGER, FOREIGN KEY(id_medicament) REFERENCES medicament(id))"
        db!!.execSQL(createTable5)
        var createTable6 = "CREATE TABLE IF NOT EXISTS allergies_pacient (id_allergies INTEGER, dni TEXT, FOREIGN KEY(id_allergies) REFERENCES allergies(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(id_allergies, dni))"
        db!!.execSQL(createTable6)
        var createTable7 = "CREATE TABLE IF NOT EXISTS motivhospit (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT)"
        db!!.execSQL(createTable7)
        var createTable8 = "CREATE TABLE IF NOT EXISTS formaPres (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT)"
        db!!.execSQL(createTable8)
        var createTable9 = "CREATE TABLE IF NOT EXISTS malaltia (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT, dni TEXT, sintomes TEXT, iniciMalaltia TEXT, fiMalaltia TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni))"
        db!!.execSQL(createTable9)//TODO: Calcular temps total
       /* var createTable10 = "CREATE TABLE IF NOT EXISTS malaltia_pacient (id_malaltia INTEGER, dni TEXT, iniciMalaltia TEXT, fiMalaltia TEXT, FOREIGN KEY(id_malaltia) REFERENCES malaltia(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(id_malaltia, dni))"
        db!!.execSQL(createTable10)//TODO: Calcular temps total*/


    }

    fun initUI(){
        iniNavigation()
        createDatabase()
        createTables()
    }
    fun iniNavigation(){
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
    fun clearNomPacient(){
        binding.NomCompltEdit.text=""
    }
   /* private fun updateBtn(){
        if (cursor != null){
            binding.PreviousPBtn.isEnabled = currentIndex >0
            binding.NextPBtn.isEnabled = currentIndex < cursor!!.count -1

        }
    }
    private fun nextReg(){

        if (currentIndex < cursor!!.count -1){
            currentIndex++
            showFullName()
        }

    }
    private fun previousReg(){
        if (currentIndex > 0){
            currentIndex--
            showFullName()
        }

    }
    private fun showFullName(){
        cursor.moveToPosition(currentIndex)
        val nom = cursor.getString(1)
        val cognom1 = cursor.getString(2)
        val cognom2 = cursor.getString(3)
        binding.NomCompltTextView.text = "$nom $cognom1 $cognom2"

    }*///TODO PLA B FER DNI INVISIBLE A LA BASE I USARLO PER A BUSCAR LA POSICIO


}