package com.example.mysplast

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ListadoOrdenCompraAdapter
import com.example.mysplast.databinding.ActivityComprasBinding
import com.example.mysplast.model.Almacen
import com.example.mysplast.model.Ordencompra
import com.example.mysplast.model.Sector
import com.example.mysplast.services.AlmacenService
import com.example.mysplast.services.OrdenCompraService
import com.example.mysplast.services.SectorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ActivityCompras : AppCompatActivity(), OnClickListener {

    lateinit var fechaInCompra: EditText
    lateinit var fechaFinCompra: EditText
    private lateinit var  binding : ActivityComprasBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var listadoOrdenCompraAdapter: ListadoOrdenCompraAdapter
    private var almacen: String? = ""
    private var sector: String? = ""
    lateinit var spalmacen: Spinner
    lateinit var spsector: Spinner
    lateinit var btnConsultarCompra: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        fechaInCompra =  findViewById(R.id.edtFechaInCompra)
        fechaFinCompra = findViewById(R.id.edtFechaFinCompra)

        spalmacen = findViewById(R.id.spalmacenCompra)
        spsector = findViewById(R.id.spsectorCompra)
        btnConsultarCompra = findViewById(R.id.btnConsultarCompra)
        btnConsultarCompra.setOnClickListener {

            var almacenSeleccionado: String =  almacen.toString()
            var sectorSeleccionado: String =  sector.toString()
            var fechaInicioSeleccionada: String = fechaInCompra.text.toString()
            var fechaFinalSeleccionada: String  = fechaFinCompra.text.toString()
            buscarOrdenesCompra(payload!!,sectorSeleccionado, almacenSeleccionado, fechaInicioSeleccionada, fechaFinalSeleccionada)

        }

        fechaInCompra.setOnClickListener(View.OnClickListener {
            CargarFechaIn()
        })
        fechaFinCompra.setOnClickListener(View.OnClickListener {
            CargarFechaFin()
        })

        spalmacen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val a: Almacen = parent.getItemAtPosition(position) as Almacen
                almacen = a.id_almacen
                obtenerSectores(payload!!, a.id_almacen)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spsector.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val s: Sector = parent.getItemAtPosition(position) as Sector
                sector = s.id_sector
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        obtenerAlmacenes(payload!!)
        configuracionRecyclerView()
        listadoOrdenesCompra(payload!!)

    }


    private fun CargarFechaIn() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, date ->
                fechaInCompra?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaInCompra?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun CargarFechaFin() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, date ->
                fechaFinCompra?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaFinCompra?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


    private fun configuracionRecyclerView(){
        listadoOrdenCompraAdapter = ListadoOrdenCompraAdapter(this)
        binding.rcvOrdenCompra.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listadoOrdenCompraAdapter
        }
    }

    private fun listadoOrdenesCompra(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenCompraService = retro.create(OrdenCompraService::class.java)
        val call: Call<ArrayList<Ordencompra>> = pro.getListadoOrdenesCompra("Bearer $token")
        call.enqueue(object : Callback<ArrayList<Ordencompra>> {
            override fun onResponse(
                call: Call<ArrayList<Ordencompra>>,
                response: Response<ArrayList<Ordencompra>>

            ) {
                listadoOrdenCompraAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Ordencompra>>, t: Throwable) {

            }

        })
    }

    private fun obtenerSectores(token: String, almacen: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: SectorService = retro.create(SectorService::class.java)
        val call: Call<ArrayList<Sector>> = pro.getObtenerSectorxAlmacen("Bearer $token", almacen)
        call.enqueue(object : Callback<ArrayList<Sector>> {
            override fun onResponse(
                call: Call<ArrayList<Sector>>,
                response: Response<ArrayList<Sector>>

            ) {
                if(response.isSuccessful()){
                    var sub: ArrayList<Sector>
                    sub = response.body()!!
                    val adapter: ArrayAdapter<Sector> = ArrayAdapter<Sector>(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        sub
                    )
                    spsector.setAdapter(adapter)
                }

            }

            override fun onFailure(call: Call<ArrayList<Sector>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun obtenerAlmacenes(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: AlmacenService = retro.create(AlmacenService::class.java)
        val call: Call<ArrayList<Almacen>> = pro.getListadoAlmacenes("Bearer $token")
        call.enqueue(object : Callback<ArrayList<Almacen>> {
            override fun onResponse(
                call: Call<ArrayList<Almacen>>,
                response: Response<ArrayList<Almacen>>

            ) {
                if (response.isSuccessful()) {
                    var al: ArrayList<Almacen>
                    al = response.body()!!
                    val adapter = ArrayAdapter(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        al
                    )
                    spalmacen.adapter = adapter

                }
            }
            override fun onFailure(call: Call<ArrayList<Almacen>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun buscarOrdenesCompra(token: String, sector: String, almacen: String, fecha1: String, fecha2: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenCompraService = retro.create(OrdenCompraService::class.java)
        val call: Call<ArrayList<Ordencompra>> = pro.getFiltroOrdenesCompra("Bearer $token", sector, almacen, fecha1, fecha2)
        call.enqueue(object : Callback<ArrayList<Ordencompra>> {
            override fun onResponse(
                call: Call<ArrayList<Ordencompra>>,
                response: Response<ArrayList<Ordencompra>>

            ) {
                if(response.isSuccessful()){
                    listadoOrdenCompraAdapter.notifyDataSetChanged()
                    listadoOrdenCompraAdapter.submitList(response.body())
                }

            }

            override fun onFailure(call: Call<ArrayList<Ordencompra>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}