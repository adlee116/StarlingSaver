package com.starling.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import src.starling.databinding.AccountItemBinding

class AccountAdapter : RecyclerView.Adapter<AccountViewHolder>() {

    private val items = mutableListOf<AccountListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = AccountItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<AccountListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}