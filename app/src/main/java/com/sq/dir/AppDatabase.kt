package com.sq.dir

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sq.dir.employees_list.model.Employee
import com.sq.dir.employees_list.model.EmployeeDao

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object {
        var INSTANCE : AppDatabase?  = null
        fun getInstance(): AppDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    App.get(),
                    AppDatabase::class.java,
                    "database"
                ).build()
            }

            return INSTANCE!!
        }
    }
}

