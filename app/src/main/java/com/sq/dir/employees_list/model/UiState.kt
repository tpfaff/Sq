package com.sq.dir.employees_list.model


sealed class UiState {
    class ListReady(val data: List<Employee>) : UiState()
    object ListEmpty : UiState()
    object Error : UiState()
    object Loading : UiState()
}