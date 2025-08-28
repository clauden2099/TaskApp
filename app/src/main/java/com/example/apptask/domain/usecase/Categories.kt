package com.example.apptask.domain.usecase

import com.example.apptask.data.ListItem
import com.example.apptask.data.RepositoryCategory

class Categories() {
    private val repostoryCategory = RepositoryCategory()

    fun getCategories(): List<ListItem> {
        return repostoryCategory.getCategory()
    }

    fun addCategory(categoryName: String): Boolean {
        if (categoryName.isEmpty()) return false

        val newId = (repostoryCategory.getCategory().filterIsInstance<ListItem.Categorias>().maxOfOrNull { it.id } ?: 0) + 1

        val nuevaCategoria = ListItem.Categorias(
            id = newId,
            nombre = categoryName,
            tareas = mutableListOf(),
            isSelected = true
        )

        repostoryCategory.addCategory(nuevaCategoria)
        return true
    }



}