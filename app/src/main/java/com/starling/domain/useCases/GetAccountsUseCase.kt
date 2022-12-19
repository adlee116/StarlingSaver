package com.starling.domain.useCases

import com.starling.core.BaseUseCase
import com.starling.core.Result
import com.starling.domain.repositories.account.AccountRepository
import com.starling.network.entities.Account

class GetAccountsUseCase(private val accountRepository: AccountRepository): BaseUseCase<List<Account>?, Unit>() {
    override suspend fun run(params: Unit): Result<List<Account>?, Exception> {
        return try {
            val account = accountRepository.getAccount()
            Result.Success(account)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }

    }
}