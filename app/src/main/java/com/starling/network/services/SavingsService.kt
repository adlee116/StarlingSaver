package com.starling.network.services

import com.starling.network.entities.SavingsGoalResponse
import com.starling.network.entities.SavingsGoalTransferResponse
import com.starling.network.requests.SavingsGoalRequest
import com.starling.network.requests.TopUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SavingsService {

    @PUT("api/v2/account/{accountUid}/savings-goals")
    suspend fun createSavingsGoal(
        @Path("accountUid") accountUid: String,
        @Body savingsGoalRequest: SavingsGoalRequest
    ): Response<SavingsGoalResponse>

    @PUT("api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}")
    suspend fun addToSavingsGoal(
        @Path("accountUid") accountUid: String,
        @Path("savingsGoalUid") categoryUid: String,
        @Path("transferUid") transferUid: String,
        @Body topUpRequest: TopUpRequest
    ): Response<SavingsGoalTransferResponse>

}