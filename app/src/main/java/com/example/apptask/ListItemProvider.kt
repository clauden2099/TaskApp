package com.example.apptask


class ListItemProvider {
    companion object {
        val items = listOf<ListItem>(
            ListItem.Categorias(
                id = 1,
                nombre = "Personal",
                tareas = mutableListOf(
                    Tarea(1, "Ir al gimnasio", "Entrenamiento de fuerza", false),
                    Tarea(2, "Leer un libro", "Terminar capítulo 5", true),
                    Tarea(3, "Llamar a mamá", "Charla semanal", false)
                ),
                isSelected = true
            ),
            ListItem.Categorias(
                id = 2,
                nombre = "Trabajo",
                tareas = mutableListOf(
                    Tarea(4, "Enviar reporte", "Reporte mensual de ventas", true),
                    Tarea(5, "Revisar correos", "Responder al cliente", false),
                    Tarea(6, "Planificar reunión", "Agenda para el lunes", false)
                )
            ),
            ListItem.Categorias(
                id = 3,
                nombre = "Estudios",
                tareas = mutableListOf(
                    Tarea(7, "Practicar Kotlin", "RecyclerView y Room", false),
                    Tarea(8, "Ver tutorial", "Curso de arquitectura MVVM", true)
                )
            ),
            ListItem.AddButton
        )
    }
}