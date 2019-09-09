package com.sq.dir

import com.sq.dir.employees_list.model.Employee
import com.sq.dir.employees_list.model.EmployeeRepoImpl
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.Mockito.`when` as whenever


/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */

class EmployeeRepoImplTest {
    @Spy
    lateinit var repo: EmployeeRepoImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmptyTypeMatchesNetworkRequest() {
        repo.getEmployees(EmployeeRepoImpl.FetchType.EMPTY)
        verify(repo).getEmployeesEmpty()
    }

    @Test
    fun testNormalTypeMatchesNetworkRequest() {
        repo.getEmployees(EmployeeRepoImpl.FetchType.NORMAL)
        verify(repo).getEmployeesNormal()
    }

    @Test
    fun testMalformedTypeMatchesNetworkRequest() {
        repo.getEmployees(EmployeeRepoImpl.FetchType.ERROR)
        verify(repo).getEmployeesMalformed()

    }
}
