package com.example.sendsms.api.model

data class SMSRequest constructor(
    val body:String,
    val To:String,
    val from:String = "+14793701592",
)
