package com.example.apptask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.AddCategoriaViewHolder
import com.example.apptask.CategoriasViewHolder
import com.example.apptask.ListItem
import com.example.apptask.R

class ListAdapter(
    private var items: List<ListItem>,
    private val categoryClick: (Int) -> Unit,
    private val onAddClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 1
        private const val VIEW_TYPE_ADD = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val view = inflater.inflate(R.layout.item_categoria, parent, false)
                CategoriasViewHolder(view)
            }

            VIEW_TYPE_ADD -> {
                val view = inflater.inflate(R.layout.item_add_categoria, parent, false)
                AddCategoriaViewHolder(view) {
                    onAddClick()
                }
            }

            else -> throw IllegalArgumentException("TIpo desconocido: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CategoriasViewHolder -> {
                if (item is ListItem.Categorias) {
                    holder.bind2(item, categoryClick) /*{ clickedPosition ->
                        // Deseleccionar el ítem anterior y seleccionar el nuevo
                        items.forEachIndexed { index, listItem ->
                            if (listItem is ListItem.Categorias) {
                                listItem.isSelected = (index == clickedPosition)
                            }
                        }
                        notifyDataSetChanged()  // Actualizar todas las vistas
                        categoryClick(item)
                    }*/
                }
            }

            is AddCategoriaViewHolder -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.Categorias -> VIEW_TYPE_CATEGORY
            ListItem.AddButton -> VIEW_TYPE_ADD
        }
    }

    fun updateList(items: List<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}