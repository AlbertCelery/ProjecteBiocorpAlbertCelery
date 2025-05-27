package com.example.projectebiocorpalbertcelery.ui.medicacio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.data.MedicacioDatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentFormaPresPopupDialogBinding
import com.example.projectebiocorpalbertcelery.databinding.FragmentSearchMedPopupDialogBinding
import com.example.projectebiocorpalbertcelery.ui.home.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class SearchMedPopupDialog : DialogFragment() {
    private var _binding: FragmentSearchMedPopupDialogBinding? = null
    private lateinit var databaseManager: DatabaseManager
    private lateinit var medicacioManager: MedicacioDatabaseManager
    private val binding get() = _binding!!
    private val sharedViewModel: MedicacioFragmentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSearchMedPopupDialogBinding.inflate(layoutInflater, container, false)
        databaseManager = DatabaseManager()
        medicacioManager = MedicacioDatabaseManager()


        val nombres: MutableList<String> = medicacioManager.obtenerMedicamentos()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.MedSearchSpinner.adapter = adapter
        binding.searchMedBtn.setOnClickListener {

            val nom = binding.MedSearchSpinner.selectedItemPosition +1
            (activity as? MainActivity)?.loadMedicament(nom)


            dismiss()
        }
        binding.searchMedCloseBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


}