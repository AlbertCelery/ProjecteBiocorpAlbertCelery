package com.example.projectebiocorpalbertcelery.ui.hopitalit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HospitalitFragmentViewModel: ViewModel() {
    private val _loadHospitalitTrigger = MutableLiveData<String>()
    val loadHospitalitTrigger: LiveData<String> = _loadHospitalitTrigger
    fun triggerLoadHospitalit(dni: String) {
        _loadHospitalitTrigger.value = dni
    }

}