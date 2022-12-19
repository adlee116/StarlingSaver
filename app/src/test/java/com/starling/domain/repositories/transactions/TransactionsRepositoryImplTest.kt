package com.starling.domain.repositories.transactions

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.starling.network.entities.Direction
import com.starling.network.entities.Transaction
import com.starling.network.entities.TransactionResponse
import com.starling.network.requests.CurrencyAndAmount
import com.starling.network.services.TransactionService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

internal class TransactionsRepositoryImplTest {

    private val service: TransactionService = mock()
    private val repository = TransactionsRepositoryImpl(service)

    @Test
    fun `Successful transaction response`() {
        runBlocking {
            val transaction = Transaction(
                "1",
                "micky",
                CurrencyAndAmount("GBP", 100),
                Direction.OUT,
                "PAYMENT"
            )
            val transactionList = mutableListOf(transaction)
            val transactionResponse = TransactionResponse(transactionList)
            val retroTransactionResponse: Response<TransactionResponse> = Response.success(transactionResponse)
            whenever(service.getTransactions(any(), any(), any(), any())).thenReturn(retroTransactionResponse)
            assert(repository.getTransactions("", "", "", "") == transactionResponse)
        }
    }
}