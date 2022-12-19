package com.starling.presentation.transactions

import androidx.recyclerview.widget.RecyclerView
import com.starling.core.formatPound
import src.starling.databinding.AccountItemBinding
import src.starling.databinding.TransactionItemBinding

class TransactionsViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(transaction: TransactionsListItem) {
        binding.name.text = transaction.name
        binding.amount.text = transaction.amount.formatPound()
    }
}