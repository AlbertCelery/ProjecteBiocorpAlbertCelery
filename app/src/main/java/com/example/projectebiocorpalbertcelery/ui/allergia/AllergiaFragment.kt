package com.example.projectebiocorpalbertcelery.ui.allergia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projectebiocorpalbertcelery.R


import com.example.projectebiocorpalbertcelery.databinding.FragmentAllergiaBinding


class AllergiaFragment : Fragment() {

    private var _binding: FragmentAllergiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllergiaBinding.inflate(layoutInflater, container, false)
        //TODO Acabar funcions dels botons
        binding.medInfoBtn1.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
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
        return binding.root
    }


}