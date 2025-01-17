package com.example.projectebiocorpalbertcelery.ui.hopitalit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentHospitalitBinding
import com.example.projectebiocorpalbertcelery.ui.home.MainActivity


class HospitalitFragment : Fragment() {
    private var _binding: FragmentHospitalitBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseManager: DatabaseManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalitBinding.inflate(layoutInflater, container, false)

        databaseManager = DatabaseManager()
        //databaseManager.openDatabase(requireContext())

        val optionsMotiv: String = "SELECT nom FROM motivhospit"
        val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsMotiv)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMotiuHosp.adapter = adapter
        binding.nouMotivBtn.setOnClickListener {
            val dialog = MotivPopupDialog()
            dialog.show(parentFragmentManager, "MotivPopupDialog")
        }
        return binding.root
    }


}