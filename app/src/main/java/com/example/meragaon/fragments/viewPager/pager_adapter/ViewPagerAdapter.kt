package com.example.meragaon.fragments.viewPager.pager_adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.meragaon.fragments.viewPager.pager_contacts.FragmentContacts
import com.example.meragaon.fragments.viewPager.fragments_pager.FragmentHistory

class ViewPagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    companion object{
        const val CONTACTS_POS = 0
        const val HISTORY_POS = 1
    }
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            CONTACTS_POS -> {
                val fragment = FragmentContacts()
                fragment
            }
            HISTORY_POS -> {
                val fragment = FragmentHistory()
                fragment
            }
            else -> {
                val fragment = FragmentContacts()
                fragment
            }
        }
    }
}