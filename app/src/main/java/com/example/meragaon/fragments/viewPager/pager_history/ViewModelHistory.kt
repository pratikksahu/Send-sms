package com.example.meragaon.fragments.viewPager.pager_history

import android.provider.Telephony.Sms
import android.util.Log
import androidx.lifecycle.*
import com.example.meragaon.api.local.SmsSent
import com.example.meragaon.api.local.SmsSentDatabase
import com.example.meragaon.data.Contact
import com.example.meragaon.fragments.viewPager.pager_contacts.ViewModelFragmentDialog
import com.example.meragaon.repository.SMSHistoryRepository
import com.example.meragaon.repository.SMSRepository
import com.example.meragaon.viewModelFactory.ViewModelAssistedFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelHistory @Inject constructor(private val handle: SavedStateHandle,
                                           private val smsSentRepository: SMSHistoryRepository):ViewModel()
{

    private val _smsHistory = MutableLiveData<List<SmsSent?>?>()
    val smsHistory :LiveData<List<SmsSent?>?> = _smsHistory

    fun getAll(filter:String = "" , isAsc: Boolean = false){
        viewModelScope.launch {
            _smsHistory.value = smsSentRepository.getAll(filter,isAsc)
            Log.d("CHANGE",smsHistory.value.toString())
        }
    }

}

class ViewModelHistoryFactory @Inject constructor(private val smsSentRepository: SMSHistoryRepository
) : ViewModelAssistedFactory<ViewModelHistory> {
    override fun create(handle: SavedStateHandle): ViewModelHistory {
        return ViewModelHistory(handle, smsSentRepository)
    }
}