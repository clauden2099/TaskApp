package com.example.apptask.ui.task.adapter

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.R
import com.example.apptask.data.Tarea

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val cbTask: CheckBox = view.findViewById(R.id.cbTask)


    fun render(task: Tarea, position: Int, onCheckChanged: (Int, Boolean) -> Unit) {
        tvTask.text = task.titulo

        // Evitar que el listener anterior se dispare
        cbTask.setOnCheckedChangeListener(null)
        cbTask.isChecked = task.completada

        // Aplicar estilo tachado si está completada
        tvTask.paintFlags = if (task.completada) {
            tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Escuchar cambios del usuario
        cbTask.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(position, isChecked)
        }
    }

}