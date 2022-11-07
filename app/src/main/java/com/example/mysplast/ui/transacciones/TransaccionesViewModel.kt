package com.example.mysplast.ui.transacciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransaccionesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is transferencias Fragment"
    }
    val text: LiveData<String> = _text
}