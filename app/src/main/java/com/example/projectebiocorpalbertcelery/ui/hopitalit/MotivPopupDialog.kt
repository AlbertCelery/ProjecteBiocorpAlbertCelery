package com.example.projectebiocorpalbertcelery.ui.hopitalit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentMotivPopupDialogBinding


class MotivPopupDialog : DialogFragment() {
    private var _binding: FragmentMotivPopupDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentMotivPopupDialogBinding.inflate(layoutInflater, container, false)

        binding.motivSaveBtn.setOnClickListener {
            val motiv = binding.motivPopupEdit.text.toString()
            val databaseManager = DatabaseManager()
            databaseManager.saveData(motiv, "motivhospit")
            dismiss()
        }
        binding.motivCloseBtn.setOnClickListener {
            dismiss()
        }
        return binding.root

    }

}