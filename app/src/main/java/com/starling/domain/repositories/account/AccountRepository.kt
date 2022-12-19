package com.starling.domain.repositories.account

import com.starling.network.entities.Account

interface AccountRepository {

    suspend fun getAccount(): List<Account>?
}