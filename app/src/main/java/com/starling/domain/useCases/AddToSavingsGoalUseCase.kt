package com.starling.domain.useCases

import com.starling.core.BaseUseCase
import com.starling.core.Result
import com.starling.domain.repositories.savings.SavingsRepository
import com.starling.network.entities.SavingsGoalTransferResponse
import com.starling.network.requests.TopUpRequest

class AddToSavingsGoalUseCase(private val savingsRepository: SavingsRepository) : BaseUseCase<SavingsGoalTransferResponse?, AddToSavingsGoalUseCase.Params>() {

    override suspend fun run(params: Params): Result<SavingsGoalTransferResponse?, Exception> {
        return try {
            val savingsGoal = savingsRepository.addToSavingsGoal(
                params.accountUid,
                params.savingsGoalUid,
                params.transferUid,
                params.topUpRequest
            )
            Result.Success(savingsGoal)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    data class Params(
        val accountUid: String,
        val savingsGoalUid: String,
        val transferUid: String,
        val topUpRequest: TopUpRequest
    )
}