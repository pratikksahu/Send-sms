package com.example.sendsms.fragments.viewPager.fragments_pager

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sendsms.R
import com.example.sendsms.databinding.FragmentPagerHistoryBinding
import com.example.sendsms.fragments.viewPager.pager_contacts.FragmentContacts
import com.example.sendsms.fragments.viewPager.pager_history.ViewModelHistory
import com.example.sendsms.fragments.viewPager.pager_history.ViewModelHistoryFactory
import com.example.sendsms.fragments.viewPager.pager_history.rv_adapter.SmsSentListHistoryAdapter
import com.example.sendsms.viewModelFactory.GenericSavedStateViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHistory: Fragment(){
    companion object{
        const val TAG = "FragmentHistory"
    }

    @Inject
    internal lateinit var viewModelHistoryFactory: ViewModelHistoryFactory

    private val viewModelHistory: ViewModelHistory by activityViewModels{
        GenericSavedStateViewModelFactory(viewModelHistoryFactory,this)
    }

    lateinit var binding: FragmentPagerHistoryBinding
    lateinit var rvSmsSentAdapter:SmsSentListHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerHistoryBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            lateinit var rootView: LinearLayout
            lateinit var editTextFilter:EditText
            lateinit var sortButton:ImageButton
            var search = ""
            var isAsc = false
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_history_menu,menu)
                rootView = menu.findItem(R.id.history_actionbar).actionView as LinearLayout
                editTextFilter = rootView.findViewById(R.id.editTextTextPersonName)
                editTextFilter.addTextChangedListener {
                    search = it.toString()
                    viewModelHistory.getAll(filter = search , isAsc = isAsc)
                }
                sortButton = rootView.findViewById(R.id.button_sort)
                sortButton.setOnClickListener{
                    isAsc = !isAsc
                    if(isAsc)
                        sortButton.rotationX = 180f
                    else
                        sortButton.rotationX = 0f

                    viewModelHistory.getAll(search,isAsc)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
        setupRV()
        setupViewModel()
    }


    private fun setupRV(){
        rvSmsSentAdapter = SmsSentListHistoryAdapter(emptyList()){
                v,pos,item ->
            Log.d(FragmentContacts.TAG,"Clicked $pos")
        }
        binding.rvSmsHistory.layoutManager = LinearLayoutManager(context)
        binding.rvSmsHistory.setHasFixedSize(true)
        binding.rvSmsHistory.itemAnimator = DefaultItemAnimator()
        binding.rvSmsHistory.adapter = rvSmsSentAdapter
    }
    private fun setupViewModel(){

        viewModelHistory.smsHistory.observe(viewLifecycleOwner){
            if(it?.isNotEmpty() == true){
                rvSmsSentAdapter.items = it
                binding.rvSmsHistory.visibility = View.VISIBLE
                binding.emptyTV.visibility = View.GONE
            }
            else {
                rvSmsSentAdapter.items = emptyList()
                binding.rvSmsHistory.visibility = View.GONE
                binding.emptyTV.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModelHistory.getAll()
    }

}