package com.sq.dir

import com.google.gson.Gson
import com.sq.dir.employees_list.model.EmployeesResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class Api {
    private val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"
    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().serializeNulls().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    var service = retrofit.create(SquareService::class.java)


    companion object {
        var INSTANCE: Api? = null
        fun get(): Api {
            if (INSTANCE == null) {
                INSTANCE = Api()
            }
            return INSTANCE as Api
        }

        fun getEmployees(): Observable<EmployeesResponse> {
            return get().service.getEmployees()
        }
        
        fun getMalformedEmployees() : Observable<EmployeesResponse> {
            return get().service.getMalformedEmployees()
        }
        
        fun getEmptyEmployees() : Observable<EmployeesResponse> {
            return get().service.getEmptyEmployees()
        }
    }
}