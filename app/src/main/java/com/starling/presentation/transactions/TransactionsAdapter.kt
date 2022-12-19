package com.starling.presentation.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import src.starling.databinding.TransactionItemBinding

class TransactionsAdapter : RecyclerView.Adapter<TransactionsViewHolder>() {

    private val items = mutableListOf<TransactionsListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding = TransactionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<TransactionsListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}