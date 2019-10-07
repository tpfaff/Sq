package com.sq.dir.employees_list.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sq.dir.*
import com.sq.dir.employee_detail.EmployeeDetailFragment
import com.sq.dir.employees_list.model.Employee
import com.sq.dir.employees_list.model.UiState
import com.sq.dir.employees_list.vm.EmployeesViewModelImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_employees.*
import kotlinx.android.synthetic.main.viewholder_employee.view.*


class EmployeesFragmentImpl : EmployeesFragment, BaseFragment() {
    private val viewModel: EmployeesViewModelImpl by lazy {
        ViewModelProviders.of(this).get(EmployeesViewModelImpl::class.java)
    }

    private val bin = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employees, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        if (restoredFromBackstack.not()) {
            viewModel.viewLoaded()
        }

        subscribeToStreams()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_employees, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.empty -> viewModel.loadEmptyClicked()
            R.id.normal -> viewModel.loadNormalClicked()
            R.id.error -> viewModel.loadMalformedClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeToStreams() {
        viewModel
            .uiState
            .defaultSchedulers()
            .subscribeBy(
                onNext = {
                    Log.d(TAG, it.toString())
                    when (it) {
                        is UiState.Loading -> showLoadingView()
                        is UiState.ListReady -> showListView(it.data)
                        is UiState.ListEmpty -> showEmptyView()
                        is UiState.Error -> showErrorView()
                    }
                },
                onError = {
                    Log.e(TAG, it.message, it)
                }
            )
            .into(bin)
    }

    override fun showListView(data: List<Employee>) {
        progress_bar.visibility = View.GONE
        error_textview.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        recycler_view.adapter = EmployeeAdapter(data)
        
    }

    override fun showEmptyView() {
        progress_bar.visibility = View.GONE
        error_textview.visibility = View.VISIBLE
        error_textview.text = getString(R.string.empty_list)
        recycler_view.visibility = View.GONE
    }

    override fun showLoadingView() {
        progress_bar.visibility = View.VISIBLE
        error_textview.visibility = View.GONE
        recycler_view.visibility = View.GONE
    }

    override fun showErrorView() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE
        recycler_view.visibility = View.GONE
        error_textview.visibility = View.VISIBLE
        error_textview.text = getString(R.string.error_generic)
    }

    override fun onDestroy() {
        super.onDestroy()
        bin.clear()
    }

    inner class EmployeeAdapter(private val data: List<Employee>) :
        RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
            when (viewType) {
                R.layout.viewholder_employee -> return EmployeeViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.viewholder_employee,
                        parent,
                        false
                    )
                )
            }
            throw RuntimeException("Unsupported viewholder type")
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.viewholder_employee
        }

        inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                itemView.setOnClickListener {
                    val args = Bundle()
                    args.putParcelable(EmployeeDetailFragment.EXTRA_EMPLOYEE, data[adapterPosition])
                    fragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            R.id.fragment_container,
                            EmployeeDetailFragment.newInstance(args),
                            EmployeeDetailFragment.TAG
                        )
                        ?.addToBackStack(EmployeeDetailFragment.TAG)
                        ?.commit()
                }
            }
            @SuppressLint("SetTextI18n")
            fun bind(employee: Employee) {
                Glide.with(itemView.context).load(employee.photo_url_small)
                    .into(itemView.employee_imageview)
                itemView.name_textview.text = employee.full_name
                itemView.team_textview.text = employee.team
                itemView.bio_textview.text = employee.biography
                itemView.employee_type.text = employee.employee_type
                itemView.contact_info_textview.text = "${employee.email_address} - ${employee.phone_number}"
                
            }
        }
    }

    companion object {
        fun newInstance(): EmployeesFragmentImpl {
            return EmployeesFragmentImpl()
        }
    }
}