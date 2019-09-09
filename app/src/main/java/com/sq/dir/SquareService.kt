package com.sq.dir

import com.sq.dir.employees_list.model.EmployeesResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface SquareService {
    @GET("employees.json")
    fun getEmployees(): Observable<EmployeesResponse>
    
    @GET("employees_malformed.json")
    fun getMalformedEmployees() : Observable<EmployeesResponse>
    
    @GET("employees_empty.json")
    fun getEmptyEmployees() : Observable<EmployeesResponse>
}