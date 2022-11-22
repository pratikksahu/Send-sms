package com.example.sendsms.fragments.viewPager.pager_history

import android.util.Log
import androidx.lifecycle.*
import com.example.sendsms.api.local.SmsSent
import com.example.sendsms.repository.SMSHistoryRepository
import com.example.sendsms.viewModelFactory.ViewModelAssistedFactory
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