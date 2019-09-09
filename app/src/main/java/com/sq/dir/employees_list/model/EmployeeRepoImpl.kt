package com.sq.dir.employees_list.model

import androidx.annotation.VisibleForTesting
import com.sq.dir.Api
import io.reactivex.Observable


open class EmployeeRepoImpl : EmployeeRepo {

    enum class FetchType {
        NORMAL,
        EMPTY,
        ERROR
    }

    override fun getEmployees(fetchType: FetchType): Observable<List<Employee>> {
        return when (fetchType) {
            FetchType.NORMAL -> getEmployeesNormal()
            FetchType.EMPTY -> getEmployeesEmpty()
            FetchType.ERROR -> getEmployeesMalformed()
        }
    }

    /**
     * Get the list of employees and sort them by name
     */
    @VisibleForTesting
    override fun getEmployeesNormal(): Observable<List<Employee>> {
        return Api.getEmployees()
            .map { it.employees }
            .flatMapIterable { list ->
                list
            }.sorted { first, second ->
                first.full_name.compareTo(second.full_name)
            }.toList()
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


//    /**
//     * Most json parsers won't thrown an exception if a field is missing
//     * 
//     */
//    fun checkRequiredFields(employee: Employee): Boolean {
//        return employee.uuid.isNotEmpty() 
//                && employee.team.isNotEmpty()
//                && employee.full_name.isNotEmpty()
//                && employee.email_address.isNotEmpty()
//                && employee.employee_type.isNotEmpty()
//    }
