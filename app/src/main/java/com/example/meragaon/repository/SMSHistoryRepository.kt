package com.example.meragaon.repository

import android.app.Application
import android.os.AsyncTask

import android.provider.ContactsContract.CommonDataKinds.Note

import androidx.lifecycle.LiveData
import com.example.meragaon.api.local.SmsSent
import com.example.meragaon.api.local.SmsSentDAO
import com.example.meragaon.api.local.SmsSentDatabase
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