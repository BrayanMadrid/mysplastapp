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
import com.example.mysplast.databinding.ListadoTransferenciasBinding
import com.example.mysplast.model.Transferencia
import java.text.SimpleDateFormat

class ListadoTransferenciasAdapter(private val listener: View.OnClickListener):
    ListAdapter<Transferencia, RecyclerView.ViewHolder>(ListadoTransferenciasAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_transferencias,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerTransferencia = getItem(position)
        with(holder as ListadoTransferenciasAdapter.ViewHolder){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerTransferencia.fechatran)
            binding.edtNroTransferencia.text = obtenerTransferencia.nro_TRAN
            binding.edtFechaTransferencia.text = currentDate.toString()
            binding.edtResponsableTransferencia.text = obtenerTransferencia.id_PERSONA.nombres + " " +obtenerTransferencia.id_PERSONA.ape_pat + " " + obtenerTransferencia.id_PERSONA.ape_mat
            binding.edtAlmacenOrTransferencia.text = obtenerTransferencia.id_sector.id_almacen.nom_almacen
            binding.edtSectorOrTransferencia.text = obtenerTransferencia.id_sector.nom_sector
            binding.edtAlmacenDesTransferencia.text = obtenerTransferencia.id_sectordest.id_almacen.nom_almacen
            binding.edtSectorDesTransferencia.text = obtenerTransferencia.id_sectordest.nom_sector
            if(obtenerTransferencia.estado.equals("I")){
                binding.edtEstadoTransferencia.text = "Inventariado"
            } else if(obtenerTransferencia.estado.equals("P")){
                binding.edtEstadoTransferencia.text = "Pendiente"
            } else if(obtenerTransferencia.estado.equals("A")){
                binding.edtEstadoTransferencia.text = "Aprobado"
            } else {
                binding.edtEstadoTransferencia.text = "Anulado"
            }
            val btnVisualizarTransferencia: Button = binding.btnVisualizarTransferencia
            btnVisualizarTransferencia.setOnClickListener {

                val intent: Intent =  Intent(context, ViewOrderCActivity::class.java).apply {
                    putExtra("idtransferencia",  obtenerTransferencia.id_tran)
                }
                context.startActivity(intent)

            }
        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Transferencia>(){
        override fun areItemsTheSame(oldItem: Transferencia, newItem: Transferencia): Boolean  = oldItem.id_tran == newItem.id_tran

        override fun areContentsTheSame(oldItem: Transferencia, newItem: Transferencia): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoTransferenciasBinding.bind(view)


    }
}