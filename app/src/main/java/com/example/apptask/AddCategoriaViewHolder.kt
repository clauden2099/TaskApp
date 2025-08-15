package com.example.apptask

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class AddCategoriaViewHolder(
    view: View,
    private val oncCLick: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init{
        view.findViewById<Button?>(R.id.btnAddCategoria).setOnClickListener{
            oncCLick()
        }

    }
}