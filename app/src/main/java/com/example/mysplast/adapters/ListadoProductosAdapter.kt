package com.example.mysplast.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoProductosBinding
import com.example.mysplast.model.Producto

class ListadoProductosAdapter (private val listener: OnClickListener):
    ListAdapter<Producto, RecyclerView.ViewHolder>(ProductoDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_productos,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerProducto = getItem(position)

        with(holder as ViewHolder){
            binding.edtNomProducto.text = obtenerProducto.nombre
        }
    }

    class ProductoDiffCallback: DiffUtil.ItemCallback<Producto>(){
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean  = oldItem.id_producto == newItem.id_producto

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoProductosBinding.bind(view)


    }

}