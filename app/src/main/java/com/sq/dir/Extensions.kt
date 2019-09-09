package com.sq.dir

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Copied code, this code is copied from other projects of mine
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

    fun Disposable.into(compositeDisposable: CompositeDisposable?) {
        compositeDisposable?.let {
            if (it.isDisposed) {
                throw IllegalStateException(
                    "attempted to add Disposable into CompositeDisposable that is already disposed"
                )
            } else {
                it.add(this)
            }
        }
    }

    fun <T>Observable<T>.defaultSchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
