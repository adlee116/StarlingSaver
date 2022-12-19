package com.starling.network.services

import com.starling.network.entities.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionService {

    //2020-01-01T12:34:56.000Z
    @GET("api/v2/feed/account/{accountUid}/category/{categoryUid}/transactions-between")
    suspend fun getTransactions(
        @Path("accountUid") accountUid: String,
        @Path("categoryUid") categoryUid: String,
        @Query("minTransactionTimestamp") minTransactionTimestamp: String,
        @Query("maxTransactionTimestamp") maxTransactionTimestamp: String
    ): Response<TransactionResponse>

}