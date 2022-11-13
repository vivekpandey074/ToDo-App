package com.example.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class],version=1,exportSchema = false)  //if want to know history of changes in exportedschema= true else false
abstract class ToDoDatabase:RoomDatabase(){
    abstract  fun toDoAppDao():ToDoAppDao   //You can then use the abstract methods from the AppDatabase to get an instance of the DAO
                                             // . In turn, you can use the methods from the DAO instance to interact with the database:

    companion object
    {
        const val DB_NAME="to_do_db"
        const val TABLE_NAME="ToDo"
    }
}