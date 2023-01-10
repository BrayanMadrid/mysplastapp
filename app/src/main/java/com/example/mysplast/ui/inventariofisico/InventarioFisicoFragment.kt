package com.example.mysplast.ui.inventariofisico

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.R
import com.example.mysplast.adapters.ListadoInventarioFisicoAdapter
import com.example.mysplast.databinding.FragmentInventarioFisicoBinding
import com.example.mysplast.model.Almacen
import com.example.mysplast.model.InventarioFisico
import com.example.mysplast.model.Sector
import com.example.mysplast.services.AlmacenService
import com.example.mysplast.services.InventarioFisicoService
import com.example.mysplast.services.SectorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class InventarioFisicoFragment : Fragment(), View.OnClickListener {

    lateinit var fechaInInventarioFisico: EditText
    lateinit var fechaFinInventarioFisico: EditText
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private var _binding: FragmentInventarioFisicoBinding? = null
    private lateinit var listadoInventarioFisicoAdapter: ListadoInventarioFisicoAdapter
    private var almacen: String? = ""
    private var sector: String? = ""
    lateinit var spalmacen: Spinner
    lateinit var spsector: Spinner
    lateinit var btnConsultarInventario: Button

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
        fechaInInventarioFisico =  root.findViewById(R.id.edtFechaInInventarioFisico)
        fechaFinInventarioFisico = root.findViewById(R.id.edtFechaFinInventarioFisico)

        spalmacen = root.findViewById(R.id.spalmacenInventarioFisico)
        spsector = root.findViewById(R.id.spsectorInventarioFisico)
        btnConsultarInventario = root.findViewById(R.id.btnConsultarInventarioFisico)


        fechaInInventarioFisico.setOnClickListener(View.OnClickListener {
            CargarFechaIn()
        })
        fechaFinInventarioFisico.setOnClickListener(View.OnClickListener {
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
        btnConsultarInventario.setOnClickListener {

            var sectorSeleccionado: String =  sector.toString()
            var fechaInicioSeleccionada: String = fechaInInventarioFisico.text.toString()
            var fechaFinalSeleccionada: String  = fechaFinInventarioFisico.text.toString()
            buscarInventariosFisicos(payload!!,sectorSeleccionado, fechaInicioSeleccionada, fechaFinalSeleccionada)

        }
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

    private fun CargarFechaIn() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(activity!!,
            { datePicker, year, month, date ->
                fechaInInventarioFisico?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaInInventarioFisico?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun CargarFechaFin() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(activity!!,
            { datePicker, year, month, date ->
                fechaFinInventarioFisico?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaFinInventarioFisico?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun obtenerSectores(token: String, almacen: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: SectorService = retro.create(SectorService::class.java)
        val call: Call<java.util.ArrayList<Sector>> = pro.getObtenerSectorxAlmacen("Bearer $token", almacen)
        call.enqueue(object : Callback<java.util.ArrayList<Sector>> {
            override fun onResponse(
                call: Call<java.util.ArrayList<Sector>>,
                response: Response<java.util.ArrayList<Sector>>

            ) {
                if(response.isSuccessful()){
                    var sub: java.util.ArrayList<Sector>
                    sub = response.body()!!
                    val adapter: ArrayAdapter<Sector> = ArrayAdapter<Sector>(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        sub
                    )
                    spsector.setAdapter(adapter)
                }

            }

            override fun onFailure(call: Call<java.util.ArrayList<Sector>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun obtenerAlmacenes(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: AlmacenService = retro.create(AlmacenService::class.java)
        val call: Call<java.util.ArrayList<Almacen>> = pro.getListadoAlmacenes("Bearer $token")
        call.enqueue(object : Callback<java.util.ArrayList<Almacen>> {
            override fun onResponse(
                call: Call<java.util.ArrayList<Almacen>>,
                response: Response<java.util.ArrayList<Almacen>>

            ) {
                if (response.isSuccessful()) {
                    var al: java.util.ArrayList<Almacen>
                    al = response.body()!!
                    val adapter = ArrayAdapter(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        al
                    )
                    spalmacen.adapter = adapter

                }
            }
            override fun onFailure(call: Call<java.util.ArrayList<Almacen>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun buscarInventariosFisicos(token: String, sector: String, fecha1: String, fecha2: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: InventarioFisicoService = retro.create(InventarioFisicoService::class.java)
        val call: Call<java.util.ArrayList<InventarioFisico>> = pro.getFiltroInventarioFisicoProd("Bearer $token", sector,fecha1, fecha2)
        call.enqueue(object : Callback<java.util.ArrayList<InventarioFisico>> {
            override fun onResponse(
                call: Call<java.util.ArrayList<InventarioFisico>>,
                response: Response<java.util.ArrayList<InventarioFisico>>

            ) {
                if (response.isSuccessful()) {
                    listadoInventarioFisicoAdapter.notifyDataSetChanged()
                    listadoInventarioFisicoAdapter.submitList(response.body())
                }

            }

            override fun onFailure(
                call: Call<java.util.ArrayList<InventarioFisico>>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }

            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }

        }