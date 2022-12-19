package com.starling.domain.useCases

import com.starling.core.BaseUseCase
import com.starling.core.Result
import com.starling.domain.repositories.transactions.TransactionsRepository
import com.starling.network.entities.Transaction

class GetTransactionsUseCase(
    private val transactionsRepository: TransactionsRepository
) : BaseUseCase<List<Transaction>?, GetTransactionsUseCase.Params>() {

    override suspend fun run(params: Params): Result<List<Transaction>?, Exception> {
        return try {
            val result = transactionsRepository.getTransactions(
                params.accountUid,
                params.categoryUid,
                params.minTransactionTimestamp,
                params.maxTransactionTimestamp
            )
            Result.Success(result?.feedItems)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    data class Params(
        val accountUid: String,
        val categoryUid: String,
        val minTransactionTimestamp: String,
        val maxTransactionTimestamp: String
    )


}