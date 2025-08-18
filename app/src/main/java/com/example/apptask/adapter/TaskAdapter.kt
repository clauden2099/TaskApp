package com.example.apptask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.R
import com.example.apptask.Tarea
import com.example.apptask.TaskViewHolder

class TaskAdapter(private var tasks: List<Tarea>, private val onCheckChanged: (Int, Boolean) -> Unit,) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position], position, onCheckChanged)

    }

    fun updateList(tasks: List<Tarea>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}