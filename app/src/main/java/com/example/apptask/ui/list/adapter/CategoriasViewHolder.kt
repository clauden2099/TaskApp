package com.example.apptask.ui.list.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.R
import com.example.apptask.data.ListItem

class CategoriasViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val tvCategoriaNombre : TextView = view.findViewById(R.id.tvCategoriaNombre)
    private val tabIndicator: View = view.findViewById(R.id.tabIndicator)

    fun bind2(item: ListItem.Categorias, onClick: (Int) -> Unit) {
        if (item.isSelected){
            tabIndicator.visibility = View.VISIBLE
        } else {
            tabIndicator.visibility = View.GONE
        }
        tvCategoriaNombre.text = item.nombre

        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onClick(position)
            }
        }
    }
}