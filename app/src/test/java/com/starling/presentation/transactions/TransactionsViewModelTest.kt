package com.starling.presentation.transactions

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.starling.core.TestCoroutineRule
import com.starling.core.mockSuccess
import com.starling.domain.useCases.AddToSavingsGoalUseCase
import com.starling.domain.useCases.CreateSavingsGoalUseCase
import com.starling.domain.useCases.GetTransactionsUseCase
import com.starling.network.entities.Direction
import com.starling.network.entities.Transaction
import com.starling.network.requests.CurrencyAndAmount
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
internal class TransactionsViewModelTest {
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val getTransactionsUseCase: GetTransactionsUseCase = mock()
    private val createSavingsGoalUseCase: CreateSavingsGoalUseCase = mock()
    private val addToSavingsGoalUseCase: AddToSavingsGoalUseCase = mock()

    private val viewModel = TransactionsViewModel(getTransactionsUseCase, createSavingsGoalUseCase, addToSavingsGoalUseCase)

    @Test
    fun `Initialise will call get transactions use case`() {
        viewModel.process(TransactionsViewModel.TransactionsEvent.Initialise("", "", "", ""))
        Mockito.verify(getTransactionsUseCase, Mockito.times(1)).invoke(
            any(),
            any(),
            any()
        )
    }

    @Test
    fun `Successful use case will update state with new list items`() {
        runBlockingTest {
            val transaction = Transaction(
                "feed", "name", CurrencyAndAmount(
                    "GBP", 100
                ), Direction.OUT, "PAYMENTS"
            )
            val list = mutableListOf(transaction)
            getTransactionsUseCase.mockSuccess(list)

            val launch = launch {
                viewModel.homeState.collect {
                    assert(it is TransactionsViewModel.TransactionsState.Reading)
                    val newTransaction = (it as TransactionsViewModel.TransactionsState.Reading).transactions[0]
                    assert(newTransaction.name == transaction.counterPartyName)
                    assert(newTransaction.currency == transaction.amount.currency)
                    assert(newTransaction.amount == transaction.amount.minorUnits)
                }
            }
            viewModel.process(TransactionsViewModel.TransactionsEvent.Initialise("", "", "", ""))
            launch.start()
            launch.cancel()
        }

    }

    @Test
    fun `Payments of IN direction are not added`() {
        runBlockingTest {
            val transaction = Transaction(
                "feed", "name", CurrencyAndAmount(
                    "GBP", 100
                ), Direction.IN, "PAYMENTS"
            )
            val list = mutableListOf(transaction)
            getTransactionsUseCase.mockSuccess(list)

            val launch = launch {
                viewModel.homeState.collect {
                    assert(it is TransactionsViewModel.TransactionsState.Reading)
                    val newTransactionList = (it as TransactionsViewModel.TransactionsState.Reading).transactions
                    assert(newTransactionList.isEmpty())
                }
            }
            viewModel.process(TransactionsViewModel.TransactionsEvent.Initialise("", "", "", ""))
            launch.start()
            launch.cancel()
        }
    }

    @Test
    fun `Non payments are not added`() {
        runBlockingTest {
            val transaction = Transaction(
                "feed", "name", CurrencyAndAmount(
                    "GBP", 100
                ), Direction.OUT, "INTERNAL"
            )
            val list = mutableListOf(transaction)
            getTransactionsUseCase.mockSuccess(list)

            val launch = launch {
                viewModel.homeState.collect {
                    assert(it is TransactionsViewModel.TransactionsState.Reading)
                    val newTransactionList = (it as TransactionsViewModel.TransactionsState.Reading).transactions
                    assert(newTransactionList.isEmpty())
                }
            }
            viewModel.process(TransactionsViewModel.TransactionsEvent.Initialise("", "", "", ""))
            launch.start()
            launch.cancel()
        }
    }

}