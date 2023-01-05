package com.example.firebaseproject

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import com.example.firebaseproject.databinding.FragmentEmployeListBinding
import com.example.firebaseproject.databinding.FragmentEmployeeDetaisBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EmployeeDetaisFragment : Fragment() {
    private lateinit var binding :FragmentEmployeeDetaisBinding
    private val viewModel: EmployeeViewModel by activityViewModels()
    private var id = ""
    //val REQUEST_IMAGE_CAPTURE =1
    val resultLauncher=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val bitmap=it.data?.extras?.get("data")as Bitmap
            viewModel.uploadImage(id,bitmap)
        }
    }
    private fun dispatchTakePictureIntent(){
        val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            resultLauncher.launch(takePictureIntent)
            //startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e:ActivityNotFoundException){

        }
    }

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding= FragmentEmployeeDetaisBinding.inflate(inflater,container,false)
         id = arguments?.getString("id")!!
         viewModel.errMsg.observe(viewLifecycleOwner){
             Toast.makeText(requireActivity(), "it", Toast.LENGTH_SHORT).show()
         }
        //viewModel.getEmployeebyId(id).observe(viewLifecycleOwner){

       // }
         if (id != null){
             viewModel.getEmployeebyId(id).observe(viewLifecycleOwner){
                 binding.employee=it

             }

         }
         binding.captureBtn.setOnClickListener {
             dispatchTakePictureIntent()

         }
        return binding.root
    }


}