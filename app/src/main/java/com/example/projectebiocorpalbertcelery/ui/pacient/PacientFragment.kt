package com.example.projectebiocorpalbertcelery.ui.pacient


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentPacientBinding

  import com.example.projectebiocorpalbertcelery.ui.home.MainActivity
import com.example.projectebiocorpalbertcelery.ui.medicacio.MedicacioFragmentViewModel
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*interface pacientFragmentListener {
    fun loadPacient(dni: String)

}*/

class PacientFragment : Fragment()/*, pacientFragmentListener */{
    private var _binding: FragmentPacientBinding? = null
    private val binding get() = _binding!!
    private var cursor: Cursor? = null


    //todo VIEWMODEL*
    private val sharedViewModel: PacientFragmentViewModel by activityViewModels()


    private lateinit var databaseManager: DatabaseManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPacientBinding.inflate(layoutInflater, container, false)
        clearPacient()
        databaseManager = DatabaseManager()
        binding.newEntryPacientBtn.setOnClickListener {
            clearPacient()

             (activity as? MainActivity)?.clearNomPacient()
        }


        binding.savePacientBtn.setOnClickListener {
            if (checkdatasave()){
                val dni = binding.dniEditText.text.toString()
                val nom = binding.NomEditText.text.toString()
                val cognom1 = binding.Cognom1EditText.text.toString()
                val cognom2 = binding.Cognom2EditText.text.toString()
                val ciutatN = binding.CiutatNEditText.text.toString()
                val ciutatR = binding.CiutatREditText.text.toString()
                val carrer = binding.CarrerEditText.text.toString()
                val dataNaixement = binding.NaixementEditText.text.toString()
                val codiPostal =
                    databaseManager.convertStringToInt(binding.CodiPostalEditText.text.toString())
                val result = databaseManager.insertDataPacient(
                    dni,
                    nom,
                    cognom1,
                    cognom2,
                    dataNaixement,
                    ciutatN,
                    ciutatR,
                    carrer,
                    codiPostal!!
                )
                if (result) {
                    databaseManager.genAlergiaStack(dni)
                    clearPacient()

                    Toast.makeText(requireContext(), "Data Inserted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error try again", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(requireContext(), "Error check data", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }


    //todo VIEWMODEL*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.loadPacientTrigger.observe(viewLifecycleOwner) { dni ->
            loadPacient(dni)
        }
    }
    fun checkdatasave():Boolean{

        val flag1 = (binding.dniEditText.text.toString() != "") && (binding.NomEditText.text.toString() != "") && (binding.Cognom1EditText.text.toString() != "") && (binding.Cognom2EditText.text.toString() != "") && (binding.NaixementEditText.text.toString() != "")
                && (binding.CiutatNEditText.text.toString() != "") && (binding.CiutatREditText.text.toString() != "") && (binding.CarrerEditText.text.toString() != "") && (binding.CodiPostalEditText.text.toString() != "")
            val flag2 = (isValidDate(binding.NaixementEditText.text.toString()))
        return flag2 && flag1
    }
    fun isValidDate(date: String): Boolean {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        sdf.isLenient = false // Esto asegura que la fecha sea válida (por ejemplo, no permitirá el 30 de febrero)
        return try {
            val parsedDate: Date? = sdf.parse(date)
            parsedDate != null
        } catch (e: ParseException) {
            false
        }
    }



    private fun clearPacient(){
        binding.dniEditText.text.clear()     //dni
        binding.NomEditText.text.clear()        //nom
        binding.Cognom1EditText.text.clear()// cognom1
        binding.Cognom2EditText.text.clear()//cognom2
        binding.NaixementEditText.text.clear()//  dataNaixement
        binding.CiutatNEditText.text.clear()//  ciutatN
        binding.CiutatREditText.text.clear()// ciutatR
        binding.CarrerEditText.text.clear()//  carrer
        binding.CodiPostalEditText.text.clear()// codiPostal
        binding.EdatNumberView.text = ""//EdatNumberView


    }
    /*override*/ fun loadPacient(dni: String){
        //binding.dniEditText.setText(dni)
               databaseManager = DatabaseManager()
                databaseManager.loadDatabase()
                cursor = databaseManager.getDatabyDNIPacient(dni)
                if (cursor != null && cursor!!.moveToFirst()) {
                    val nom = cursor!!.getString(cursor!!.getColumnIndexOrThrow("nom"))
                    val cognom1 = cursor!!.getString(cursor!!.getColumnIndexOrThrow("cognom1"))
                    val cognom2 = cursor!!.getString(cursor!!.getColumnIndexOrThrow("cognom2"))
                    val dataNaixement = cursor!!.getString(cursor!!.getColumnIndexOrThrow("naixement"))
                    val ciutatN = cursor!!.getString(cursor!!.getColumnIndexOrThrow("ciutatN"))
                    val ciutatR = cursor!!.getString(cursor!!.getColumnIndexOrThrow("ciutatR"))
                    val carrer = cursor!!.getString(cursor!!.getColumnIndexOrThrow("carrer"))
                    val codiPostal = cursor!!.getInt(cursor!!.getColumnIndexOrThrow("codPost"))
                    val naixement = cursor!!.getString(cursor!!.getColumnIndexOrThrow("naixement"))
                    val edad = databaseManager.getAgePacient(naixement)
                    binding.NomEditText.setText(nom)
                    binding.Cognom1EditText.setText(cognom1)
                    binding.Cognom2EditText.setText(cognom2)
                    binding.NaixementEditText.setText(dataNaixement)
                    binding.CiutatNEditText.setText(ciutatN)
                    binding.CiutatREditText.setText(ciutatR)
                    binding.CarrerEditText.setText(carrer)
                    binding.CodiPostalEditText.setText(codiPostal.toString())
                    binding.dniEditText.setText(dni)
                    binding.EdatNumberView.text = edad.toString()
                }else{
                    Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
                }

    }




}




