package com.example.firebaseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firebaseproject.databinding.FragmentAddEmployeeBinding
import com.example.firebaseproject.models.Employee

class AddEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentAddEmployeeBinding
    private val viewModel:EmployeeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddEmployeeBinding.inflate(inflater,container,false)
        viewModel.errMsg.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.nameInputEt.text.toString()
            val department = binding.DepInputEt.text.toString()
            val salary = binding.salaryInputEt.text.toString()
            val employee=
                Employee(name = name,
            department = department,
            salary = salary.toDouble())
            viewModel.addemployee(employee){
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}