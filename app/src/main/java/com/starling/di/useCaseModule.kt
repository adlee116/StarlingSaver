package com.starling.di

import com.starling.domain.useCases.AddToSavingsGoalUseCase
import com.starling.domain.useCases.CreateSavingsGoalUseCase
import com.starling.domain.useCases.GetAccountsUseCase
import com.starling.domain.useCases.GetTransactionsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetAccountsUseCase(get()) }
    single { GetTransactionsUseCase(get()) }
    single { CreateSavingsGoalUseCase(get()) }
    single { AddToSavingsGoalUseCase(get()) }

}