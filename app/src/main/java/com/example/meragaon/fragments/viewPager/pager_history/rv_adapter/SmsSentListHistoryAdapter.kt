package com.example.meragaon.fragments.viewPager.pager_history.rv_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meragaon.R
import com.example.meragaon.api.local.SmsSent
import com.example.meragaon.databinding.ItemSmsHistoryBinding

class SmsSentListHistoryAdapter(items : List<SmsSent?>
                                , private val itemClick:(view: View, position: Int,item: SmsSent) -> Unit)
    :RecyclerView.Adapter<SmsSentListHistoryAdapter.ViewHolder>() {

    var items = items
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
                R.layout.item_sms_history,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener(holder)
    }

    override fun getItemCount(): Int = items.count()

    inner class ViewHolder(private val binding: ItemSmsHistoryBinding)
        : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        override fun onClick(v: View?) {
            val item = items.get(adapterPosition)
            if (v != null) {
                if (item != null) {
                    itemClick.invoke(v,adapterPosition,item)
                }
            }
        }

        fun bind(position:Int){
            binding.item = items.get(position)
            binding.contactImg.setImageResource(R.drawable.ic_avatar)
            binding.executePendingBindings()
        }
    }
}