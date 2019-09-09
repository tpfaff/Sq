package com.sq.dir.employees_list.model

import androidx.annotation.VisibleForTesting
import io.reactivex.Observable

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */
interface EmployeeRepo{
    fun getEmployees(fetchType: EmployeeRepoImpl.FetchType): Observable<List<Employee>>
    @VisibleForTesting
    fun getEmployeesNormal(): Observable<List<Employee>>

    @VisibleForTesting
    fun getEmployeesEmpty(): Observable<List<Employee>>

    @VisibleForTesting
    fun getEmployeesMalformed(): Observable<List<Employee>>
}