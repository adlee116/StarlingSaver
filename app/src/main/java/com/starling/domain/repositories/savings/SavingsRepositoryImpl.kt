package com.starling.domain.repositories.savings

import com.starling.core.CodeException
import com.starling.network.entities.SavingsGoalResponse
import com.starling.network.entities.SavingsGoalTransferResponse
import com.starling.network.requests.SavingsGoalRequest
import com.starling.network.requests.TopUpRequest
import com.starling.network.services.SavingsService

class SavingsRepositoryImpl(private val service: SavingsService): SavingsRepository {

    override suspend fun createSavingsGoal(accountUid: String, savingsGoalRequest: SavingsGoalRequest): SavingsGoalResponse? {
        val response = service.createSavingsGoal(accountUid, savingsGoalRequest)
        return when(response.isSuccessful) {
            true -> response.body()
            false -> throw CodeException(response.code())
        }
    }

    override suspend fun addToSavingsGoal(
        accountUid: String,
        savingsGoalUid: String,
        transferUid: String,
        topUpRequest: TopUpRequest
    ): SavingsGoalTransferResponse? {
        val response = service.addToSavingsGoal(accountUid, savingsGoalUid, transferUid, topUpRequest)
        return when(response.isSuccessful) {
            true -> response.body()
            false -> throw CodeException(response.code())
        }
    }


}