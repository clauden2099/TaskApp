package com.example.apptask.data

import kotlin.collections.forEach

class RepositoryCategory {

    // Trabajamos directamente con la lista compartida
    fun getCategory(): List<ListItem> {
        return ListItemProvider.items
    }

    fun addCategory(nuevaCategoria: ListItem.Categorias) {
        // 🔄 Desmarcar todas las demás categorías
        ListItemProvider.items.filterIsInstance<ListItem.Categorias>()
            .forEach { it.isSelected = false }

        // 🔄 Insertar la nueva categoría antes del botón de agregar
        val insertIndex = ListItemProvider.items.size - 1
        ListItemProvider.items.add(insertIndex, nuevaCategoria)
    }
}