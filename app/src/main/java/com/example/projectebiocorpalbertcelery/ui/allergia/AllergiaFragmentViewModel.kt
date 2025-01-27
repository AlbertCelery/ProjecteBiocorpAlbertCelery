package com.example.projectebiocorpalbertcelery.ui.allergia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllergiaFragmentViewModel: ViewModel() {
    private val _loadAllergiaTrigger = MutableLiveData<String>()
    val loadAllergiaTrigger: LiveData<String> = _loadAllergiaTrigger
    fun triggerLoadAllergia(dni: String) {
        _loadAllergiaTrigger.value = dni
    }





}