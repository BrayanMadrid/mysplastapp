package com.example.mysplast.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.ViewOrderCActivity
import com.example.mysplast.databinding.ListadoIngresosBinding
import com.example.mysplast.model.Ingreso
import java.text.SimpleDateFormat

class ListadoIngresosAdapter(private val listener: View.OnClickListener):
    ListAdapter<Ingreso, RecyclerView.ViewHolder>(ListadoIngresosAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_ingresos,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerIngreso = getItem(position)
        with(holder as ListadoIngresosAdapter.ViewHolder){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerIngreso.fechatran)
            binding.edtNroIngreso.text = obtenerIngreso.nro_TRAN
            binding.edtFechaIngreso.text = currentDate.toString()
            binding.edtResponsableIngreso.text = obtenerIngreso.id_PERSONA.nombres + " " +obtenerIngreso.id_PERSONA.ape_pat + " " + obtenerIngreso.id_PERSONA.ape_mat
            binding.edtAlmacenInIngreso.text = obtenerIngreso.id_sector.id_almacen.nom_almacen
            binding.edtSectorInIngreso.text = obtenerIngreso.id_sector.nom_sector
            if(obtenerIngreso.estado.equals("I")){
                binding.edtEstadoIngreso.text = "Inventariado"
            } else if(obtenerIngreso.estado.equals("P")){
                binding.edtEstadoIngreso.text = "Pendiente"
            } else if(obtenerIngreso.estado.equals("A")){
                binding.edtEstadoIngreso.text = "Aprobado"
            } else {
                binding.edtEstadoIngreso.text = "Anulado"
            }
            val btnVisualizarIngreso: Button = binding.btnVisualizarIngreso
            btnVisualizarIngreso.setOnClickListener {

                val intent: Intent =  Intent(context, ViewOrderCActivity::class.java).apply {
                    putExtra("idingreso",  obtenerIngreso.id_tran)
                }
                context.startActivity(intent)

            }
        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Ingreso>(){
        override fun areItemsTheSame(oldItem: Ingreso, newItem: Ingreso): Boolean  = oldItem.id_tran == newItem.id_tran

        override fun areContentsTheSame(oldItem: Ingreso, newItem: Ingreso): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoIngresosBinding.bind(view)


    }
}