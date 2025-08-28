package com.example.apptask.ui.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.R
import com.example.apptask.data.ListItem
import com.example.apptask.data.Tarea
import com.example.apptask.domain.usecase.Categories
import com.example.apptask.ui.list.adapter.ListAdapter
import com.example.apptask.ui.task.adapter.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //categorias
    private val categories = Categories()

    //RecyclerView de las categorias
    private lateinit var rvCategories: RecyclerView

    //RecyclerView de las tares
    private lateinit var rvTask: RecyclerView

    //Boton para agregar tareas
    private lateinit var fabAddTask: FloatingActionButton

    //Conviete la lista de items a una mutableList
    private var itemsMutableList = mutableListOf<ListItem>()

    //Lista de tareas
    private var taskMutableList: MutableList<Tarea> = mutableListOf()

    //Adapter de las categorias
    private lateinit var listAdapter: ListAdapter

    //Adapter de las tareas
    private lateinit var taskAdapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cargarTareas()
        initComponents()
        initUI()
        loadCategories()
        initListeners()
    }

    private fun loadCategories() {
        itemsMutableList.clear()
        itemsMutableList.addAll(categories.getCategories())
        listAdapter.notifyDataSetChanged()
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener {
            agregarTarea()
        }
    }

    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategorias)
        rvTask = findViewById(R.id.rvTareas)
        fabAddTask = findViewById(R.id.fabAdTask)
    }

    private fun initUI() {
        //Se inicializa el adapter de las categorias
        initListAdpater()
        //Se inicializa el adapter de las tareas
        initTaskAdapter()
    }

    private fun initListAdpater() {
        listAdapter = ListAdapter(
            itemsMutableList,
            categoryClick = { position -> categoriaSelecionada(position) },
            onAddClick = { agregarCategoria() })
        rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = listAdapter
    }

    private fun initTaskAdapter() {
        taskAdapter = TaskAdapter(taskMutableList) { position, isChecked ->
            taskMutableList[position].completada = isChecked
            taskAdapter.notifyItemChanged(position)
        }
        rvTask.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTask.adapter = taskAdapter
    }

    private fun agregarCategoria() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAdd: Button = dialog.findViewById(R.id.btnAddList)
        val etName: EditText = dialog.findViewById(R.id.etList)

        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                if (categories.addCategory(name)) {
                    loadCategories() // ✅ Recargar lista completa
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this, "Nombre vacío", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun agregarTarea() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAddList: Button = dialog.findViewById(R.id.btnAddList)
        val etList: EditText = dialog.findViewById(R.id.etList)

        btnAddList.setOnClickListener {
            val nameTask = etList.text.toString()
            if (nameTask.isNotEmpty()) {
                val newId = 1
                val newTask = Tarea(
                    id = newId,
                    titulo = nameTask,
                    descripcion = "joto",
                    completada = true
                )

                actualizarTareasSegunCategoriaSeleccionada()

                // 🔄 Desmarcar todas las demás categorías
                taskMutableList.add(newTask)
                taskAdapter.updateList(taskMutableList)

                dialog.dismiss()
                Log.d("MainActivity", "Nueva tarea agregada: ${newTask.titulo}")
            } else {
                Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }


    private fun categoriaSelecionada(position: Int) {
        // 1. Recorremos toda la lista de categorías
        for (index in itemsMutableList.indices) {
            val listItem = itemsMutableList[index]

            // 2. Verificamos si el item actual es una categoría
            if (listItem is ListItem.Categorias) {
                // 3. Si el índice coincide con el que se seleccionó, lo marcamos como seleccionado
                if (index == position) {
                    listItem.isSelected = true
                } else {
                    // 4. Si no coincide, lo desmarcamos
                    listItem.isSelected = false
                }
            }
        }

        // 5. Notificamos al adapter que la lista de categorías cambió
        listAdapter.notifyDataSetChanged()

        // 6. Creamos una variable para guardar la categoría seleccionada
        var categoriaSeleccionada: ListItem.Categorias? = null

        // 7. Recorremos la lista nuevamente para encontrar la categoría que está marcada como seleccionada
        for (item in itemsMutableList) {
            if (item is ListItem.Categorias && item.isSelected) {
                categoriaSeleccionada = item
                break // Salimos del ciclo una vez que la encontramos
            }
        }

        // 8. Si encontramos una categoría seleccionada, actualizamos la lista de tareas
        if (categoriaSeleccionada != null) {
            // 9. Asignamos sus tareas a la lista mutable
            taskMutableList = categoriaSeleccionada.tareas

            // 10. Actualizamos el adapter de tareas con la nueva lista
            taskAdapter.updateList(taskMutableList)

            // 11. Imprimimos en el log para depurar
            Log.d(
                "MainActivity",
                "Tareas de '${categoriaSeleccionada.nombre}': ${taskMutableList.joinToString { tarea -> tarea.titulo }}"
            )
        } else {
            // 12. Si no hay ninguna categoría seleccionada, limpiamos la lista de tareas
            taskAdapter.updateList(emptyList())
        }
    }

    /**
     * Busca la categoría que esté marcada como seleccionada
     * y actualiza el RecyclerView de tareas con sus tareas.
     * Si no hay ninguna seleccionada, limpia la lista de tareas.
     */
    private fun actualizarTareasSegunCategoriaSeleccionada() {
        // Variable para guardar la categoría seleccionada
        var categoriaSeleccionada: ListItem.Categorias? = null

        // Recorremos la lista para encontrar la categoría seleccionada
        for (item in itemsMutableList) {
            if (item is ListItem.Categorias && item.isSelected) {
                categoriaSeleccionada = item
                break // Salimos del ciclo al encontrarla
            }
        }

        // Si encontramos una categoría seleccionada, actualizamos las tareas
        if (categoriaSeleccionada != null) {
            taskMutableList = categoriaSeleccionada.tareas
            taskAdapter.updateList(taskMutableList)
            Log.d(
                "MainActivity",
                "Tareas de '${categoriaSeleccionada.nombre}': ${taskMutableList.joinToString { it.titulo }}"
            )
        } else {
            // Si no hay ninguna seleccionada, limpiamos la lista
            taskAdapter.updateList(emptyList())
            Log.d("MainActivity", "No hay categoría seleccionada. Lista de tareas vacía.")
        }
    }

    /*Version corta de categoriaSelecionada a usar con el actualizartarea
        private fun categoriaSelecionada(position: Int) {
        // Actualizar selección de categorías
        for (index in itemsMutableList.indices) {
            val listItem = itemsMutableList[index]
            if (listItem is ListItem.Categorias) {
                listItem.isSelected = (index == position)
            }
        }

        // Notificar cambios en la UI
        listAdapter.notifyDataSetChanged()

        // Actualizar tareas según la categoría seleccionada
        actualizarTareasSegunCategoriaSeleccionada()
    }

    V2
    fun seleccionarCategoria(nuevaSeleccion: Categoria) {
    val anteriorSeleccion = categorias.indexOfFirst { it.isSelected }
    val nuevaSeleccionIndex = categorias.indexOf(nuevaSeleccion)

    categorias.forEach { it.isSelected = false }
    nuevaSeleccion.isSelected = true

    notifyItemChanged(anteriorSeleccion)
    notifyItemChanged(nuevaSeleccionIndex)
}

    * */


    private fun cargarTareas() {
        itemsMutableList.forEachIndexed { index, listItem ->
            if (listItem is ListItem.Categorias) {
                if (listItem.isSelected) {
                    taskMutableList = listItem.tareas
                }
            }
        }
        Log.d("MainActivity", "Tareas actuales: ${taskMutableList.joinToString()}")
    }
}