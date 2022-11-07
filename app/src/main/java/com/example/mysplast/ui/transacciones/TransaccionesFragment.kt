package com.example.mysplast.ui.transacciones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysplast.R

class TransaccionesFragment : Fragment() {

    companion object {
        fun newInstance() = TransaccionesFragment()
    }

    private lateinit var viewModel: TransaccionesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transacciones, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransaccionesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}