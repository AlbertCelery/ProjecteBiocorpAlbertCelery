package com.example.projectebiocorpalbertcelery.ui.medicacio

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.data.MedicacioDatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentMedicacioBinding
import com.example.projectebiocorpalbertcelery.ui.hopitalit.MotivPopupDialog


class MedicacioFragment : Fragment() {
    private var _binding: FragmentMedicacioBinding? = null
    private val binding get() = _binding!!
   // private lateinit var databaseManager: DatabaseManager
    private lateinit var medicacioManager: MedicacioDatabaseManager
    private val sharedViewModel: MedicacioFragmentViewModel by activityViewModels()
    private var currentIndex = 0
    private var cursor: Cursor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicacioBinding.inflate(layoutInflater, container, false)
       // databaseManager = DatabaseManager()
        medicacioManager = MedicacioDatabaseManager()


        val optionsFormaPres: String = "SELECT nom FROM formaPres"
        val nombres: MutableList<String> = medicacioManager.obtenerNombres(optionsFormaPres)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.formaPresSpinner.adapter = adapter


        binding.newFormaPresBtn.setOnClickListener {
            val dialog = FormaPresPopupDialog()
            dialog.show(parentFragmentManager, "FormaPresPopupDialog")
        }


        binding.saveMedBtn.setOnClickListener {
            val nom = binding.nMedEdit.text.toString()
            val marca = binding.marcaEdit.text.toString()
            val formaPres = binding.formaPresSpinner.selectedItem.toString()
            val efecteSecundari = binding.efectSecundEdit.text.toString()
            val result = medicacioManager.insertDataMedicacio(nom, marca, formaPres, efecteSecundari)
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

        binding.searchMedPopupBtn.setOnClickListener {
            val dialog = SearchMedPopupDialog()
            dialog.show(parentFragmentManager, "SearchMedPopupDialog")
        }
        binding.nextMedBtn.setOnClickListener {
            nextmedicament()
        }
        binding.previousMedBtn.setOnClickListener {
            previousmedicament()

        }


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.loadMedicacioTrigger.observe(viewLifecycleOwner) { nom ->
            loadmedicament(nom)
        }
    }


    private fun clearMedicacio() {
        binding.nMedEdit.text?.clear()
        binding.marcaEdit.text?.clear()
        binding.efectSecundEdit.text?.clear()
    }
    fun loadmedicament(idmed: Int) {
        currentIndex = idmed
        val nom = medicacioManager.getmedicacioNom(currentIndex)
        val marca = medicacioManager.getmedicacioMarca(currentIndex)

        val formaPres = currentIndex -1
        val efecteSecundari = medicacioManager.getmedicacioEfsec(currentIndex)
        binding.nMedEdit.setText(nom)
        binding.marcaEdit.setText(marca)
        if (formaPres >= 0) {
            binding.formaPresSpinner.setSelection(formaPres)
        }

        binding.efectSecundEdit.setText(efecteSecundari)
        updateBtn()




    }
    private fun updateReg(){
        cursor = medicacioManager.getMedicamentCursor()
        if (cursor!=null && cursor!!.moveToFirst()){
            loadmedicament(currentIndex)

        } else {
            Toast.makeText(context,"No data", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateBtn(){
        if (cursor != null){
            binding.previousMedBtn.isEnabled = currentIndex >0
            binding.nextMedBtn.isEnabled = currentIndex < cursor!!.count -1

        }
    }

    //FER NEXT MEDICAMENT
    fun nextmedicament() {

        if (currentIndex < cursor!!.count -1){
            loadmedicament(currentIndex++)
        }
    }
    fun previousmedicament() {
        if (currentIndex > 0){
            loadmedicament(currentIndex--)
        }

    }


}