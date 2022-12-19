package com.starling.network.entities

data class SavingsGoalTransferResponse(
    val transferUid: String,
    val success: Boolean,
    val errors: List<ErrorDetail>
)