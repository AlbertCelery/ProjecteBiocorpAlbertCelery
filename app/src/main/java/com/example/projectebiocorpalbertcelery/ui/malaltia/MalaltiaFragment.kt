package com.example.projectebiocorpalbertcelery.ui.malaltia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectebiocorpalbertcelery.databinding.FragmentMalaltiaBinding


class MalaltiaFragment : Fragment() {
    private var _binding: FragmentMalaltiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMalaltiaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}