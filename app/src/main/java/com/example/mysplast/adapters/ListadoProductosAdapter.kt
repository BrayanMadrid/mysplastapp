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
            binding.edtCategoria.text = obtenerProducto.id_categoria.nom_categoria
            binding.edtUnMedida.text = obtenerProducto.id_unmedida.nom_unmedida
            binding.edtMarca.text = obtenerProducto.id_marca.nom_marca
            binding.edtCodigo.text = obtenerProducto.codexterno

            if (obtenerProducto.estado=="A"){
                binding.edtEstado.text = "Activo"
            } else {
                binding.edtEstado.text = "Inactivo"
            }

            if (obtenerProducto.flag_produccion=="Y"){
                binding.edtProduccion.text = "Si"
            } else {
                binding.edtProduccion.text = "No"
            }

            if (obtenerProducto.flag_insumo=="Y"){
                binding.edtInsumo.text = "Si"
            } else {
                binding.edtInsumo.text = "No"
            }
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