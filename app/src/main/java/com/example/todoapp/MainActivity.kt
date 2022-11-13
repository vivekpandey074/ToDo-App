package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    lateinit var toDoDatabase:ToDoDatabase
    lateinit var recyclerView:RecyclerView
    lateinit var toDoAdapter:ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setTitle(" ")

        val floatingaddbutton:FloatingActionButton=findViewById(R.id.floatingaddbutton)

        var list:MutableList<ToDo> = mutableListOf()

        toDoDatabase= Room.databaseBuilder(applicationContext,ToDoDatabase::class.java,ToDoDatabase.DB_NAME).build()

        floatingaddbutton.setOnClickListener{
            val intent= Intent(this,createtodo::class.java)
            startActivity(intent)

        }
         recyclerView=findViewById(R.id.recyclerView)

         toDoAdapter=ToDoAdapter(this,list,toDoDatabase)

        recyclerView.adapter=toDoAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

       fetchtodolist()



    }
    private fun fetchtodolist()
    {
        doAsync {
            val list=toDoDatabase.toDoAppDao().fetchlist()

            uiThread {
                toDoAdapter.setlist(list)      //fetching list from database into the list which is in
                                             // adapter

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchitem: MenuItem =menu?.findItem(R.id.searchaction)!!
        val searchView: SearchView = searchitem.actionView as SearchView            //wanting ide to know actionview is searchview

       searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {

               return  true

           }

           override fun onQueryTextChange(newText: String?): Boolean {
              newText?.let{
                  searchTodos(it)
              }
               return true
           }

       })

        return true
    }

    private fun searchTodos(newText:String)
    {
        doAsync {
            val list=toDoDatabase.toDoAppDao().fetchlist()

            uiThread {
                val filterList= filter(list,newText)

                filterList?.let{ listafterfiltering ->
                    toDoAdapter.setlist(listafterfiltering)

                    recyclerView.scrollToPosition(0)
                }
            }
        }


    }

    private fun filter(list:List<ToDo>, newText:String):MutableList<ToDo>{
        val lowercasetext= newText.lowercase()
        val filterlist:MutableList<ToDo> = mutableListOf()

        for(item in list)
        {
            val text:String= item.name?.lowercase()!!
            if(text.contains(lowercasetext))
            {
                filterlist.add(item)
            }
        }

        return  filterlist
    }
}