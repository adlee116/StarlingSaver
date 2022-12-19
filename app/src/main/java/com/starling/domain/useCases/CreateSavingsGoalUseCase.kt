package com.starling.domain.useCases

import com.starling.core.BaseUseCase
import com.starling.core.Result
import com.starling.domain.repositories.savings.SavingsRepository
import com.starling.network.entities.SavingsGoalResponse
import com.starling.network.requests.CurrencyAndAmount
import com.starling.network.requests.SavingsGoalRequest

class CreateSavingsGoalUseCase(private val repository: SavingsRepository): BaseUseCase<SavingsGoalResponse?, CreateSavingsGoalUseCase.Params>() {

    override suspend fun run(params: Params): Result<SavingsGoalResponse?, Exception> {
        return try {
            val savingsGoalRequest = SavingsGoalRequest(
                name = params.name,
                currency = params.currency,
                target = params.target,
                base64EncodedPhoto = params.base64EncodedPhoto
            )
            Result.Success(repository.createSavingsGoal(params.accountUid, savingsGoalRequest))
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    data class Params(
        val accountUid: String,
        val name: String,
        val currency: String,
        val target: CurrencyAndAmount,
        val base64EncodedPhoto: String
    )
}