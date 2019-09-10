package com.sq.dir

import com.sq.dir.employees_list.model.EmployeeRepoImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy




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
        repo.getEmployees(EmployeeRepoImpl.FetchType.MALFORMED)
        verify(repo).getEmployeesMalformed()

    }
}
