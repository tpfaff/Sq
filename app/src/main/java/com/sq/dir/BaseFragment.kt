package com.sq.dir

import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {
    var restoredFromBackstack = false
    override fun onDestroy() {
        super.onDestroy()
        restoredFromBackstack = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        restoredFromBackstack = true
    }
}