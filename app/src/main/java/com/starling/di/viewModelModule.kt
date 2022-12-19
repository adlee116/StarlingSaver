package com.starling.di

import com.starling.presentation.home.HomeViewModel
import com.starling.presentation.transactions.TransactionsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }
    single { TransactionsViewModel(get(), get(), get()) }
}