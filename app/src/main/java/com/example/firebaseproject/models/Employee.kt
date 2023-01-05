package com.example.firebaseproject.models

data class Employee(
    var id : String = "",
    var name : String="",
    var department : String="",
    var salary : Double=0.0,
    var imgUrl : String?=null
)
