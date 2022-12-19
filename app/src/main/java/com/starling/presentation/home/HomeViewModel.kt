package com.starling.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starling.domain.useCases.GetAccountsUseCase
import com.starling.network.entities.Account
import com.starling.presentation.PresentationError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import src.starling.R

class HomeViewModel(private val accountUseCase: GetAccountsUseCase) : ViewModel() {

    // This would be a part of the parent BaseActivity we would create.
    // We would have a state OBSERVER instead of a flow.
    private val _homeState = MutableSharedFlow<HomeState>()
    val homeState = _homeState.asSharedFlow()

    private val _accountSelected = MutableSharedFlow<Pair<String, String>>()
    val accountSelected = _accountSelected.asSharedFlow()

    private val _failure = MutableSharedFlow<PresentationError>()
    val failure = _failure.asSharedFlow()

    init {
        viewModelScope.launch {
            getAccounts()
            _homeState.emit(HomeState.Loading)
        }
    }

    private fun accountSelected(uid: String, defaultCategory: String) = viewModelScope.launch { _accountSelected.emit(
        Pair(uid, defaultCategory)
    ) }

    private fun getAccounts() {
        accountUseCase.invoke(viewModelScope, Unit) { result ->
            result.result(
                onSuccess = { accounts ->
                    accounts?.let {
                        emitAccounts(createAccountListItems(it))
                    } ?: run {
                        emitFailure(null)
                    }
                },
                onFailure = { emitFailure(it.message) }
            )
        }
    }

    private fun createAccountListItems(accounts: List<Account>): List<AccountListItem> {
        val accountList = mutableListOf<AccountListItem>()
        accounts.forEach {
            accountList.add(
                AccountListItem(
                    name = it.name,
                    uid = it.accountUid,
                    defaultCategory = it.defaultCategory,
                    onClick = { uid, default -> accountSelected(uid, default) }
                )
            )
        }
        return accountList
    }

    private fun emitFailure(failure: String?) = viewModelScope.launch {
        failure?.let { _failure.emit(PresentationError.StringError(it)) } ?: run {
            _failure.emit(PresentationError.IntError(R.string.error))
        }

    }

    private fun emitAccounts(accounts: List<AccountListItem>) = viewModelScope.launch {
        _homeState.emit(HomeState.Reading(accounts))
    }

    sealed class HomeState {
        object Loading : HomeState()
        class Reading(val accounts: List<AccountListItem>) : HomeState()
    }

//    sealed class HomeEvent {
//        class AccountSelected(val accountUid: String) : HomeEvent()
//    }

}