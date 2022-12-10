package com.example.mysplast.ui.recetas

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.R
import com.example.mysplast.adapters.ItemRecetaAdapter
import com.example.mysplast.adapters.ListadoProductosAdapter
import com.example.mysplast.adapters.ListadoRecetasAdapter
import com.example.mysplast.databinding.FragmentProductoBinding
import com.example.mysplast.databinding.FragmentRecetasBinding
import com.example.mysplast.databinding.ListadoItemRecetasBinding
import com.example.mysplast.model.Producto
import com.example.mysplast.model.Recetaprod
import com.example.mysplast.services.ProductosService
import com.example.mysplast.services.RecetaProdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecetasFragment : Fragment() , View.OnClickListener {

    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentRecetasBinding? = null
    private lateinit var listadoRecetasAdapter: ListadoRecetasAdapter


    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RecetasFragment()
    }

    private lateinit var viewModel: RecetasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        _binding = FragmentRecetasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        configuracionRecyclerView()
        listadoRecetas(payload!!)
        return root
        return inflater.inflate(R.layout.fragment_recetas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecetasViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun configuracionRecyclerView(){
        listadoRecetasAdapter = ListadoRecetasAdapter(this)
        binding.rcvRecetas.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listadoRecetasAdapter
        }
    }

    private fun listadoRecetas(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: RecetaProdService = retro.create(RecetaProdService::class.java)
        val call: Call<List<Recetaprod>> = pro.getListadoRecetasProduccion("Bearer $token")
        call.enqueue(object : Callback<List<Recetaprod>> {
            override fun onResponse(
                call: Call<List<Recetaprod>>,
                response: Response<List<Recetaprod>>
            ) {
                listadoRecetasAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Recetaprod>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}