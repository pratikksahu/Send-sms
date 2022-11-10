package com.example.meragaon.fragments.viewPager.pager_contacts

import androidx.lifecycle.*
import com.example.meragaon.api.local.SmsSent
import com.example.meragaon.api.model.SMSRequest
import com.example.meragaon.api.model.SMSResponse
import com.example.meragaon.repository.SMSHistoryRepository
import com.example.meragaon.repository.SMSRepository
import com.example.meragaon.viewModelFactory.ViewModelAssistedFactory
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
class ViewModelFragmentDialog (private val handle: SavedStateHandle,
                               private val smsRepository: SMSRepository,
                               private val smsHistoryRepository: SMSHistoryRepository)
    :ViewModel() {

    companion object{
        const val SENDING = 0
        const val SENT = 1
        const val FAILED = 2
    }
    private val _smsResponse = MutableLiveData<SMSResponse?>()
    var smsResponse: LiveData<SMSResponse?> = _smsResponse

    private val _errorLiveData = MutableLiveData<Throwable?>()
    val errorLiveData: LiveData<Throwable?> = _errorLiveData

    private val _progressLiveData = MutableLiveData<Int?>()
    val progressLiveData: LiveData<Int?> = _progressLiveData

    fun sendSMS(smsRequest: SMSRequest){
        _progressLiveData.postValue(0)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                smsRepository.sendSMS(smsRequest).onSuccess {
                    _smsResponse.postValue(it)
                    _progressLiveData.postValue(1)
                }.onFailure {
                    _errorLiveData.postValue(it)
                    _progressLiveData.postValue(2)
                }
            }

        }
    }

    fun saveSMS(smsSent:SmsSent){
        viewModelScope.launch(Dispatchers.IO) {
            smsHistoryRepository.insert(smsSent)
        }
    }

    fun reset(){
        _errorLiveData.postValue(null)
        _progressLiveData.postValue(null)
        _smsResponse.postValue(null)
    }

}

class ViewModelFragmentDialogFactory @Inject constructor(private val smsRepository: SMSRepository,
                                                         private val smsHistoryRepository: SMSHistoryRepository
) : ViewModelAssistedFactory<ViewModelFragmentDialog> {
    override fun create(handle: SavedStateHandle): ViewModelFragmentDialog {
        return ViewModelFragmentDialog(handle, smsRepository,smsHistoryRepository)
    }
}