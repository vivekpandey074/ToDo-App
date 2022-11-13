package com.example.todoapp

import androidx.room.*


@Dao
interface ToDoAppDao {

    @Insert
    fun insertTodo(todo:ToDo):Long

    @Query("SELECT * FROM " + ToDoDatabase.TABLE_NAME)
    fun fetchlist():MutableList<ToDo>

    @Update
    fun updatetodo(todo: ToDo)

    @Delete
    fun deleteTodo(todo: ToDo)


}