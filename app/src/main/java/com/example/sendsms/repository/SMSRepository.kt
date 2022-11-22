package com.example.sendsms.repository

import com.example.sendsms.api.model.SMSRequest
import com.example.sendsms.api.SMSService
import javax.inject.Inject

class SMSRepository @Inject constructor(private val smsService: SMSService) {
//
//    private val _smsResponse = MutableLiveData<SMSResponse>()
//    var smsResponse:LiveData<SMSResponse> = _smsResponse

    suspend fun sendSMS(smsRequest: SMSRequest) = smsService.sendSMS(smsRequest.body,smsRequest.from,smsRequest.To)

}