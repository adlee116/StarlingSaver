package com.starling.domain.useCases

import com.starling.core.CodeException
import com.starling.core.Result
import com.starling.domain.repositories.transactions.TransactionsRepository
import com.starling.network.entities.Transaction
import com.starling.network.entities.TransactionResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


internal class GetTransactionsUseCaseTest{

    private val repo: TransactionsRepository = mock()
    private val useCase = GetTransactionsUseCase(repo)
    private val transactionList = mutableListOf<Transaction>()
    private val transactionResponse = TransactionResponse(transactionList)
    private val params = GetTransactionsUseCase.Params("","","","")


    @Test
    fun `Success response`() {
        runBlocking {
            whenever(repo.getTransactions(any(), any(), any(), any())).thenReturn(transactionResponse)
            useCase.enableTesting = true
            useCase.invoke(this, params) { result ->
                assert(result is Result.Success)
                result.result(
                    onSuccess = { assert(it == transactionList) },
                    onFailure = {}
                )
            }
        }
    }

    @Test
    fun `Error - Request failed 401`() = runBlocking {
        val errorResponse = CodeException(401)
        useCase.enableTesting = true
        given(repo.getTransactions(any(),any(),any(),any())).willAnswer { throw errorResponse }
        useCase.invoke(this, params) { result ->
            assert(result is Result.Failure)
            result.result(
                onSuccess = {},
                onFailure = {
                    assert(it == errorResponse)
                }
            )
        }
    }


    @Test
    fun `Error - Request failed 404`() {
        val errorResponse = CodeException(404)

        runBlocking {
            given(repo.getTransactions(any(),any(),any(),any())).willAnswer { throw errorResponse }
            useCase.invoke(this, params) { result ->
                assert(result is Result.Failure)
                result.result(
                    onFailure = {
                        assert(it == errorResponse)
                    }
                )
            }
        }
    }

    @Test
    fun `Error - Exception thrown during request`() {
        val exception = Exception()
        runBlocking {
            given(repo.getTransactions(any(), any(), any(), any())).willAnswer { throw exception }
            useCase.invoke(this, params) { result ->
                assert(result is Result.Failure)
                result.result(
                    onFailure = {
                        assert(it == exception)
                    }
                )
            }
        }
    }

}