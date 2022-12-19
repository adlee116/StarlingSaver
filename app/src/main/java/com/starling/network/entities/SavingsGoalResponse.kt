package com.starling.network.entities

data class SavingsGoalResponse(
    val savingsGoalUid: String,
    val success: Boolean,
    val errors: List<ErrorDetail>
)

data class ErrorDetail(val message: String)
