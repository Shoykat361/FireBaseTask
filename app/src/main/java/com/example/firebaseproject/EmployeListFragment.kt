package com.example.firebaseproject

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseproject.adapter.EmployeeAdapter
import com.example.firebaseproject.databinding.FragmentEmployeListBinding
import com.example.firebaseproject.databinding.FragmentLoginBinding


class EmployeListFragment : Fragment() {
   private lateinit var binding: FragmentEmployeListBinding
   private val employeeViewModel : EmployeeViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_logout ->{
                employeeViewModel.logOut()

            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEmployeListBinding.inflate(inflater,container,false)
        employeeViewModel.authLd.observe(viewLifecycleOwner){
            if (it==EmployeeViewModel.Authentication.UnAunthenticated){
                findNavController().navigate(R.id.action_employeListFragment_to_loginFragment)
            }
        }
        val adapter=EmployeeAdapter{
            val bundle = bundleOf("id" to it)
            findNavController().navigate(R.id.action_employeListFragment_to_employeeDetaisFragment,bundle)

        }
        binding.employeeRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.employeeRv.adapter=adapter
        employeeViewModel.getAllemployee().observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.empmsgTV.visibility=View.VISIBLE
            }else{
                binding.empmsgTV.visibility=View.GONE

            }
            adapter.submitList(it)

        }
        binding.fabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_employeListFragment_to_addEmployeeFragment)
        }

        return binding.root
    }

}