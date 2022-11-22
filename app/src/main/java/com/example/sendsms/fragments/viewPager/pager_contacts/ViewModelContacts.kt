package com.example.sendsms.fragments.viewPager.pager_contacts

import android.net.Uri
import androidx.lifecycle.*
import com.example.sendsms.api.model.SMSResponse
import com.example.sendsms.data.Contact
import com.example.sendsms.repository.SMSRepository
import com.example.sendsms.viewModelFactory.ViewModelAssistedFactory
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ViewModelContacts(
    private val handle: SavedStateHandle,
    private val smsRepository: SMSRepository,
): ViewModel() {

    private val _smsResponse = MutableLiveData<SMSResponse>()
    var smsResponse:LiveData<SMSResponse> = _smsResponse

    private val _errorLiveData = MutableLiveData<Throwable?>()
    val errorLiveData: LiveData<Throwable?> = _errorLiveData

    private val _progressLiveData = MutableLiveData<Boolean?>()
    val progressLiveData: LiveData<Boolean?> = _progressLiveData

    private val _contactList =
        MutableLiveData<Contact?>()
    val contactList: LiveData<Contact?> =
        _contactList

    private val _jsonUri =
        MutableLiveData<Uri?>()
    val jsonUri: LiveData<Uri?> =
        _jsonUri

    fun setUri(uri:Uri?){
        _jsonUri.value = uri
    }

    fun setContactList(list:Contact?){
        _contactList.value = list
    }

    fun addContact(contact:Contact.Contacts){
        _contactList.value?.contact?.add(contact)
    }




}

class ViewModelContactsFactory @Inject constructor(private val smsRepository: SMSRepository
) : ViewModelAssistedFactory<ViewModelContacts> {
    override fun create(handle: SavedStateHandle): ViewModelContacts {
        return ViewModelContacts(handle, smsRepository)
    }
}