package com.example.meragaon

import android.app.Notification.Action
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import com.example.meragaon.data.Contact
import com.example.meragaon.fragments.viewPager.pager_contacts.ViewModelContacts
import com.example.meragaon.fragments.viewPager.pager_contacts.ViewModelContactsFactory
import com.example.meragaon.viewModelFactory.GenericSavedStateViewModelFactory
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    internal lateinit var viewModelContactsFactory: ViewModelContactsFactory

    private val viewModelContacts: ViewModelContacts by viewModels{
        GenericSavedStateViewModelFactory(viewModelContactsFactory,this)
    }

    lateinit var jsonAsset:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadJsonFromAsset()
    }

    private fun loadJsonFromAsset(){
        try{
            jsonAsset = applicationContext.assets.open("contact").bufferedReader().use { it.readText() }
            viewModelContacts.setContactList(Gson().fromJson(jsonAsset, Contact::class.java))
        }catch (e:java.lang.Exception){
            showToast("Couldn't load contacts from assets, Please upload manually")
        }
    }

    private fun showToast(message: String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
    }
}