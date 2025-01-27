package com.example.projectebiocorpalbertcelery.ui.pacient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


//todo VIEWMODEL*
class PacientFragmentViewModel: ViewModel() {
    private val _loadPacientTrigger = MutableLiveData<String>()
    val loadPacientTrigger: LiveData<String> = _loadPacientTrigger

    fun triggerLoadPacient(dni: String) {
        _loadPacientTrigger.value = dni
    }
}