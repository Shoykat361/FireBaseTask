package com.example.firebaseproject

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseproject.models.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.security.auth.callback.Callback

class EmployeeViewModel : ViewModel() {
     val collectionEmployee = "Employees"
    private val db = FirebaseFirestore.getInstance()
    enum class Authentication {
        Aunthenticated , UnAunthenticated

    }
    val authLd : MutableLiveData<Authentication> = MutableLiveData()
    val errMsg : MutableLiveData<String> = MutableLiveData()
    val firebaseAuth : FirebaseAuth= FirebaseAuth.getInstance()
    val user : FirebaseUser?=firebaseAuth.currentUser
    init {
        if (user != null){
            authLd.value=Authentication.Aunthenticated
        }
        else{
            authLd.value=Authentication.UnAunthenticated
        }
    }
    fun login(email : String ,password : String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    authLd.value=Authentication.Aunthenticated
                }
            }
            .addOnFailureListener {
                errMsg.value=it.localizedMessage

            }

    }
    fun logOut(){
        if (user != null){
            firebaseAuth.signOut()
            authLd.value=Authentication.UnAunthenticated
        }
    }
    fun addemployee (employee: Employee, callback :()  -> Unit){
        val docref = db.collection(collectionEmployee).document()
        employee.id=docref.id
        docref.set(employee)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener {
                errMsg.value="Please Try Again"
            }

    }
    fun getAllemployee():LiveData<List<Employee>> {
        val empListLD:MutableLiveData<List<Employee>> = MutableLiveData()
        db.collection(collectionEmployee).addSnapshotListener { value, error ->
            if (error != null){
                errMsg.value=error.localizedMessage
                return@addSnapshotListener
            }
            val tempList = mutableListOf<Employee>()
            for (doc in value!!.documents){
                tempList.add(doc.toObject(Employee::class.java)!!)
            }
            empListLD.value=tempList
        }
        return empListLD
    }

    fun getEmployeebyId(id :String):LiveData<Employee> {
        val empLD:MutableLiveData<Employee> = MutableLiveData()
        db.collection(collectionEmployee)
            .document(id)
            .addSnapshotListener { value, error ->
            if (error != null){
                errMsg.value=error.localizedMessage
                return@addSnapshotListener
            }

            empLD.value=value!!.toObject(Employee::class.java)
        }
        return empLD
    }
    fun isEmailVerified(): Boolean=user!!.isEmailVerified

    fun uploadImage(id: String, bitmap: Bitmap) {
        val photoRef = FirebaseStorage.getInstance().reference
            .child("img/${System.currentTimeMillis()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val data :ByteArray = baos.toByteArray()
        val uploadTask=photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    errMsg.value=it.localizedMessage
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                updateEmployeeImg(downloadUri,id)
            } else {
                // Handle failures
                // ...
            }
        }

    }

    private fun updateEmployeeImg(downloadUri: String, id: String) {
        val docRef=db.collection(collectionEmployee).document(id)
        docRef.update("imgUrl",downloadUri)
            .addOnFailureListener {
                errMsg.value=it.localizedMessage
            }
            .addOnSuccessListener {

            }

    }
}