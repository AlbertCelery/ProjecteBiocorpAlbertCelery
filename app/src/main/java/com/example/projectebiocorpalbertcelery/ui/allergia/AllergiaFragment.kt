package com.example.projectebiocorpalbertcelery.ui.allergia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectebiocorpalbertcelery.databinding.FragmentAllergiaBinding


class AllergiaFragment : Fragment() {

    private var _binding: FragmentAllergiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllergiaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}