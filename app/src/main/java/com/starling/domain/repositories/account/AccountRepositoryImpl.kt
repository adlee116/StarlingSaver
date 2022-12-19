package com.starling.domain.repositories.account

import com.starling.core.CodeException
import com.starling.network.services.AccountsService
import com.starling.network.entities.Account

class AccountRepositoryImpl(private val accountsService: AccountsService) : AccountRepository {

    override suspend fun getAccount(): List<Account>? {
        val accountResponse = accountsService.getAccount()
        when (accountResponse.isSuccessful) {
            true -> return accountResponse.body()?.accounts
            false -> throw CodeException(accountResponse.code())
        }
    }

}