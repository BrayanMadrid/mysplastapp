package com.example.mysplast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoItemInventariofisicoBinding
import com.example.mysplast.model.ItemInventarioFisico

class ItemInventarioFisicoAdapter(private val listener: View.OnClickListener):
    ListAdapter<ItemInventarioFisico, RecyclerView.ViewHolder>(ItemInventarioFisicoAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_inventariofisico,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemInventarioFisico = getItem(position)
        with(holder as ItemInventarioFisicoAdapter.ViewHolder){

            binding.edtProductoInventarioFisico.text = itemInventarioFisico.id_producto.nombre
            binding.edtCantidadInventarioFisico.text = itemInventarioFisico.cantidad.toString()
            binding.edtCantidadSistemaInventarioFisico.text = itemInventarioFisico.cantidadsistema.toString()

        }
    }


    class OrdenDiffCallback: DiffUtil.ItemCallback<ItemInventarioFisico>(){
        override fun areItemsTheSame(oldItem: ItemInventarioFisico, newItem: ItemInventarioFisico): Boolean  = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemInventarioFisico, newItem: ItemInventarioFisico): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemInventariofisicoBinding.bind(view)


    }
}