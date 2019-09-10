package com.sq.dir.employees_list.vm

import com.sq.dir.employees_list.model.EmployeeRepoImpl


interface EmployeesViewModel{
    fun viewLoaded()
    fun loadEmptyClicked()
    fun loadNormalClicked()
    fun requestEmployees(fetchType: EmployeeRepoImpl.FetchType)
    fun loadMalformedClicked()
}