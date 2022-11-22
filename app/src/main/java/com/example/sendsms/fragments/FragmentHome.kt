package com.example.sendsms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.sendsms.databinding.FragmentHomeBinding
import com.example.sendsms.fragments.viewPager.pager_adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FragmentHome : Fragment() {

    companion object{
        const val CONTACTS_POS = 0
        const val HISTORY_POS = 1
        const val TAG = "FRAGMENTHOME"
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var tabLayout: TabLayout

    lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
    }

    private fun setupTabLayout(){
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        TabLayoutMediator(tabLayout,viewPager){tab , position ->
            when(position){
                CONTACTS_POS -> {
                    tab.text = "Contacts"
                }
                HISTORY_POS -> {
                    tab.text = "History"
                }
            }
        }.attach()
    }
}