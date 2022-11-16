package com.example.mysplast.ui.productos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ListadoProductosAdapter
import com.example.mysplast.databinding.FragmentProductoBinding
import com.example.mysplast.model.Producto
import com.example.mysplast.services.ProductosService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.mysplast.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductoFragment : Fragment(), OnClickListener{

    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentProductoBinding? = null
    private lateinit var listAdapterProductos: ListadoProductosAdapter
    lateinit var btnBuscarProducto: Button
    lateinit var edtTerminoProducto: EditText

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mpref = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        _binding = FragmentProductoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        configuracionRecyclerView()
        listadoProductos(payload!!)
        btnBuscarProducto = root.findViewById(R.id.btnConsultarProducto)
        edtTerminoProducto = root.findViewById(R.id.edtTerminoProducto)
        btnBuscarProducto.setOnClickListener {
            var termino: String = edtTerminoProducto.text.toString()
            if (termino==""){
                Toast.makeText(this.context,"Tiene que ingresar al menos un término para poder realizar la consulta!",Toast.LENGTH_SHORT).show()
            }else {
                buscarProductoxTermino(payload!!,termino)
            }


        }
        return root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configuracionRecyclerView(){
        listAdapterProductos = ListadoProductosAdapter(this)
        binding.rcvProductos.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapterProductos
        }
    }

    private fun listadoProductos(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(GsonConverterFactory.create()).build()
        val pro: ProductosService = retro.create(ProductosService::class.java)
        val call: Call<List<Producto>> = pro.getListadoProductos("Bearer $token")
        call.enqueue(object : Callback<List<Producto>>{
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                listAdapterProductos.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun buscarProductoxTermino(token: String, term: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(GsonConverterFactory.create()).build()
        val pro: ProductosService = retro.create(ProductosService::class.java)
        val call: Call<List<Producto>> = pro.getProductosxTermino("Bearer $token", term)
        call.enqueue(object : Callback<List<Producto>>{
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                listAdapterProductos.notifyDataSetChanged()
                listAdapterProductos.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}