package com.example.sendsms.repository

import com.example.sendsms.api.local.SmsSent
import com.example.sendsms.api.local.SmsSentDAO
import javax.inject.Inject

class SMSHistoryRepository @Inject constructor(private val smsSentDAO: SmsSentDAO) {
//    val smsSent = smsSentDAO.allSmsSent

    fun insert(smsSent: SmsSent) {
        return smsSentDAO.insert(smsSent)
    }

    fun update(smsSent: SmsSent) {
        return smsSentDAO.update(smsSent)
    }

    fun delete(smsSent: SmsSent) {
        return smsSentDAO.delete(smsSent)
    }

    fun deleteAll() {
        return smsSentDAO.deleteAll()
    }

    suspend fun getAll(filter:String = "" ,isAsc: Boolean = false):List<SmsSent?>? {
        return smsSentDAO.getAll(filter,isAsc)
    }

}