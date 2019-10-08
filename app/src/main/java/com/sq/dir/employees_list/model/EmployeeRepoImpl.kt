package com.sq.dir.employees_list.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.sq.dir.Api
import com.sq.dir.TAG
import com.sq.dir.defaultSchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


open class EmployeeRepoImpl : EmployeeRepo {

    val localDataSource = LocalDataSourceImpl()
    enum class FetchType {
        NORMAL,
        EMPTY,
        MALFORMED
    }

    override fun getEmployees(fetchType: FetchType): Observable<List<Employee>> {
        return when (fetchType) {
            FetchType.NORMAL -> getEmployeesNormal()
            FetchType.EMPTY -> getEmployeesEmpty()
            FetchType.MALFORMED -> getEmployeesMalformed()
        }
    }

    /**
     * Get the list of employees and sort them by name
     */
    @SuppressLint("DefaultLocale")
    @VisibleForTesting
    override fun getEmployeesNormal(): Observable<List<Employee>> {
        if(!localDataSource.getEmployees().isEmpty().blockingGet()){
            Log.d(TAG, "Fetching locally")
            return localDataSource.getEmployees()
        }

        Log.d(TAG, "Fetching from the network")
        val result = Api.getEmployees()
            .map { it.employees }
            .flatMapIterable { list -> list }
            .sorted { first, second ->
                first.full_name.compareTo(second.full_name)
            }
            .map {
                it.employee_type = it.employee_type.replace('_',' ').toLowerCase().capitalize()
                it
            }
            .toList()
            .toObservable()

        result
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(onNext = {
                localDataSource.saveEmployees(it)
                Log.d(TAG, "Saved to local data source")
            },
                onError = {
                    Log.e(TAG, "Couldn't dave to local data source, ${it.message}", it)
                })

        return result
    }

    @VisibleForTesting
    override fun getEmployeesEmpty(): Observable<List<Employee>> {
        return Api.getEmptyEmployees().map { it.employees }
    }

    @VisibleForTesting
    override fun getEmployeesMalformed(): Observable<List<Employee>> {
        return Api.getMalformedEmployees().map { it.employees }
    }
}
