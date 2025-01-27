package com.example.projectebiocorpalbertcelery.ui.malaltia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MalaltiaFragmentViewModel: ViewModel() {
    private val _loadMalaltiaTrigger = MutableLiveData<String>()
    val loadMalaltiaTrigger: LiveData<String> = _loadMalaltiaTrigger
    fun triggerLoadMalaltia(dni: String) {
        _loadMalaltiaTrigger.value = dni

    }

}