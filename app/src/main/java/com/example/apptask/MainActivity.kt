package com.example.apptask

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.adapter.ListAdapter
import com.example.apptask.adapter.TaskAdapter

class MainActivity : AppCompatActivity() {

    //RecyclerView de las categorias
    private lateinit var rvCategorias : RecyclerView
    //Conviete la lista de items a una mutableList
    private var itemsMutableList : MutableList<ListItem> = ListItemProvider.items.toMutableList()
    //Lista de tareas
    private var taskMutableList: MutableList<Tarea> = mutableListOf()
    //Adapter de las categorias
    private lateinit var listAdapter: ListAdapter
    //Adapter de las tareas
    private lateinit var taskAdapter : TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initUI()
    }

    private fun initComponents() {
        rvCategorias = findViewById(R.id.rvCategorias)
    }

    private fun initUI() {
        listAdapter = ListAdapter(
            itemsMutableList,
            categoryClick = { position -> categoriaSelecionada(position)},
            onAddClick = { agregarCategoria() })
        rvCategorias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategorias.adapter = listAdapter
    }


    private fun agregarCategoria() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAddList : Button = dialog.findViewById(R.id.btnAddList)
        val etList : EditText = dialog.findViewById(R.id.etList)
        btnAddList.setOnClickListener{
            val nameList = etList.text.toString()
            if (nameList.isNotEmpty()){
                // Crear un ID único simple (por ejemplo, max id + 1)
                val newId = (itemsMutableList.filterIsInstance<ListItem.Categorias>().maxOfOrNull { it.id } ?: 0) + 1
                val newList = ListItem.Categorias(
                    id = newId,
                    nombre = nameList,
                    tareas = mutableListOf(),
                    isSelected = false
                )
                itemsMutableList.add(itemsMutableList.size-1,newList)
                // Notificar al adapter
                listAdapter.notifyItemInserted(itemsMutableList.size-1)

                dialog.dismiss()
                Log.d("MainActivity", "Items actuales: ${itemsMutableList.size}")

            } else {
                Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }


    private fun categoriaSelecionada(position:Int){
        val item = itemsMutableList[position]
        when(item){
            is ListItem.Categorias -> {
                item.isSelected  = !item.isSelected
                itemsMutableList.forEachIndexed{ index, listItem ->
                    if (listItem is ListItem.Categorias) {
                        listItem.isSelected = (index == position)
                    }
                }
            }
            is ListItem.AddButton -> {
                //No hacer nada jaja lol
            }
        }
        listAdapter.notifyDataSetChanged()
    }

    private fun mostrarTareas(){
        itemsMutableList.forEachIndexed{ index , listItem ->
            if(listItem is ListItem.Categorias){
                if (listItem.isSelected){
                    taskMutableList = listItem.tareas
                }
            }
        }
    }



}