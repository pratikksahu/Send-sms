package com.example.sendsms.fragments.viewPager.pager_contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sendsms.R
import com.example.sendsms.api.local.SmsSent
import com.example.sendsms.api.model.SMSRequest
import com.example.sendsms.viewModelFactory.GenericSavedStateViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FragmentDialog:DialogFragment() {
    companion object{
        const val TAG = "FragmentDialog"
    }
    private val parentNavController
        get() = requireParentFragment().findNavController()
    val args: FragmentDialogArgs by navArgs()
    lateinit var message:String
    private val _text = mutableStateOf("Send")
    var text: State<String> = _text
    @Inject
    internal lateinit var viewModelFragmentDialogFactory: ViewModelFragmentDialogFactory

    private val viewModelFragmentDialog: ViewModelFragmentDialog by activityViewModels{
        GenericSavedStateViewModelFactory(viewModelFragmentDialogFactory,this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        message = "Hi , Your OTP is ${generateOTP()}"

        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.fillMaxWidth()) {
                    var localmessage by remember { mutableStateOf(TextFieldValue("")) }
                    OutlinedTextField(
                        value = localmessage,
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp,top = 30.dp),
                        label = { Text(
                            text = "Enter your message",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
//                            modifier = Modifier
//                                .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                        ) },
                        onValueChange = {
                            localmessage = it
                            message = it.text
                        }
                    )
                    Row(
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(top = 1.dp)
                            .background(Color.White)
                            .height(48.dp)
                    ) {
                        TextButton(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1F),
                            onClick = {
                                parentNavController.popBackStack()
                            },
                        ) {
                            Text(text = "Cancel", color = Color.Gray)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(Color.Gray)
                        )
                        TextButton(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1F),
                            enabled = (text.value == "Send"),
                            onClick = {
                                viewModelFragmentDialog.sendSMS(SMSRequest(body = message , To = args.contacts?.number.toString()))
                            },
                        ) {
                            Text(text = text.value)

                        }
                    }
                }
            }
        }
    }
    @Composable
    fun OutLineTextFieldSample() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            label = { Text(text = "Enter Your Name") },
            onValueChange = {
                text = it
            }
        )
    }

    private fun setupViewModel(){
        viewModelFragmentDialog.progressLiveData.observe(viewLifecycleOwner) { progress ->
            when(progress){
                ViewModelFragmentDialog.SENDING ->{
                    _text.value = "Sending..."
                }
                ViewModelFragmentDialog.SENT -> {
                    _text.value = "Sent"
                    showToast("Otp sent")
                    saveContact()
                    //Save it in local db
                    viewModelFragmentDialog.reset()
                    parentNavController.navigate(R.id.action_fragmentDialog_to_fragmentHome)
                }
                ViewModelFragmentDialog.FAILED -> {
                    val error = viewModelFragmentDialog.errorLiveData.value
                    if(error?.message == "No internet connection") {
                        _text.value = "Send"
                        viewModelFragmentDialog.reset()
                        showToast(error.message.toString())
                    }
                    else {
                        _text.value = "Failed"
                        showToast("Failed to send, please check the number")
                        saveContact()
                        viewModelFragmentDialog.reset()
                        parentNavController.navigate(R.id.action_fragmentDialog_to_fragmentHome)
                    }
                }
            }
        }
    }

    fun saveContact(){
        val name = args.contacts?.fname + " " + args.contacts?.lname
        val phone = args.contacts?.number
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        val smsSent = SmsSent(name = name,
            message = message,
            phone = phone,
            time = dateInString)
        viewModelFragmentDialog.saveSMS(smsSent)
    }

    private fun showToast(message: String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    private fun generateOTP(): Int {
        return Random.nextInt(899999) + 100000
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}