package com.example.apptask.data

sealed class ListItem{
    data class Categorias(val id:Int, val nombre:String, val tareas:MutableList<Tarea>, var isSelected: Boolean = false) : ListItem()
    object AddButton : ListItem()
}

data class Tarea(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var completada: Boolean
)