package com.example.todoapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import androidx.cardview.widget.CardView

class ToDoAdapter(var context: Context, var todolist: MutableList<ToDo>,var toDoDatabase: ToDoDatabase):RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nametextview: TextView = itemView.findViewById(R.id.titleoftask)
        var editbutton: ImageView = itemView.findViewById(R.id.btnedit)
        var deletebutton: ImageView = itemView.findViewById(R.id.btndelete)
        var cardContainer:CardView=itemView.findViewById(R.id.cvtodo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ToDoViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {

        val todo = todolist[position]
        holder.nametextview.text = todo.name

        holder.editbutton.setOnClickListener {
            val intent = Intent(context, createtodo::class.java)
            intent.putExtra("previoustodo", todo)
            context.startActivity(intent)
        }

        holder.nametextview.setBackgroundColor(Color.parseColor(colorpicker.getcolor()))

        holder.deletebutton.setOnClickListener {
            val alert = AlertDialog.Builder(context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this?")
                .setPositiveButton("Yes") { dialogInterface, i ->
                    deleteItem(todo, position)
                }
                .setNegativeButton("No") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .create()

            alert.show()




        }
    }

        override fun getItemCount(): Int {
            return todolist.size
        }

        fun setlist(list: MutableList<ToDo>) {
            todolist = list
            notifyDataSetChanged()        //telling adapter to refresh its list

        }

        private fun deleteItem(todo: ToDo, position: Int) {
            doAsync {
                toDoDatabase.toDoAppDao().deleteTodo(todo)

                uiThread {
                    todolist.remove(todo)
                    notifyItemRemoved(position)
                }
            }


        }

    }
