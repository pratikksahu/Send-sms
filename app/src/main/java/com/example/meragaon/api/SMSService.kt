package com.example.meragaon.api

import com.example.meragaon.api.model.SMSResponse
import retrofit2.http.*

interface SMSService {

    @FormUrlEncoded
    @POST("2010-04-01/Accounts/${NetworkModule.username}/Messages.json")
    suspend fun sendSMS(@Field("Body" , encoded = true) body:String,
                    @Field("From" , encoded = true) from:String,
                    @Field("To" , encoded = true) to:String, ) : Result<SMSResponse>
}