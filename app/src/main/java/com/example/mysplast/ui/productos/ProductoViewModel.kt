package com.example.mysplast.ui.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is productos Fragment"
    }
    val text: LiveData<String> = _text
}