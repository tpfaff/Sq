package com.sq.dir.employees_list.model

import com.sq.dir.AppDatabase
import io.reactivex.Observable

class LocalDataSourceImpl : LocalDataSource{
    override fun getEmployees() : Observable<List<Employee>> {
        return AppDatabase.getInstance().employeeDao().getAll()
    }

    override fun saveEmployees(list: List<Employee>) {
        AppDatabase.getInstance().employeeDao().insertAll(*list.toTypedArray())
    }
}