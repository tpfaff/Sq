package com.sq.dir.employees_list.vm

import com.sq.dir.employees_list.model.EmployeeRepo

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */
interface EmployeesViewModel{
    fun viewLoaded()
    fun loadEmptyClicked()
    fun loadNormalClicked()
    fun requestEmployees(fetchType: EmployeeRepo.FetchType)
    fun loadMalformedClicked()
}