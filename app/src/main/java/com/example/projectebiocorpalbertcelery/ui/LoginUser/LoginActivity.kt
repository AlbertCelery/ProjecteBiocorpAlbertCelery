package com.example.projectebiocorpalbertcelery.ui.LoginUser

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.ActivityLoginBinding
import com.example.projectebiocorpalbertcelery.ui.home.ConnectionClass
import com.example.projectebiocorpalbertcelery.ui.home.MainActivity
import java.io.File

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseManager: DatabaseManager
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        databaseManager = DatabaseManager()
        createDatabase()
        createTables()
        binding.loginbtn.setOnClickListener {

            if(checkUserPass()){
                val intent = Intent( this, MainActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "Incorrect user or password", Toast.LENGTH_SHORT).show()
            }

        }
        binding.registerbtn.setOnClickListener {
            if(checkSaveData()) {
                saveUser()

            }else{
                Toast.makeText(this, "Error check data", Toast.LENGTH_SHORT).show()
            }

        }

    }
    fun checkSaveData(): Boolean{
       return (binding.useredit.text.toString()!="") && (binding.passwordedit.text.toString() != "")


    }
    fun saveUser(){
        val dni = binding.useredit.text.toString()
        val password = binding.passwordedit.text.toString()
        databaseManager.saveUserPass(dni, password)

    }
    fun checkUserPass(): Boolean{
        val dni = binding.useredit.text.toString()
        val password = binding.passwordedit.text.toString()
        return databaseManager.userPasswordExists(dni, password)

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


    private fun createTables() {
        var createTable0 =
            "CREATE TABLE IF NOT EXISTS user (Username TEXT PRIMARY KEY, Password TEXT)"
        db!!.execSQL(createTable0)
        var createTable = "CREATE TABLE IF NOT EXISTS pacient (dni TEXT PRIMARY KEY, nom TEXT, cognom1 TEXT, cognom2 TEXT, naixement TEXT, ciutatN TEXT, ciutatR TEXT, carrer TEXT, codPost INTEGER)"
        db!!.execSQL(createTable)
        var createTable2 = "CREATE TABLE IF NOT EXISTS hospitalitzacio (id INTEGER PRIMARY KEY AUTOINCREMENT, dataInici TEXT, dataFi TEXT, nomHosp TEXT, MotiusHosp INTEGER, dni TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni), FOREIGN KEY(MotiusHosp) REFERENCES motivhospit(id))"
        db!!.execSQL(createTable2)
        var createTable25 ="CREATE TABLE IF NOT EXISTS tractamentHosp (id INTEGER PRIMARY KEY AUTOINCREMENT, idHospitalitzacio INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT," +
                " medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id)," +
                " FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(idHospitalitzacio) REFERENCES hospitalitzacio(id))"
        db!!.execSQL(createTable25)
        var createTable3 = "CREATE TABLE IF NOT EXISTS medicament (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, marca TEXT, formaPres INTEGER, efecteSecundari TEXT, FOREIGN KEY(formaPres) REFERENCES formapres(id))"
        db!!.execSQL(createTable3)

        var createTable4 = "CREATE TABLE IF NOT EXISTS tractamentMalalt (id INTEGER PRIMARY KEY AUTOINCREMENT, idMalaltia INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT, medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id), FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(idMalaltia) REFERENCES malaltia(id))"
        db!!.execSQL(createTable4)

        var createTable6 = "CREATE TABLE IF NOT EXISTS alergies (id_medicament INTEGER, alergicPosition INTEGER, descripcio TEXT, dni TEXT, FOREIGN KEY(id_medicament) REFERENCES medicament(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(alergicPosition, dni))"
        db!!.execSQL(createTable6)
        var createTable7 = "CREATE TABLE IF NOT EXISTS motivhospit (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT)"
        db!!.execSQL(createTable7)
        var createTable8 = "CREATE TABLE IF NOT EXISTS formaPres (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT)"
        db!!.execSQL(createTable8)
        var createTable9 = "CREATE TABLE IF NOT EXISTS malaltia (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, descripcio TEXT, dni TEXT, sintomes TEXT, iniciMalaltia TEXT, fiMalaltia TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni))"
        db!!.execSQL(createTable9)



    }


}