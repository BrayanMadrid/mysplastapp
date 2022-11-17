package com.example.mysplast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoOrdenesprodBinding
import com.example.mysplast.model.Ordenprod
import java.text.SimpleDateFormat

class ListadoOrdenesProdAdapter  (private val listener: OnClickListener):
    ListAdapter<Ordenprod, RecyclerView.ViewHolder>(OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_ordenesprod,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerOrdenprod = getItem(position)
        with(holder as ViewHolder){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerOrdenprod.fecha)
            binding.edtNroOrdenProd.text = obtenerOrdenprod.nro_ordenprod
            binding.edtComentarios.text = obtenerOrdenprod.desc_ordenprod
            binding.edtFecha.text = currentDate.toString()
            binding.edtResponsable.text = obtenerOrdenprod.id_PERSONA.nombres + " " +obtenerOrdenprod.id_PERSONA.ape_pat + " " + obtenerOrdenprod.id_PERSONA.ape_mat
            binding.edtAlmacenIn.text = obtenerOrdenprod.id_sector.id_almacen.nom_almacen
            binding.edtSectorIn.text = obtenerOrdenprod.id_sector.nom_sector
            binding.edtAlmacenSa.text = obtenerOrdenprod.id_sectorinsumos.id_almacen.nom_almacen
            binding.edtSectorSa.text = obtenerOrdenprod.id_sectorinsumos.nom_sector
            if(obtenerOrdenprod.estado.equals("I")){
                binding.edtEstado.text = "Inventariado"
            } else if(obtenerOrdenprod.estado.equals("P")){
                binding.edtEstado.text = "Pendiente"
            } else {
                binding.edtEstado.text = "Anulado"
            }
        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Ordenprod>(){
        override fun areItemsTheSame(oldItem: Ordenprod, newItem: Ordenprod): Boolean  = oldItem.id_ordenprod == newItem.id_ordenprod

        override fun areContentsTheSame(oldItem: Ordenprod, newItem: Ordenprod): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoOrdenesprodBinding.bind(view)


    }
}