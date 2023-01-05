package com.example.firebaseproject.adapter

import android.media.browse.MediaBrowser
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseproject.databinding.EmployeeRowBinding
import com.example.firebaseproject.models.Employee

class EmployeeAdapter(val callback: (String) ->Unit):
    ListAdapter<Employee,EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffutil()) {
    class EmployeeViewHolder(val binding:EmployeeRowBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(employee: Employee){
            binding.employee=employee
        }
    }
    class EmployeeDiffutil :DiffUtil.ItemCallback<Employee>(){
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
           return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =EmployeeRowBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee= getItem(position)
        holder.bind(employee)
        holder.binding.empRow.setOnClickListener {
            callback(employee.id)
        }
    }
}