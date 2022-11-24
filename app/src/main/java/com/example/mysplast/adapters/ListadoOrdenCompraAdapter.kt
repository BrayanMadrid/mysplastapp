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
import com.example.mysplast.databinding.ListadoOrdenescompraBinding
import com.example.mysplast.model.Ordencompra
import java.text.SimpleDateFormat

class ListadoOrdenCompraAdapter(private val listener: View.OnClickListener):
    ListAdapter<Ordencompra, RecyclerView.ViewHolder>(OrdenDiffCallback()) {

    private lateinit var context: Context


    class OrdenDiffCallback: DiffUtil.ItemCallback<Ordencompra>(){
        override fun areItemsTheSame(oldItem: Ordencompra, newItem: Ordencompra): Boolean  = oldItem.id_ordencompra == newItem.id_ordencompra

        override fun areContentsTheSame(oldItem: Ordencompra, newItem: Ordencompra): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoOrdenescompraBinding.bind(view)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_ordenescompra,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerOrdencompra = getItem(position)
        with(holder as ListadoOrdenCompraAdapter.ViewHolder){
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerOrdencompra.fecha)
            binding.edtNroOrdenCompra.text = obtenerOrdencompra.nroordencompra
            binding.edtFechaOrdenCompra.text = currentDate.toString()
            binding.edtResponsableOrdenCompra.text = obtenerOrdencompra.empleado.nombres + " " +obtenerOrdencompra.empleado.ape_pat + " " + obtenerOrdencompra.empleado.ape_mat
            binding.edtAlmacenInOrdenCompra.text = obtenerOrdencompra.sector.id_almacen.nom_almacen
            binding.edtSectorInOrdenCompra.text = obtenerOrdencompra.sector.nom_sector
            if(obtenerOrdencompra.estado.equals("I")){
                binding.edtEstadoOrdenCompra.text = "Inventariado"
            } else if(obtenerOrdencompra.estado.equals("P")){
                binding.edtEstadoOrdenCompra.text = "Pendiente"
            } else if(obtenerOrdencompra.estado.equals("A")){
                binding.edtEstadoOrdenCompra.text = "Aprobado"
            } else {
                binding.edtEstadoOrdenCompra.text = "Anulado"
            }
            val btnVisualizarOrden: Button = binding.btnVisualizarOrdenCompra
            btnVisualizarOrden.setOnClickListener {

                val intent: Intent =  Intent(context, ViewOrderCActivity::class.java).apply {
                    putExtra("idordencompra",  obtenerOrdencompra.id_ordencompra)
                }
                context.startActivity(intent)

            }
        }
    }
}