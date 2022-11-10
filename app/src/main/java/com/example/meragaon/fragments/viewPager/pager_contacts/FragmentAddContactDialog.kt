package com.example.meragaon.fragments.viewPager.pager_contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.meragaon.data.Contact
import com.example.meragaon.databinding.FragmentDialogAddContactBinding
import com.example.meragaon.viewModelFactory.GenericSavedStateViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAddContactDialog: DialogFragment(),View.OnClickListener{

    @Inject
    internal lateinit var viewModelContactsFactory: ViewModelContactsFactory

    private val viewModelContacts: ViewModelContacts by activityViewModels{
        GenericSavedStateViewModelFactory(viewModelContactsFactory,this)
    }

    private val parentNavController
        get() = requireParentFragment().findNavController()

    lateinit var binding:FragmentDialogAddContactBinding
    var fname = ""
    var lname = ""
    var number = ""

    lateinit var fnameET:EditText
    lateinit var lnameET:EditText
    lateinit var numberET:EditText
    lateinit var codeTV:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddContactBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSave.setOnClickListener(this)
        codeTV = binding.codeTV
        fnameET = binding.fnameET
        lnameET = binding.lnameET
        numberET = binding.numberET
        fnameET.addTextChangedListener {
           fname = it.toString()
        }
        lnameET.addTextChangedListener {
            lname = it.toString()
        }
        numberET.addTextChangedListener {
            number = codeTV.text.toString() + it.toString()
        }
        super.onViewCreated(view, savedInstanceState)
    }
    private fun numberValidator():Boolean{
        val regex = Regex("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$")
        if(regex.matches(number))
            return true
        return false
    }

    private fun validate():Boolean{
        if(fname.isBlank()){
            showToast("First name required")
            return false
        }
        if(lname.isBlank()){
            showToast("Last name required")
            return false
        }
        if(number.isBlank()){
            showToast("Phone number required")
            return false
        }
            return true
    }

    override fun onClick(v: View?) {
        when(v){
            binding.buttonSave -> {
                if(validate()){
                    if(numberValidator()){
                        val obj = Contact.Contacts(fname,lname,number)
                        viewModelContacts.addContact(obj)
                        showToast("Contact added")
                        parentNavController.popBackStack()
                    }else{
                        showToast("Invalid number")
                    }
                }
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}