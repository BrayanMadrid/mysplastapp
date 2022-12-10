package com.example.mysplast

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ItemOrdenCompraAdapter
import com.example.mysplast.adapters.ItemOrdenProdAdapter
import com.example.mysplast.databinding.ActivityViewOrdenPactivityBinding
import com.example.mysplast.databinding.ActivityViewOrderCactivityBinding
import com.example.mysplast.model.Ordencompra
import com.example.mysplast.model.Ordenprod
import com.example.mysplast.services.OrdenCompraService
import com.example.mysplast.services.OrdenProdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ViewOrderCActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding : ActivityViewOrderCactivityBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var itemOrdenCompraAdapter: ItemOrdenCompraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrderCactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        val ordenObtenida: Intent = intent
        val ordenseleccionada =  ordenObtenida.getStringExtra("idordencompra")
        configuracionRecyclerView()
        obtenerOrdenCompra(payload!!, ordenseleccionada.toString())
    }

    private fun obtenerOrdenCompra(token: String, id: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenCompraService = retro.create(OrdenCompraService::class.java)
        val call: Call<Ordencompra> = pro.getOrdenCompraxID("Bearer $token", id)
        call.enqueue(object : Callback<Ordencompra> {
            override fun onResponse(
                call: Call<Ordencompra>,
                response: Response<Ordencompra>

            ) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(response.body()?.fecha)
                var nroOrden: TextView = findViewById(R.id.edtItemNroOrdenCompra)
                var fecha: TextView = findViewById(R.id.edtItemFechaCompra)
                var estado: TextView = findViewById(R.id.edtItemEstadoCompra)
                var responsable: TextView = findViewById(R.id.edtItemResponsableCompra)
                var razonsocial: TextView = findViewById(R.id.edtItemProveedorCompra)
                var ruc: TextView = findViewById(R.id.edtItemRUCCompra)
                var direccion: TextView = findViewById(R.id.edtItemProveedorDireccionCompra)
                var almacenin: TextView = findViewById(R.id.edtItemAlmacenInCompra)
                var sectorin: TextView = findViewById(R.id.edtItemSectorInCompra)
                var moneda: TextView = findViewById(R.id.edtTipoPagoCompra)
                var total: TextView = findViewById(R.id.edtTotalCompra)
                var comentarios: TextView = findViewById(R.id.edtItemComentariosCompra)
                nroOrden.text = response.body()?.nroordencompra.toString()
                fecha.text = currentDate
                responsable.text = response.body()?.empleado?.nombres + " " +response.body()?.empleado?.ape_pat + " " + response.body()?.empleado?.ape_mat
                razonsocial.text = response.body()?.proveedor?.razonsocial
                ruc.text = response.body()?.proveedor?.nrodoc
                direccion.text = response.body()?.proveedor?.direccion
                almacenin.text = response.body()?.sector?.id_almacen?.nom_almacen
                sectorin.text = response.body()?.sector?.nom_sector
                moneda.text = response.body()?.moneda
                comentarios.text = response.body()?.desc_ordencompra
                itemOrdenCompraAdapter.submitList(response.body()?.items)

                if(response.body()?.estado.equals("I")){
                    estado.text = "Inventariado"
                } else if(response.body()?.estado.equals("P")){
                    estado.text = "Pendiente"
                }else if(response.body()?.estado.equals("A")){
                    estado.text = "Aprobado"
                } else {
                    estado.text = "Anulado"
                }

                if(response.body()?.moneda.equals("LOCAL")){
                    total.text = "S/. " + response.body()?.total.toString()
                } else {
                    total.text = "$ " + response.body()?.total.toString()
                }

            }

            override fun onFailure(call: Call<Ordencompra>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun configuracionRecyclerView(){
        itemOrdenCompraAdapter = ItemOrdenCompraAdapter(this)
        binding.rcvItemOrdenCompra.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemOrdenCompraAdapter
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}