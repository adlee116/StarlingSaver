package com.starling.network.services

import com.starling.network.entities.AccountResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountsService {

    @GET("api/v2/accounts")
    suspend fun getAccount(
    ): Response<AccountResponse>
}