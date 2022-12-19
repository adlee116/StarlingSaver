package com.starling.network.entities

// Would like to use enums for the Account type, Currency and Name, if we knew all possible types
data class Account(
    val accountUid: String,
    val accountType: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String,
    val name: String
)