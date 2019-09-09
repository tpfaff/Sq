package com.sq.dir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sq.dir.employees_list.view.EmployeesFragmentImpl
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val bin = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    EmployeesFragmentImpl.newInstance(),
                    EmployeesFragmentImpl.TAG
                )
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bin.clear()
    }
}
