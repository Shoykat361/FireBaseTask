package com.example.firebaseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firebaseproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val employeeViewModel: EmployeeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        employeeViewModel.authLd.observe(viewLifecycleOwner){
            if (it==EmployeeViewModel.Authentication.Aunthenticated){
                findNavController().popBackStack()
            }

        }
        employeeViewModel.errMsg.observe(viewLifecycleOwner){
            binding.errMsgTv.text=it

        }
        binding.loginBtn.setOnClickListener {
            val email= binding.emailInputEt.text.toString()
            val password=binding.passwordInputEt.text.toString()
            employeeViewModel.login(email,password)
        }

        return binding.root
    }

}