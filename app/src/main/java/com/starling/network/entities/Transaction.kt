package com.starling.network.entities

import com.starling.network.requests.CurrencyAndAmount

data class Transaction(
    val feedItemUid: String,
    val counterPartyName: String,
    val amount: CurrencyAndAmount,
    val direction: Direction,
    val spendingCategory: String
)

data class TransactionResponse(val feedItems: List<Transaction>)

enum class Direction {
    IN, OUT
}