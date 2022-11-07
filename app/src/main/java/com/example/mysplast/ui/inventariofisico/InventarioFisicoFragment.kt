package com.example.mysplast.ui.inventariofisico

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysplast.R

class InventarioFisicoFragment : Fragment() {

    companion object {
        fun newInstance() = InventarioFisicoFragment()
    }

    private lateinit var viewModel: InventarioFisicoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventario_fisico, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InventarioFisicoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}