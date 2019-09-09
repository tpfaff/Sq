package com.sq.dir.employees_list.view

import com.sq.dir.employees_list.model.Employee

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */
interface EmployeesFragment {
    fun showEmptyView()
    fun showLoadingView()
    fun showErrorView()
    fun showListView(data: List<Employee>)
}