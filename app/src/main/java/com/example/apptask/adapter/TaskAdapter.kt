package com.example.apptask.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.Tarea

class TaskAdapter(var task: List<Tarea>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return task.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}