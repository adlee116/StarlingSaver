package com.starling.network.requests

data class SavingsGoalRequest(
    val name: String,
    val currency: String,
    val target: CurrencyAndAmount,
    val base64EncodedPhoto: String
)

data class CurrencyAndAmount(
    val currency: String,
    val minorUnits: Int
)