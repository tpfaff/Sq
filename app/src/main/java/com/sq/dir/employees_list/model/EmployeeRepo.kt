package com.sq.dir.employees_list.model

import com.sq.dir.Api
import io.reactivex.Observable


class EmployeeRepo {

    enum class FetchType {
        NORMAL,
        EMPTY,
        ERROR
    }

    fun getEmployees(fetchType: FetchType): Observable<List<Employee>> {
        return when (fetchType) {
            FetchType.NORMAL ->
                Api.getEmployees().map { it.employees }
            FetchType.EMPTY ->
                Api.getEmptyEmployees().map { it.employees }
            FetchType.ERROR ->
                Api.getMalformedEmployees().map { it.employees }
        }
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
