package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.google.android.material.badge.BadgeUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class createtodo : AppCompatActivity() {

    lateinit var toDoDatabase: ToDoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createtodo)
        supportActionBar?.hide()

        var isbeingupdated=false
        var previousTodo:ToDo?=null

        if(intent.hasExtra("previoustodo"))
        {
            isbeingupdated=true
            previousTodo=intent.extras?.get("previoustodo") as ToDo

        }


        toDoDatabase= Room.databaseBuilder(applicationContext,ToDoDatabase::class.java,ToDoDatabase.DB_NAME).build()
        //  intializing database object   ....... building database







        val btnsavetext:Button=findViewById(R.id.btnsavetext)
        val todotext:EditText=findViewById(R.id.todotext)

        todotext.setText(previousTodo?.name)

        btnsavetext.setOnClickListener{
            val  enteredtext:String=todotext.text.toString()

            if(isbeingupdated)
            {
                previousTodo?.name=enteredtext
                previousTodo?.let {
                    updateRow(previousTodo )        //let is executed when previoustodo is not null
                }
            }
            else {
                val todo = ToDo()
                todo.name = enteredtext
                insertRow(todo)

            }

        }

    }

    private fun insertRow(todo:ToDo)
    {   doAsync {
        val id= toDoDatabase.toDoAppDao().insertTodo(todo)

        uiThread {

            todo.todoId=id

            val intent= Intent(this@createtodo,MainActivity::class.java )
            startActivity(intent)
            finish()
        }


        }



    }
    private fun updateRow(todo:ToDo)
    {
        doAsync {
            toDoDatabase.toDoAppDao().updatetodo(todo)

            uiThread {
                val intent= Intent(this@createtodo,MainActivity::class.java )
                startActivity(intent)
                finish()
            }
        }


    }

}