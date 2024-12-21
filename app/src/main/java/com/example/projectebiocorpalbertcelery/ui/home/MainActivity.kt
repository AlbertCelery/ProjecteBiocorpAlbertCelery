package com.example.projectebiocorpalbertcelery.ui.home

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
import com.example.projectebiocorpalbertcelery.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null
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
    /**   //COMPLETAR TAULES**/
    private fun createTables(){

        var createTable = "CREATE TABLE IF NOT EXISTS pacient (dni TEXT PRIMARY KEY, nom TEXT, cognom1 TEXT, cognom2 TEXT, adreca TEXT, telefon TEXT, correu TEXT)"
        db!!.execSQL(createTable)//tipus_motiu posar o no
        var createTable2 = "CREATE TABLE IF NOT EXISTS hospitalitzacio (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT, dni TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni))"
        db!!.execSQL(createTable2)
        var createTable3 = "CREATE TABLE IF NOT EXISTS medicament (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT)"
        db!!.execSQL(createTable3)//arreglar tractament
        var createTable4 = "CREATE TABLE IF NOT EXISTS tractament (id INTEGER PRIMARY KEY AUTOINCREMENT, id_medicament INTEGER, nom TEXT, descripcio TEXT, dni TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni), FOREIGN KEY(id_medicament) REFERENCES medicament(id))"
        db!!.execSQL(createTable4)
        var createTable5 = "CREATE TABLE IF NOT EXISTS allergies (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT)"
        db!!.execSQL(createTable5)
        var createTable6 = "CREATE TABLE IF NOT EXISTS allergies_pacient (id_allergies INTEGER, dni TEXT, FOREIGN KEY(id_allergies) REFERENCES allergies(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(id_allergies, dni))"
        db!!.execSQL(createTable6)
        var createTable7 = "CREATE TABLE IF NOT EXISTS motius (id INTEGER PRIMARY KEY AUTOINCREMENT, tipus_motiu TEXT)"
        db!!.execSQL(createTable7)
        var createTable8 = "CREATE TABLE IF NOT EXISTS motiu_hospitalitzacio (id_motiu INTEGER, id_hospitalitzacio INTEGER, FOREIGN KEY(id_motiu) REFERENCES motius(id), FOREIGN KEY(id_hospitalitzacio) REFERENCES hospitalitzacio(id), PRIMARY KEY(id_motiu, id_hospitalitzacio))"
        db!!.execSQL(createTable8)
        var createTable9 = "CREATE TABLE IF NOT EXISTS malaltia (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT)"
        db!!.execSQL(createTable9)
        var createTable10 = "CREATE TABLE IF NOT EXISTS malaltia_pacient (id_malaltia INTEGER, dni TEXT, FOREIGN KEY(id_malaltia) REFERENCES malaltia(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(id_malaltia, dni))"
        db!!.execSQL(createTable10)


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
}