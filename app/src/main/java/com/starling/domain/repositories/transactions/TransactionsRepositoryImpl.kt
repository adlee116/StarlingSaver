package com.starling.domain.repositories.transactions

import com.starling.core.CodeException
import com.starling.network.entities.TransactionResponse
import com.starling.network.services.TransactionService

class TransactionsRepositoryImpl(private val transactionService: TransactionService) : TransactionsRepository {

    override suspend fun getTransactions(
        accountUid: String,
        categoryUid: String,
        minTransactionTimestamp: String,
        maxTransactionTimestamp: String
    ): TransactionResponse? {
        val transactionResponse = transactionService.getTransactions(
            accountUid,
            categoryUid,
            minTransactionTimestamp,
            maxTransactionTimestamp
        )
        when (transactionResponse.isSuccessful) {
            true -> return transactionResponse.body()
            false -> throw CodeException(transactionResponse.code())
        }
    }

}