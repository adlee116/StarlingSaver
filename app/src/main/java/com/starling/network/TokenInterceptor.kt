package com.starling.network

import okhttp3.*

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sessionsToken
        val request = builderWithAllHeaders(originalRequest, token).build()
        return chain.proceed(request)
    }

    private fun builderWithAllHeaders(original: Request, token: String): Request.Builder =
        original.newBuilder().apply {
            addHeader("Authorization", token)
            addHeader("Accept", "application/json")
            addHeader("User-Agent", "Aidan Lee")
            url(original.url())
        }

    private val sessionsToken: String = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lS3Oqy3APBZuueUH8gHDaGSrLCRKErvZSuXfI5AwxuvKsbvn0aMZ-JNJ57I-g0kyTqN5cx6skvo8gL6-oRmz18zNQ4gQOECXn5Dx7h1ZlXNiHdYDO3UkShK5oLoLwfR7yvqiaYs2r9uqfc0k-Eg0RXdaCEA0s_Y_jeJkf0kearfvUA9NR4y60KA6VQ0bqlywmtdFI-i9Kt-X2t5cSceMgjdlU7YNK7BtWcWB2IBlwYgoCPnpVFIVMsJYPxDJuZgluqHmIgfG265mFdacDTkvWT0UVQ1lg13eLAOjmWh5lOiUoTKOeG8J-MvGXVb7TMNITwX_NT0IkpP2UkiyR15J5w9MApzbYLwnLv0NRMV7wMtIt8gdf1rp6QVmfzFWurBGJjWXH5LPoGLwAAo0JmsIljM02lujYqOFSZrRQtoRvDSaGcHErLm7Se7WfQOxNc7Om3EbkUaQqbCiYESfe5gm9XVDa9QImoOnnpOiUGKDSbNX8ssgkyVBloJ39z8p2ojapAApvICns13nuE_8LqZUsniBbbqRPAQ30GOAq5rwOtQEX0SbFEEaIoI9iMkRzmmmRITOVm9rifF3sregHeDuOtBsmNW137ZLO7U7iHg3EfFWYLmRcG-j9HtNZTCYuKuwEswsR_LIpixrhFTbSHHGA7VGWUKSkz8Ad5Tig6_Ls6CS6OAj7NSxs9ltHbg03IH7lhnfLxR-VmIXn9TaxVgUL8RnRZyl90s0eR_mnacEJ9i-qfADXW-bGcvv2h_Zre-RfZLPzKe-8Z7WBaL7eKQmLhI1Dw5teNzlbrYu99wadX9c6_4ery37-w-gvgQjGQYAAA.dWZdzXRX0tpRKE_qkabTz6IzwJ9lXjCmJWpPUSg4LHG67WmVMX-8_nZeAN2YTlEzisUgM5pRE32VQ7Ul3M2hrISv0jPSRNelAzr1BvId1EOmCaGX9KYOxmrN2EPyTkzJUlatTzuiEtgQM27CKHWI_fuAr51CujMX-wbdyLtZv3SqftMQWVpspJ-2IL1zk3m8_lacQEzk-tFibqEM2dEDOd7ld0LNdUQjrrlkzPDQurjkWHvCGZYxfcet7wogQB1DUfOQlH47rorDoTI4bcea2pS9UZJC8-zWsyigmUbm1GjF0RgEXKB0_F928P0rics2m-a_o5Q4TN01FzCNBehxGgRCj2JfksA-8LrrbZ6Ff3xa_qUeebJM7IqqbkZWIMTEa288mH4cN9WFSgN5hMll_fEb_siitqEKo8VctBVZuI65xGs8-n64cNhHPlpEZz0zlxiAeVqJyr6F0kJj-G2sQ8l-wpNrcsyeQr7Ak3EIgfMAkJRhM0sLGqgcfR9IVHENItdJYPug4D2_avZy6feOawKUK-tt0tvDJLb4gI-LXMndaElIvxGzIwMRosqKNOrD8nliVUz2rHM-W3H_YRujSJ3YPewn7JXIfokNnse2cB-0IyPX65LLgqalXtz5Yjkff4hMRFkLa-RPom28v-fW3W-KQWcihSRTic4Oy3kndIY"

}



