package com.sq.dir.employee_detail

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sq.dir.R
import com.sq.dir.employees_list.model.Employee
import kotlinx.android.synthetic.main.fragment_employee_detail.*

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */
class EmployeeDetailFragment : Fragment() {

    lateinit var employee: Employee
    lateinit var imageView: ImageView
    val originalConstraints = ConstraintSet()
    val newConstraints = ConstraintSet()
    var isLargeView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        employee = arguments?.getParcelable(EXTRA_EMPLOYEE)!!
        originalConstraints.clone(requireContext(), R.layout.fragment_employee_detail)
        newConstraints.clone(requireContext(), R.layout.fragment_employee_detail_large)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employee_detail, container, false)
        imageView = view.findViewById(R.id.employee_imageview)
        return view
    }

    override fun onStart() {
        super.onStart()
        Glide.with(requireContext()).load(employee.photo_url_large).into(imageView)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isLargeView) {
                    TransitionManager.beginDelayedTransition(detail_root)
                    originalConstraints.applyTo(detail_root)
                    isLargeView = false
                    this.isEnabled = false
                } else {
                    this.isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }

        imageView
            .setOnClickListener {
                TransitionManager.beginDelayedTransition(detail_root)
                newConstraints.applyTo(detail_root)
                isLargeView = true
                callback.isEnabled = true
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {
        const val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"
        fun newInstance(args: Bundle): EmployeeDetailFragment {
            val fragment = EmployeeDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}