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
import com.example.mysplast.databinding.ListadoItemRecetasBinding
import com.example.mysplast.model.ItemRecetaprod

class ItemRecetaAdapter(private val listener: View.OnClickListener):
    ListAdapter<ItemRecetaprod, RecyclerView.ViewHolder>(ItemRecetaAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_recetas,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemReceta = getItem(position)
        with(holder as ItemRecetaAdapter.ViewHolder){

            binding.edtProductoItemReceta.text = itemReceta.id_producto.nombre
            binding.edtUnMedidaItemReceta.text = itemReceta.id_producto.id_unmedida.nom_unmedida
            binding.edtCantidadItemReceta.text = itemReceta.cantidad.toString()

        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<ItemRecetaprod>(){
        override fun areItemsTheSame(oldItem: ItemRecetaprod, newItem: ItemRecetaprod): Boolean  = oldItem.id_itemrecetaprod == newItem.id_itemrecetaprod

        override fun areContentsTheSame(oldItem: ItemRecetaprod, newItem: ItemRecetaprod): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemRecetasBinding.bind(view)


    }
}