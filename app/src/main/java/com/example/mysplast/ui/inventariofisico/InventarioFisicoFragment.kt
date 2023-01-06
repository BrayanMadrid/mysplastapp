package com.example.mysplast.ui.inventariofisico

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.R
import com.example.mysplast.adapters.ListadoInventarioFisicoAdapter
import com.example.mysplast.databinding.FragmentInventarioFisicoBinding
import com.example.mysplast.model.InventarioFisico
import com.example.mysplast.services.InventarioFisicoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InventarioFisicoFragment : Fragment(), View.OnClickListener {

    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentInventarioFisicoBinding? = null
    private lateinit var listadoInventarioFisicoAdapter: ListadoInventarioFisicoAdapter

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = InventarioFisicoFragment()
    }

    private lateinit var viewModel: InventarioFisicoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        _binding = FragmentInventarioFisicoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        configuracionRecyclerView()
        listadoInventariosFisicos(payload!!)
        return root
        return inflater.inflate(R.layout.fragment_inventario_fisico, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InventarioFisicoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun configuracionRecyclerView(){
        listadoInventarioFisicoAdapter = ListadoInventarioFisicoAdapter(this)
        binding.rcvInventarioFisico.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listadoInventarioFisicoAdapter
        }
    }

    private fun listadoInventariosFisicos(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: InventarioFisicoService = retro.create(InventarioFisicoService::class.java)
        val call: Call<ArrayList<InventarioFisico>> = pro.getListadoInventarioFisicosProduccion("Bearer $token")
        call.enqueue(object : Callback<ArrayList<InventarioFisico>> {
            override fun onResponse(
                call: Call<ArrayList<InventarioFisico>>,
                response: Response<ArrayList<InventarioFisico>>
            ) {
                listadoInventarioFisicoAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<ArrayList<InventarioFisico>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}