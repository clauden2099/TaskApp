package com.example.apptask

sealed class ListItem{
    data class Categorias(val id:Int, val nombre:String, val tareas:MutableList<Tarea>, var isSelected: Boolean = false) : ListItem()
    object AddButton : ListItem()
}

data class Tarea(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean
)