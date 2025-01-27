package com.example.projectebiocorpalbertcelery.ui.medicacio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MedicacioFragmentViewModel: ViewModel() {
    private val _loadMedicacioTrigger = MutableLiveData<Int>()
    val loadMedicacioTrigger: LiveData<Int> = _loadMedicacioTrigger
    fun triggerLoadMedicacio(idmed: Int) {
        _loadMedicacioTrigger.value = idmed
    }

}