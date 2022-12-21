package com.starling.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starling.core.penniesToRoundUp
import com.starling.domain.useCases.AddToSavingsGoalUseCase
import com.starling.domain.useCases.CreateSavingsGoalUseCase
import com.starling.domain.useCases.GetTransactionsUseCase
import com.starling.network.entities.Direction
import com.starling.network.entities.SavingsGoalResponse
import com.starling.network.entities.Transaction
import com.starling.network.requests.CurrencyAndAmount
import com.starling.network.requests.TopUpRequest
import com.starling.presentation.PresentationError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import src.starling.R
import java.util.UUID

class TransactionsViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val createSavingsGoalUseCase: CreateSavingsGoalUseCase,
    private val addToSavingsGoalUseCase: AddToSavingsGoalUseCase
) : ViewModel() {

    var accountUid: String? = null

    // This would be a part of the parent BaseActivity we would create.
    // We would have a state OBSERVER instead of a flow.
    private val _transactionState = MutableStateFlow<TransactionsState>(TransactionsState.Loading)
    val transactionState = _transactionState.asSharedFlow()

    private val _failure = MutableSharedFlow<PresentationError>()
    val failure = _failure.asSharedFlow()

    private val _savingsAdded = MutableSharedFlow<Unit>()
    val savingsAdded = _savingsAdded.asSharedFlow()

    private var transactionsList = listOf<TransactionsListItem>()

    fun process(transactionsEvent: TransactionsEvent) {
        when (transactionsEvent) {
            is TransactionsEvent.Initialise -> {
                accountUid = transactionsEvent.accountUid
                getTransactions(
                    transactionsEvent.accountUid,
                    transactionsEvent.categoryUid,
                    transactionsEvent.minTransactionTimestamp,
                    transactionsEvent.maxTransactionTimestamp
                )
            }
            TransactionsEvent.SubmitClicked -> {
                roundUpInitiated()
            }
        }
    }

    private fun roundUpInitiated() {
        accountUid?.let {
            createSavingsGoalUseCase.invoke(
                viewModelScope, CreateSavingsGoalUseCase.Params(
                    it, "Lamborghini", "GBP", CurrencyAndAmount("GBP", 10000), ""
                )
            ) { result ->
                result.result(
                    onSuccess = { savingResponse -> handleSavingsGoalResponse(savingResponse) },
                    onFailure = { exception -> emitFailure(exception.message) }
                )
            }
        } ?: run {
            emitFailure(null)
        }
    }

    private fun handleSavingsGoalResponse(savingsGoalResponse: SavingsGoalResponse?) {
        if(savingsGoalResponse == null || !savingsGoalResponse.success) {
            emitFailure(null)
            return
        }
        addToSavingsGoal(savingsGoalResponse.savingsGoalUid)
    }

    private fun addToSavingsGoal(savingsGoalUid: String) {
        val id = accountUid
        if(id == null) {
            emitFailure(null)
            return
        }
        val params = AddToSavingsGoalUseCase.Params(
            id,
            savingsGoalUid,
            UUID.randomUUID().toString(),
            TopUpRequest(
                CurrencyAndAmount(
                    "GBP",
                    transactionRoundUpTotal(transactionsList)
                )
            )
        )
        addToSavingsGoalUseCase.invoke(viewModelScope, params) { result ->
            result.result(
                onSuccess = {
                    emitSavingsAdded()
                },
                onFailure = {
                    emitFailure(it.message)
                }
            )
        }
    }

    private fun transactionRoundUpTotal(transactionsList: List<TransactionsListItem>): Int {
        return transactionsList.sumOf { it.amount.penniesToRoundUp() }
    }

    private fun getTransactions(transactionUid: String, categoryUid: String, minTransactionTimestamp: String, maxTransactionTimestamp: String) {
        val params = GetTransactionsUseCase.Params(
            transactionUid, categoryUid, minTransactionTimestamp, maxTransactionTimestamp
        )
        getTransactionsUseCase.invoke(viewModelScope, params) { result ->
            result.result(
                onSuccess = { accounts ->
                    accounts?.let {
                        val itemList = createTransactionListItems(it)
                        transactionsList = itemList
                        emitTransactions(itemList)
                    } ?: run {
                        emitFailure(null)
                    }
                },
                onFailure = { emitFailure(it.message) }
            )
        }
    }

    private fun createTransactionListItems(transactions: List<Transaction>): List<TransactionsListItem> {
        val transactionList = mutableListOf<TransactionsListItem>()
        transactions.forEach {
            if (it.direction == Direction.OUT && it.spendingCategory == "PAYMENTS") {
                transactionList.add(
                    TransactionsListItem(
                        name = it.counterPartyName,
                        amount = it.amount.minorUnits,
                        currency = it.amount.currency
                    )
                )
            }
        }
        return transactionList
    }

    private fun emitFailure(failure: String?) = viewModelScope.launch {
        failure?.let { _failure.emit(PresentationError.StringError(it)) } ?: run {
            _failure.emit(PresentationError.IntError(R.string.error))
        }

    }

    private fun emitSavingsAdded() = viewModelScope.launch {
        _savingsAdded.emit(Unit)
    }

    private fun emitTransactions(accounts: List<TransactionsListItem>) {
        _transactionState.value = TransactionsState.Reading(accounts)
    }

    sealed class TransactionsState {
        object Loading : TransactionsState()
        class Reading(val transactions: List<TransactionsListItem>) : TransactionsState()
    }

    sealed class TransactionsEvent {
        class Initialise(
            val accountUid: String,
            val categoryUid: String,
            val minTransactionTimestamp: String,
            val maxTransactionTimestamp: String
        ) : TransactionsEvent()

        object SubmitClicked : TransactionsEvent()
    }

}