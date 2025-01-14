package com.example.projectebiocorpalbertcelery.ui.pacient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentPacientBinding

  import com.example.projectebiocorpalbertcelery.ui.home.MainActivity


class PacientFragment : Fragment() {
    private var _binding: FragmentPacientBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseManager: DatabaseManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPacientBinding.inflate(layoutInflater, container, false)
        databaseManager = DatabaseManager()
        binding.newEntryPacientBtn.setOnClickListener {
            clearPacient()

             (activity as? MainActivity)?.clearNomPacient()
        }
        binding.savePacientBtn.setOnClickListener {
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
                clearPacient()

                Toast.makeText(requireContext(), "Data Inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error try again", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
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



}




