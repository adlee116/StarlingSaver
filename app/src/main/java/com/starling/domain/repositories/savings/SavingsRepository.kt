package com.starling.domain.repositories.savings

import com.starling.network.entities.SavingsGoalResponse
import com.starling.network.entities.SavingsGoalTransferResponse
import com.starling.network.requests.SavingsGoalRequest
import com.starling.network.requests.TopUpRequest

interface SavingsRepository {

    suspend fun createSavingsGoal(accountUid: String, savingsGoalRequest: SavingsGoalRequest): SavingsGoalResponse?

    suspend fun addToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        topUpRequest: TopUpRequest
    ): SavingsGoalTransferResponse?
}