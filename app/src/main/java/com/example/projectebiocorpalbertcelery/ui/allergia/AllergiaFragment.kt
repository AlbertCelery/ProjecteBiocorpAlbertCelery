package com.example.projectebiocorpalbertcelery.ui.allergia

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.AllergiaDatabaseManager
import com.example.projectebiocorpalbertcelery.data.DatabaseManager


import com.example.projectebiocorpalbertcelery.databinding.FragmentAllergiaBinding


class AllergiaFragment : Fragment() {

    private var _binding: FragmentAllergiaBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseManager: DatabaseManager
    private lateinit var allergiaManager: AllergiaDatabaseManager
    private var cursor: Cursor? = null
    private val sharedViewModel: AllergiaFragmentViewModel by activityViewModels()
    private var dni: String = ""
    private var isNavigating = false
    private lateinit var viewModel: AllergiaFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllergiaBinding.inflate(layoutInflater, container, false)
        databaseManager = DatabaseManager()
        allergiaManager = AllergiaDatabaseManager()
        databaseManager.loadDatabase()
        alergiaInit()


        // alergiaInit()
        //TODO Acabar funcions dels botons

        binding.medInfoBtn1.setOnClickListener {
            findNavController().navigate(R.id.action_allergiaFragment_to_medicacioFragment)


        }

        binding.medInfoBtn2.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.medInfoBtn3.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.medInfoBtn4.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.medInfoBtn5.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.alergiaSave1Btn.setOnClickListener {
            val medicament1 = binding.medAllergiaSpinner1.selectedItemPosition + 1
            val descMedicament1 = binding.descAllergiaEdit1.text.toString()
            allergiaManager.updateAlergia(medicament1, 1, descMedicament1, dni)

        }
        binding.alergiaSave2Btn.setOnClickListener {
            val medicament2 = binding.medAllergiaSpinner2.selectedItemPosition + 1
            val descMedicament2 = binding.descAllergiaEdit2.text.toString()
            allergiaManager.updateAlergia(medicament2, 2, descMedicament2, dni)
        }
        binding.alergiaSave3Btn.setOnClickListener {
            val medicament3 = binding.medAllergiaSpinner3.selectedItemPosition + 1
            val descMedicament3 = binding.descAllergiaEdit3.text.toString()
            allergiaManager.updateAlergia(medicament3, 3, descMedicament3, dni)
        }
        binding.alergiaSave4Btn.setOnClickListener {
            val medicament4 = binding.medAllergiaSpinner4.selectedItemPosition + 1
            val descMedicament4 = binding.descAllergiaEdit4.text.toString()
            allergiaManager.updateAlergia(medicament4, 4, descMedicament4, dni)
        }
        binding.alergiaSave5Btn.setOnClickListener {
            val medicament5 = binding.medAllergiaSpinner5.selectedItemPosition + 1
            val descMedicament5 = binding.descAllergiaEdit5.text.toString()
            allergiaManager.updateAlergia(medicament5, 5, descMedicament5, dni)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.loadAllergiaTrigger.observe(viewLifecycleOwner) { dni ->
            loadAlergia(dni)



        }




    }
    fun clearFields() {
        binding.medAllergiaSpinner1.setSelection(0)
        binding.medAllergiaSpinner2.setSelection(0)
        binding.medAllergiaSpinner3.setSelection(0)
        binding.medAllergiaSpinner4.setSelection(0)
        binding.medAllergiaSpinner5.setSelection(0)
        binding.descAllergiaEdit1.text?.clear()
        binding.descAllergiaEdit2.text?.clear()
        binding.descAllergiaEdit3.text?.clear()
        binding.descAllergiaEdit4.text?.clear()
        binding.descAllergiaEdit5.text?.clear()

    }

        fun loadAlergia(dni: String) {

            this.dni = dni
            databaseManager = DatabaseManager()
            databaseManager.loadDatabase()
            cursor = allergiaManager.getAlergiaCursor(dni, 1)
            if (cursor != null && cursor!!.moveToFirst()) {
                val idMedicament = cursor!!.getInt(0)
                if (idMedicament > 0) {
                    val indexmed = idMedicament - 1
                    binding.medAllergiaSpinner1.setSelection(indexmed)
                    binding.descAllergiaEdit1.setText(cursor!!.getString(2))
                }
            }
            cursor = allergiaManager.getAlergiaCursor(dni, 2)
            if (cursor != null && cursor!!.moveToFirst()) {
                val idMedicament = cursor!!.getInt(0)
                if (idMedicament > 0) {
                    val indexmed = idMedicament - 1
                    binding.medAllergiaSpinner2.setSelection(indexmed)
                    binding.descAllergiaEdit2.setText(cursor!!.getString(2))
                }
            }
            cursor = allergiaManager.getAlergiaCursor(dni, 3)
            if (cursor != null && cursor!!.moveToFirst()) {
                val idMedicament = cursor!!.getInt(0)
                if (idMedicament > 0) {
                    val indexmed = idMedicament - 1
                    binding.medAllergiaSpinner3.setSelection(indexmed)
                    binding.descAllergiaEdit3.setText(cursor!!.getString(2))
                }
            }
            cursor = allergiaManager.getAlergiaCursor(dni, 4)
            if (cursor != null && cursor!!.moveToFirst()) {
                val idMedicament = cursor!!.getInt(0)
                if (idMedicament > 0) {
                    val indexmed = idMedicament - 1
                    binding.medAllergiaSpinner4.setSelection(indexmed)
                    binding.descAllergiaEdit4.setText(cursor!!.getString(2))
                }
            }
            cursor = allergiaManager.getAlergiaCursor(dni, 5)
            if (cursor != null && cursor!!.moveToFirst()) {
                val idMedicament = cursor!!.getInt(0)
                if (idMedicament > 0) {
                    val indexmed = idMedicament - 1
                    binding.medAllergiaSpinner5.setSelection(indexmed)
                    binding.descAllergiaEdit5.setText(cursor!!.getString(2))
                }
            }
        }




        fun alergiaInit() {

            val optionsMedicacio: String = "SELECT nom FROM medicament"
            val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsMedicacio)
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.medAllergiaSpinner1.adapter = adapter
            binding.medAllergiaSpinner2.adapter = adapter
            binding.medAllergiaSpinner3.adapter = adapter
            binding.medAllergiaSpinner4.adapter = adapter
            binding.medAllergiaSpinner5.adapter = adapter
        }

        /*fun updateAlergia(dni: String){

    }*/



    }