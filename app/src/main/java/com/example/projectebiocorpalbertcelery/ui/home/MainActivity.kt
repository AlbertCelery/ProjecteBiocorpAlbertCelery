package com.example.projectebiocorpalbertcelery.ui.home

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.ActivityMainBinding
import com.example.projectebiocorpalbertcelery.ui.allergia.AllergiaFragmentViewModel
import com.example.projectebiocorpalbertcelery.ui.hopitalit.HospitalitFragmentViewModel
import com.example.projectebiocorpalbertcelery.ui.malaltia.MalaltiaFragmentViewModel
import com.example.projectebiocorpalbertcelery.ui.medicacio.MedicacioFragmentViewModel
import com.example.projectebiocorpalbertcelery.ui.pacient.PacientFragment
import com.example.projectebiocorpalbertcelery.ui.pacient.PacientFragmentViewModel
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var myFile: File? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    private var currentIndex = 0
    private lateinit var pacientFragment: PacientFragment


    //todo VIEWMODEL*
    private val sharedViewModel: PacientFragmentViewModel by viewModels()
    private val sharedViewModel2: MedicacioFragmentViewModel by viewModels()
    private val sharedViewModel3: AllergiaFragmentViewModel by viewModels()
    private val sharedViewModel4: MalaltiaFragmentViewModel by viewModels()
    private val sharedViewModel5: HospitalitFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //pacientFragment = PacientFragment()

        // Use the fragment

        initUI()



        // TODO ACONSEGUIR CANVIAR DE FRAGMENT supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pacientFragment).commit()*/
        binding.NextPBtn.setOnClickListener {
            val dni = nextReg()


            //todo VIEWMODEL*
            sharedViewModel.triggerLoadPacient(dni)
            sharedViewModel3.triggerLoadAllergia(dni)
            sharedViewModel4.triggerLoadMalaltia(dni)
            sharedViewModel5.triggerLoadHospitalit(dni)
            //pacientFragmentListener?.loadPacient("dni")
            //
           //TODO ACONSEGUIR CANVIAR DE FRAGMENT pacientFragment.loadPacient(dni)

        }
        binding.PreviousPBtn.setOnClickListener {
            val dni = previousReg()
            sharedViewModel.triggerLoadPacient(dni)
            sharedViewModel3.triggerLoadAllergia(dni)
            sharedViewModel4.triggerLoadMalaltia(dni)
            sharedViewModel5.triggerLoadHospitalit(dni)

            //TODO ACONSEGUIR CANVIAR DE FRAGMENT pacientFragment.loadPacient(dni)
        }
        updateReg()




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

        var createTable = "CREATE TABLE IF NOT EXISTS pacient (dni TEXT PRIMARY KEY, nom TEXT, cognom1 TEXT, cognom2 TEXT, naixement TEXT, ciutatN TEXT, ciutatR TEXT, carrer TEXT, codPost INTEGER)"
        db!!.execSQL(createTable)//TODO: MOSTRAR EDAT PER CALCUL
        var createTable2 = "CREATE TABLE IF NOT EXISTS hospitalitzacio (id INTEGER PRIMARY KEY AUTOINCREMENT, dataInici TEXT, dataFi TEXT, nomHosp TEXT, MotiusHosp INTEGER, dni TEXT, FOREIGN KEY(dni) REFERENCES pacient(dni), FOREIGN KEY(MotiusHosp) REFERENCES motivhospit(id))"
        db!!.execSQL(createTable2)//TODO: motiu posar o no i com // MOSTRAR TEMPS TOTAL PER CALCUL
        var createTable25 ="CREATE TABLE IF NOT EXISTS tractamentHosp (id INTEGER PRIMARY KEY AUTOINCREMENT, idHospitalitzacio INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT," +
                " medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id)," +
                " FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(idHospitalitzacio) REFERENCES hospitalitzacio(id))"
        db!!.execSQL(createTable25)//TODO:Calcular temps total
        var createTable3 = "CREATE TABLE IF NOT EXISTS medicament (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, marca TEXT, formaPres INTEGER, efecteSecundari TEXT, FOREIGN KEY(formaPres) REFERENCES formapres(id))"
        db!!.execSQL(createTable3)//crear un null per a medicament

        var createTable4 = "CREATE TABLE IF NOT EXISTS tractamentMalalt (id INTEGER PRIMARY KEY AUTOINCREMENT, idMalaltia INTEGER, horamed1 TEXT, horamed2 TEXT, horamed3 TEXT, horamed4 TEXT, medid INTEGER, medid2 INTEGER, medid3 INTEGER, medid4 INTEGER, iniciT TEXT, fiT TEXT," +
                " FOREIGN KEY(medid) REFERENCES medicament(id), FOREIGN KEY(medid2) REFERENCES medicament(id), FOREIGN KEY(medid3) REFERENCES medicament(id), FOREIGN KEY(medid4) REFERENCES medicament(id), FOREIGN KEY(idMalaltia) REFERENCES malaltia(id))"
        db!!.execSQL(createTable4)//TODO: Calcular temps total
        /*var createTable5 = "CREATE TABLE IF NOT EXISTS allergies (id INTEGER PRIMARY KEY AUTOINCREMENT, descripcio TEXT, id_medicament INTEGER, FOREIGN KEY(id_medicament) REFERENCES medicament(id))"
        db!!.execSQL(createTable5)*///TODO: NO TINC RECURSOS MORALS SUFIENS PER FER ALERGIA EN DOS TAULES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ARREGLAR TAULA ALERGIA
        var createTable6 = "CREATE TABLE IF NOT EXISTS alergies (id_medicament INTEGER, alergicPosition INTEGER, descripcio TEXT, dni TEXT, FOREIGN KEY(id_medicament) REFERENCES medicament(id), FOREIGN KEY(dni) REFERENCES pacient(dni), PRIMARY KEY(alergicPosition, dni))"
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
    private fun updateReg()/*:String*/{
        cursor = db!!.query("pacient",null,null,null,null,null,null)
        if (cursor!=null && cursor!!.moveToFirst()){
            //return showFullName()


        } else {
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show()
           // return ""
        }


    }
    fun loadMedicament(idmed: Int){
        sharedViewModel2.triggerLoadMedicacio(idmed)

    }
    //mostrar estudiant
    private fun showFullName():String {
        if (cursor != null && !cursor!!.isClosed){
            cursor!!.moveToPosition(currentIndex)

            //agafar dades del registre
            val name = cursor!!.getString(cursor!!.getColumnIndexOrThrow("nom"))
            val surname1 = cursor!!.getString(cursor!!.getColumnIndexOrThrow("cognom1"))
            val surname2 = cursor!!.getString(cursor!!.getColumnIndexOrThrow("cognom2"))
            val dni = cursor!!.getString(cursor!!.getColumnIndexOrThrow("dni"))
            val fullname: String = buildString {
                append(name)
                append(" ")
                append(surname1)
                append(" ")
                append(surname2)
            }

            binding.NomCompltEdit.text = fullname

            updateBtn()

            return dni

        }
        return ""
    }
    private fun updateBtn(){
        if (cursor != null){
            binding.PreviousPBtn.isEnabled = currentIndex >0
            binding.NextPBtn.isEnabled = currentIndex < cursor!!.count -1

        }

    }
    private fun nextReg():String{

        if (currentIndex < cursor!!.count -1){
            currentIndex++
            return showFullName()
        }
return ""
    }
    private fun previousReg():String{
        if (currentIndex > 0){
            currentIndex--
            return showFullName()
        }
return ""
    }



}