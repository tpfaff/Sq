package com.sq.dir.employees_list.model

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import com.sq.dir.Api
import io.reactivex.Observable


open class EmployeeRepoImpl : EmployeeRepo {

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
        return Api.getEmployees()
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
