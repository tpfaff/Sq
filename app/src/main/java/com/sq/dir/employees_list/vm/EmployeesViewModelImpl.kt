package com.sq.dir.employees_list.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sq.dir.TAG
import com.sq.dir.defaultSchedulers
import com.sq.dir.employees_list.model.EmployeeRepo
import com.sq.dir.employees_list.model.UiState
import com.sq.dir.into
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject


class EmployeesViewModelImpl : EmployeesViewModel, ViewModel() {

    val bin = CompositeDisposable()
    val uiState = BehaviorSubject.create<UiState>()
    val repo = EmployeeRepo()

    override fun viewLoaded() {
        uiState.onNext(UiState.Loading)
        requestEmployees(EmployeeRepo.FetchType.NORMAL)
    }

    override fun loadEmptyClicked() {
        requestEmployees(EmployeeRepo.FetchType.EMPTY)
    }

    override fun loadNormalClicked() {
        requestEmployees(EmployeeRepo.FetchType.NORMAL)
    }
    
    override fun loadMalformedClicked() {
//        requestEmployees(EmployeeRepo.FetchType.ERROR)
                            uiState.onNext(UiState.Error)
    }

    override fun requestEmployees(fetchType: EmployeeRepo.FetchType) {
        repo.getEmployees(fetchType)
            .defaultSchedulers()
            .subscribeBy(
                onNext = {
                    if (it.isEmpty()) {
                        uiState.onNext(UiState.ListEmpty)
                    } else {
                        uiState.onNext(UiState.ListReady(it))
                    }
                },
                onError = {
                    Log.e(TAG, it.message, it)
                    uiState.onNext(UiState.Error)
                }
            ).into(bin)
    }


    override fun onCleared() {
        super.onCleared()
        bin.clear()
    }
}