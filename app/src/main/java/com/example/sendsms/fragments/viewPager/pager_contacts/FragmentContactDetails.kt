package com.example.sendsms.fragments.viewPager.pager_contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sendsms.R
import com.example.sendsms.databinding.FragmentContactDetailBinding

class FragmentContactDetails() : Fragment(),View.OnClickListener {

    private val parentNavController
        get() = requireParentFragment().findNavController()
    lateinit var binding: FragmentContactDetailBinding
    val args: FragmentContactDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(layoutInflater)
        binding.contactImg.setImageResource(R.drawable.ic_avatar)
        binding.item = args.contacts
        binding.buttonSend.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(v: View?) {
        when(v){
            binding.buttonSend -> {
//                prompt dialog
                val action = FragmentContactDetailsDirections.actionFragmentContactDetailsToFragmentDialog(args.contacts)
                parentNavController.navigate(action)
            }
        }
    }
}