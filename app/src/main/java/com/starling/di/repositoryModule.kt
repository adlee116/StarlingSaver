package com.starling.di

import com.starling.domain.repositories.account.AccountRepository
import com.starling.domain.repositories.account.AccountRepositoryImpl
import com.starling.domain.repositories.savings.SavingsRepository
import com.starling.domain.repositories.savings.SavingsRepositoryImpl
import com.starling.domain.repositories.transactions.TransactionsRepository
import com.starling.domain.repositories.transactions.TransactionsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single <AccountRepository>{ AccountRepositoryImpl(get()) }
    single <TransactionsRepository>{ TransactionsRepositoryImpl(get()) }
    single <SavingsRepository>{ SavingsRepositoryImpl(get()) }

}