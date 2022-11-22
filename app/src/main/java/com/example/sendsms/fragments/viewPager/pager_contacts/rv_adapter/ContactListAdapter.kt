package com.example.sendsms.fragments.viewPager.pager_contacts.rv_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sendsms.R
import com.example.sendsms.data.Contact
import com.example.sendsms.databinding.ItemContactBinding

class ContactListAdapter(items : Contact
                         ,private val itemClick:(view: View, position: Int,item: Contact.Contacts) -> Unit)
    :RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    var items = items
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
                R.layout.item_contact,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener(holder)
    }

    override fun getItemCount(): Int = items.contact!!.count()

    inner class ViewHolder(private val binding: ItemContactBinding)
        : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        override fun onClick(v: View?) {
            val item = items.contact!![adapterPosition]
            if (v != null) {
                itemClick.invoke(v,adapterPosition,item)
            }
        }

        fun bind(position:Int){
            binding.item = items.contact!![position]
            binding.contactImg.setImageResource(R.drawable.ic_avatar)
            binding.executePendingBindings()
        }
    }
}