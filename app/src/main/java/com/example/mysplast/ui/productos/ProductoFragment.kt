package com.example.mysplast.ui.productos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mysplast.databinding.FragmentProductoBinding

class ProductoFragment : Fragment() {

    var mpref: SharedPreferences? = null
    var payload: String? = null

    private var _binding: FragmentProductoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productoViewModel =
            ViewModelProvider(this).get(ProductoViewModel::class.java)

        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")

        Log.d("tokeeeeeeeeeeeeeeeeeeeeeeeeeeeen",payload.toString())

        _binding = FragmentProductoBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textProducto
        productoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}