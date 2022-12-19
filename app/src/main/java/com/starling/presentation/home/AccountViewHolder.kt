package com.starling.presentation.home

import androidx.recyclerview.widget.RecyclerView
import src.starling.databinding.AccountItemBinding

class AccountViewHolder(private val binding: AccountItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(account: AccountListItem) {
        binding.name.text = account.name
        binding.accountButton.setOnClickListener { account.onClick(account.uid, account.defaultCategory) }
    }
}