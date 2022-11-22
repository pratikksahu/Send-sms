package com.example.sendsms.fragments.viewPager.pager_contacts

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sendsms.R
import com.example.sendsms.data.Contact
import com.example.sendsms.databinding.FragmentPagerContactsBinding
import com.example.sendsms.fragments.FragmentHomeDirections
import com.example.sendsms.fragments.viewPager.pager_contacts.rv_adapter.ContactListAdapter
import com.example.sendsms.viewModelFactory.GenericSavedStateViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class FragmentContacts : Fragment(),View.OnClickListener {

    companion object{
        const val TAG = "FRAGMENTCONTACTS"
    }

    @Inject
    internal lateinit var viewModelContactsFactory: ViewModelContactsFactory

    private val viewModelContacts: ViewModelContacts by activityViewModels{
        GenericSavedStateViewModelFactory(viewModelContactsFactory,this)
    }

    private val parentNavController
        get() = requireParentFragment().findNavController()

    lateinit var binding : FragmentPagerContactsBinding
    lateinit var rvContactAdapter:ContactListAdapter
    lateinit var buttonImport : MaterialButton
    lateinit var buttonAdd : MaterialButton

    private lateinit var storagePermissions: Array<String>
    var jsonAsset = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerContactsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storagePermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        setupViewModel()
        setupButton()
        setupRV()
    }

    private fun setupViewModel(){
        viewModelContacts.contactList.observe(viewLifecycleOwner){
            if (it != null) {
                rvContactAdapter.items = it
            }
        }

        viewModelContacts.jsonUri.observe(viewLifecycleOwner){
            if(it != null){
                viewModelContacts.setContactList(parseJson(it))
            }
        }
    }
    private fun setupButton(){
        buttonImport = binding.buttonImport
        buttonImport.setOnClickListener(this)

        buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener(this)
    }
    private fun setupRV(){
        rvContactAdapter = ContactListAdapter(Contact(arrayListOf())){
            v,pos,item ->
            Log.d(TAG,"Clicked $pos")
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentContactDetails(
                item
            )
            parentNavController.navigate(action)
        }
        binding.rvContactList.layoutManager = LinearLayoutManager(context)
        binding.rvContactList.setHasFixedSize(true)
        binding.rvContactList.itemAnimator = DefaultItemAnimator()
        binding.rvContactList.adapter = rvContactAdapter
    }

    //Function to check storage permissions
    private fun checkStoragePermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) ==
                PackageManager.PERMISSION_GRANTED)
    }
    //Function to request storage permissions
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
        ){
            if(it.containsValue(false)){
            showToast("Requires storage permission")
             }else
            pickFile()

        }


    private fun pickFile(){
        val intent: Intent
        if (Build.MANUFACTURER.equals("samsung", ignoreCase = true)) {
            intent = Intent("com.sec.android.app.myfiles.PICK_DATA")
            intent.putExtra("CONTENT_TYPE", "*/*")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
        } else {
            intent = Intent(Intent.ACTION_GET_CONTENT) // or ACTION_OPEN_DOCUMENT
            intent.type = "application/octet-stream"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }
        jsonActivityResultLauncher.launch(intent)
    }

    private val jsonActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            viewModelContacts.setUri(data?.data)
        }else{
            showToast("Cancelled...")
        }
    }

    //Parse JSON
    private fun parseJson(uri:Uri): Contact? {
        val inputStream = uri.let { context?.contentResolver?.openInputStream(it) }
        val myJson = inputStream?.let { inputStreamToString(it) }
        return Gson().fromJson(myJson, Contact::class.java)
    }
    private fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }


    override fun onClick(v: View?) {
        when(v){
            buttonImport -> {
                if(checkStoragePermission())
                pickFile()
                else
                    requestPermissionLauncher.launch(storagePermissions)
            }
            buttonAdd -> {
                parentNavController.navigate(R.id.action_fragmentHome_to_fragmentAddContactDialog)
            }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}