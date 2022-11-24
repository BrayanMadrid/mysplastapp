package com.example.mysplast.ui.ordenes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.mysplast.ActivityCompras
import com.example.mysplast.ActivityProduccion
import com.example.mysplast.databinding.FragmentOrdenesBinding
import com.example.mysplast.R

class OrdenesFragment : Fragment() {

    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentOrdenesBinding? = null

    lateinit var btnProduccion: CardView
    lateinit var btnCompras: CardView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        _binding = FragmentOrdenesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        btnProduccion = root.findViewById(R.id.btnProduccion)
        btnProduccion.setOnClickListener {
            val intent =  Intent(this.context, ActivityProduccion::class.java).apply {
                putExtra("payload",  payload!!)
            }
            startActivity(intent)
        }
        btnCompras = root.findViewById(R.id.btnCompras)
        btnCompras.setOnClickListener {
            val intent =  Intent(this.context, ActivityCompras::class.java).apply {
                putExtra("payload",  payload!!)
            }
            startActivity(intent)
        }
            return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}