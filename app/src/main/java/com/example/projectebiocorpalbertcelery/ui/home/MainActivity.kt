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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.AllergiaDatabaseManager
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.data.HospitalDatabaseManager
import com.example.projectebiocorpalbertcelery.data.MedicacioDatabaseManager
import com.example.projectebiocorpalbertcelery.data.PacientDatabaseManager
import com.example.projectebiocorpalbertcelery.data.TractHospitalDatabaseManager
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
    //private lateinit var pacientFragment: PacientFragment


    //VIEWMODEL*
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


        initUI()

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController




        binding.NextPBtn.setOnClickListener {
            val dni = nextReg()



            sharedViewModel.triggerLoadPacient(dni)
            sharedViewModel3.triggerLoadAllergia(dni)
            sharedViewModel4.triggerLoadMalaltia(dni)
            sharedViewModel5.triggerLoadHospitalit(dni)


        }
        binding.PreviousPBtn.setOnClickListener {

            val dni = previousReg()
            sharedViewModel.triggerLoadPacient(dni)
            sharedViewModel3.triggerLoadAllergia(dni)
            sharedViewModel4.triggerLoadMalaltia(dni)
            sharedViewModel5.triggerLoadHospitalit(dni)

            //ACONSEGUIR CANVIAR DE FRAGMENT pacientFragment.loadPacient(dni)
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


    private fun createTables(){
        var createTable0 = "CREATE TABLE IF NOT EXISTS user (Username TEXT PRIMARY KEY, Password TEXT)"
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

    //TODO TEST INSERTS >>>
    private fun testingDataBase(){
        //pacients
        val pacient = PacientDatabaseManager()

        pacient.insertDataPacient("53399387B",
            "Albert", "Celery", "Cosconera",
            "19-10-2001", "Escaldes Engordany",
            "La Seu d'Urgell", "Carrer Major", 25700 )


        pacient.insertDataPacient("53389287C",
            "Josep", "Capdevila", "Saura",
            "23-02-1967", "Barcelona",
            "Lleida", "Carrer Alcalde Costa", 25002)

        pacient.insertDataPacient("53396757D",
            "Clara", "Ponts", "Gómez",
            "12-04-2004", "Girona",
            "Banyoles", "Passeig de Mossèn Lluís Constans", 17820)
        //allergia
        val alergia = AllergiaDatabaseManager()
        alergia.genAlergiaStack("53399387B")
        alergia.genAlergiaStack("53389287C")
        alergia.genAlergiaStack("53396757D")



        alergia.updateAlergia(
            7,
            1,
            "Reacción anafiláctica",
            "53399387B"
        )




        alergia.updateAlergia(
            8,
            2,
            "Urticaria",
            "53399387B")




        alergia.updateAlergia(
            9,
            1,
            "Dificultad para respirar",
            "53389287C")




        alergia.updateAlergia(
            10,
            2,
            "Náuseas y vómitos",
            "53389287C")




        alergia.updateAlergia(//TODO afegir medicament null
            2,
            1,
            "Erupción cutánea",
            "53396757D")


        //Medicament
        val formesPres = arrayOf(//TODO Modificar segons els medicaments introduits
            "Crema",
            "Injeccions subcutànies",
            "Comprimits",
            "Xarop",
            "Injeccions intramusculars",
            "Gotes",
            "Pols",
            "Parches transdèrmics"

        )
        val med = MedicacioDatabaseManager()

        med.insertDataMedicacio(
            "...",//1
            "...",
            "...",
            "..."
        )
        med.insertDataMedicacio(
            "Ibuprofèn",//2
            "Nurofen",
            "Comprimits",
            "Mal de cap, marejos"
        )
        med.insertDataMedicacio(
            "Paracetamol",//3
            "Panadol",
            "Xarop",
            "Nàusees, erupcions cutànies"
        )
        med.insertDataMedicacio(
            "Amoxicil·lina",//4
            "Amoxil",
            "Injeccions",
            "Diarrhea, reaccions al·lèrgiques"
        )
        med.insertDataMedicacio(
            "Loratadina",//5
            "Claritin",
            "Comprimits",
            "Somnolència, sequedat de boca"
        )
        med.insertDataMedicacio(
            "Omeprazol",//6
            "Losec",
            "Càpsules",
            "Dolor abdominal, gasos"
        )
        med.insertDataMedicacio(
            "Penicilina",//7
            "Pfizer",
            "Comprimits",
            "Reacció al·lèrgica"
        )
        med.insertDataMedicacio(
            "Aspirina",//8
            "Bayer",
            "Comprimits",
            "Nàusees"
        )
        med.insertDataMedicacio(
            "Sulfonamida",//9
            "Sandoz",
            "Comprimits",
            "Erupcions cutànies"
        )
        med.insertDataMedicacio(
            "Codeína",//10
            "Ratiopharm",
            "Xarop",
            "Somnolència"
        )





        //hospitalitzacions
        val motivhospit = HospitalDatabaseManager()
        val motivHosp = arrayOf(
            "Cirurgia programada",
            "Emergencia médica",
            "Infecció greu",
            "Malaltia crónica descompensada",
            "Recuperació postoperatoria",
            "Tractament de cáncer",
            "Problemes respiratoris",
            "Complicacions del embaràs",
            "Accident o trauma",
            "Malalties cardiovasculars"
        )
        for (i in motivHosp.indices) {
            motivhospit.saveData(motivHosp[i], "motivhospit")
        }

        val hospital = HospitalDatabaseManager()
        hospital.insertDataHospitalitzacio("19-04-2023", "23-04-2023",
            "Hospital Sant Joan",
            1, "53399387B")
        hospital.insertDataHospitalitzacio("24-02-2024", "02-03-2024",
            "Hospital Sant Joan",
            2, "53389287B")
        hospital.insertDataHospitalitzacio(
            "01-03-2023",
            "10-03-2023",
            "Hospital Arnau de Vilanova",
            1, // Cirurgia programada
            "53389287C"
        )

        hospital.insertDataHospitalitzacio(
            "15-09-2023",
            "20-09-2023",
            "Hospital Arnau de Vilanova",
            2, // Emergència mèdica
            "53389287C"
        )

        hospital.insertDataHospitalitzacio(
            "05-08-2023",
            "12-08-2023",
            "Clínica Salus Infirmorum",
            3, // Infecció greu
            "53396757D"
        )

        hospital.insertDataHospitalitzacio(
            "10-09-2023",
            "15-09-2023",
            "Clínica Salus Infirmorum",
            9, // Accident o trauma
            "53396757D"
        )

        hospital.insertDataHospitalitzacio(
            "01-10-2023",
            "05-10-2023",
            "Clínica Salus Infirmorum",
            5, // Recuperació postoperatòria
            "53396757D"
        )
        val tract = TractHospitalDatabaseManager()

        tract.insertDataTractamentHosp(
            1,
            "08:00",
            "12:00",
            "",
            "",
            2, // id medicament 1
            3, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "19-04-2023",
            "23-04-2023"
        )

        tract.insertDataTractamentHosp(
            2,
            "09:00",
            "13:00",
            "",
            "",
            4, // id medicament 1
            5, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "24-02-2024",
            "02-03-2024"
        )

        tract.insertDataTractamentHosp(
            3,
            "10:00",
            "14:00",
            "",
            "",
            6, // id medicament 1
            7, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "01-03-2023",
            "10-03-2023"
        )


        tract.insertDataTractamentHosp(
            4,
            "08:00",
            "12:00",
            "18:00",
            "22:00",
            2, // id medicament 1
            3, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "15-09-2023",
            "20-09-2023"
        )

        tract.insertDataTractamentHosp(
            5,
            "09:00",
            "13:00",
            "",
            "",
            4, // id medicament 1
            5, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "05-08-2023",
            "12-08-2023"
        )

        tract.insertDataTractamentHosp(
            6,
            "10:00",
            "14:00",
            "",
            "",
            6, // id medicament 1
            7, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "10-09-2023",
            "15-09-2023"
        )

        tract.insertDataTractamentHosp(
            7,
            "07:00",
            "11:00",
            "",
            "",
            8, // id medicament 1
            9, // id medicament 2
            0, // id medicament 3 (no s'utilitza)
            0, // id medicament 4 (no s'utilitza)
            "01-10-2023",
            "05-10-2023"
        )
        








    }



    fun initUI(){
        iniNavigation()
        createDatabase()
        createTables()
        //testingDataBase()
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
    //mostrar
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