package com.example.projectebiocorpalbertcelery.ui.medicacio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentMedicacioBinding
import com.example.projectebiocorpalbertcelery.ui.hopitalit.MotivPopupDialog


class MedicacioFragment : Fragment() {
    private var _binding: FragmentMedicacioBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseManager: DatabaseManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicacioBinding.inflate(layoutInflater, container, false)
        databaseManager = DatabaseManager()
        //databaseManager.openDatabase(requireContext())

        val optionsFormaPres: String = "SELECT nom FROM formaPres"
        val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsFormaPres)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.formaPresSpinner.adapter = adapter
        binding.newFormaPresBtn.setOnClickListener {
            val dialog = MotivPopupDialog()
            dialog.show(parentFragmentManager, "MotivPopupDialog")
        }
        binding.saveMedBtn.setOnClickListener {
            val nom = binding.nMedEdit.text.toString()
            val marca = binding.marcaEdit.text.toString()
            val formaPres = binding.formaPresSpinner.selectedItem.toString()
            val efecteSecundari = binding.efectSecundEdit.text.toString()
            val result = databaseManager.insertDataMedicacio(nom, marca, formaPres, efecteSecundari)
            if (result) {
                clearMedicacio()
                Toast.makeText(requireContext(), "Data Inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error try again", Toast.LENGTH_SHORT).show()
            }

        }
        binding.newEntryMedBtn.setOnClickListener {
            clearMedicacio()
        }
        return binding.root
    }

    private fun clearMedicacio() {
        binding.nMedEdit.text?.clear()
        binding.marcaEdit.text?.clear()
        binding.efectSecundEdit.text?.clear()
    }

}