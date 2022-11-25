package com.example.mysplast.ui.transacciones

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.mysplast.*
import com.example.mysplast.databinding.FragmentOrdenesBinding
import com.example.mysplast.databinding.FragmentTransaccionesBinding

class TransaccionesFragment : Fragment() {

    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentTransaccionesBinding? = null

    lateinit var btnIngresos: CardView
    lateinit var btnEgresos: CardView
    lateinit var btnTransferencias: CardView

    private val binding get() = _binding!!

    private lateinit var viewModel: TransaccionesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken", "")
        _binding = FragmentTransaccionesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        btnIngresos = root.findViewById(R.id.btnIngresos)
        btnIngresos.setOnClickListener {
            val intent = Intent(this.context, ActivityIngresos::class.java).apply {
                putExtra("payload", payload!!)
            }
            startActivity(intent)
        }
        btnEgresos = root.findViewById(R.id.btnEgresos)
        btnEgresos.setOnClickListener {
            val intent = Intent(this.context, ActivityEgresos::class.java).apply {
                putExtra("payload", payload!!)
            }
            startActivity(intent)
        }
        btnTransferencias = root.findViewById(R.id.btnTransferencias)
        btnTransferencias.setOnClickListener {
            val intent = Intent(this.context, ActivityTransferencias::class.java).apply {
                putExtra("payload", payload!!)
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