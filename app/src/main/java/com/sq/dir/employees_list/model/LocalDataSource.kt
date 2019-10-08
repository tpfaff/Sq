package com.sq.dir.employees_list.model

import io.reactivex.Observable

interface LocalDataSource{
    fun getEmployees() : Observable<List<Employee>>
    fun saveEmployees(list: List<Employee>)
}