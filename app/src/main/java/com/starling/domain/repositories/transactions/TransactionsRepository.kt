package com.starling.domain.repositories.transactions

import com.starling.network.entities.TransactionResponse

interface TransactionsRepository {

    suspend fun getTransactions(accountUid: String, categoryUid: String, minTransactionTimestamp: String, maxTransactionTimestamp: String): TransactionResponse?
}