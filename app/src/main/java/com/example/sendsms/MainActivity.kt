package com.example.sendsms


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.sendsms.data.Contact
import com.example.sendsms.fragments.viewPager.pager_contacts.ViewModelContacts
import com.example.sendsms.fragments.viewPager.pager_contacts.ViewModelContactsFactory
import com.example.sendsms.viewModelFactory.GenericSavedStateViewModelFactory
import com.example.sendsms.R
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