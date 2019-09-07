package com.sq.dir

import com.sq.dir.model.EmployeesResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface SquareService {
    @GET("employees.json")
    fun getEmployees(): Observable<EmployeesResponse>
}