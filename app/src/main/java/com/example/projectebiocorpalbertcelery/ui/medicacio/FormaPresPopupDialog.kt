package com.example.projectebiocorpalbertcelery.ui.medicacio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentFormaPresPopupDialogBinding


class FormaPresPopupDialog : DialogFragment() {
    private var _binding: FragmentFormaPresPopupDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormaPresPopupDialogBinding.inflate(layoutInflater, container, false)
        binding.formaPresSaveBtn.setOnClickListener {
            val motiv = binding.formaPresPopupEdit.text.toString()
            val databaseManager = DatabaseManager()
            databaseManager.saveData(motiv, "motivhospit")
            dismiss()
        }
        binding.formaPresCloseBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}