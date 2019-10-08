package com.sq.dir.employees_list.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): Observable<List<Employee>>

    @Query("SELECT * FROM employee WHERE uuid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Employee>

    @Insert
    fun insertAll(vararg users: Employee)

    @Delete
    fun delete(user: Employee)
}