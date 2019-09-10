package com.sq.dir.employees_list.view

import com.sq.dir.employees_list.model.Employee


interface EmployeesFragment {
    fun showEmptyView()
    fun showLoadingView()
    fun showErrorView()
    fun showListView(data: List<Employee>)
}