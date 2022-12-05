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
import com.example.mysplast.ViewEgresoActivity
import com.example.mysplast.databinding.ListadoEgresosBinding
import com.example.mysplast.model.Egreso
import java.text.SimpleDateFormat

class ListadoEgresosAdapter(private val listener: View.OnClickListener):
    ListAdapter<Egreso, RecyclerView.ViewHolder>(ListadoEgresosAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_egresos,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerEgreso = getItem(position)
        with(holder as ListadoEgresosAdapter.ViewHolder){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerEgreso.fechatran)
            binding.edtNroEgreso.text = obtenerEgreso.nro_TRAN
            binding.edtFechaEgreso.text = currentDate.toString()
            binding.edtResponsableEgreso.text = obtenerEgreso.id_PERSONA.nombres + " " +obtenerEgreso.id_PERSONA.ape_pat + " " + obtenerEgreso.id_PERSONA.ape_mat
            binding.edtAlmacenInEgreso.text = obtenerEgreso.id_sector.id_almacen.nom_almacen
            binding.edtSectorInEgreso.text = obtenerEgreso.id_sector.nom_sector
            if(obtenerEgreso.estado.equals("I")){
                binding.edtEstadoEgreso.text = "Inventariado"
            } else if(obtenerEgreso.estado.equals("P")){
                binding.edtEstadoEgreso.text = "Pendiente"
            } else if(obtenerEgreso.estado.equals("A")){
                binding.edtEstadoEgreso.text = "Aprobado"
            } else {
                binding.edtEstadoEgreso.text = "Anulado"
            }
            val btnVisualizarEgreso: Button = binding.btnVisualizarEgreso
            btnVisualizarEgreso.setOnClickListener {

                val intent: Intent =  Intent(context, ViewEgresoActivity::class.java).apply {
                    putExtra("idtransaccion",  obtenerEgreso.id_tran)
                }
                context.startActivity(intent)

            }
        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Egreso>(){
        override fun areItemsTheSame(oldItem: Egreso, newItem: Egreso): Boolean  = oldItem.id_tran == newItem.id_tran

        override fun areContentsTheSame(oldItem: Egreso, newItem: Egreso): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoEgresosBinding.bind(view)


    }
}