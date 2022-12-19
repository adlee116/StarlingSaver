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

    private val sessionsToken: String = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lS3Oqy1swDbccssP5AOGmcFWWUiUJLzZSuXfI5AwxuvKsbvn0aMZ-JNJ57Img0EK4t58OA9WSX1uQV8_0PTZe-bGNkR02EKdFyioPqEoc2JRY9WKouZuz13ecVWHYP49ZM3ucNyVx3x3LN8zCT4Sxak4TgQgmlH7n0YR21-SQu3jCar2ULPgOjQoi_Ig2jLvREXV7tDxqdyfptreXFnHjJxzpBPWImcqRYm7WtQFkThxhQcqqNjxlBHG-oHIzsWsrm4r6nIQdKyrkFWRaHPai6rdlRXsD1jnh2lgNANPjxKdClTGMTWWgd4W7jLbFxp6fin4r-FJkMTay06y3fJKOr9hEiCywXjDJP0dRMV7wEvP98gVf1rp-Q1GfzFWurBGITXJm6QRVAxuQYHGZA3BkkCjvTUqNpqYpBndSduDl0YL04lu1OTukrt3X0BsjaPzpl9G5B5kKqw4GNHnBoZBfd3RHNWDJvDcECsOJRaYNHtlPw0yWO7YcvDu_idFG1EbFCCHF_B8tvMcj4nfxZTKFi-wTNezh-AGGgxwVhOehxrgi3mRIkhDRLAGCdnDOc2UiNDZ6mUtMf5B9ha0A1xdB1q0o7o2y3Z5pVYHEa8mIl4KTDcS7q2Xfq2pDAYTDxVmQpjpSJ7ZlGVNJ9UyUpxxQ81RlpHl4DfAbaX44PPyLKgkOriFnTpxNqutDZeG23DfMuP7hcKvSqzii1qrGIvihWlUTCK9X6LZ-zDvOCQ4wPJNhR_ofNvCWHpov2WXvlv2Rb4wn_rOe54XiO72TA3UJWpsHdrwuNPdLF0euTnq8bjm_T1fW_b3H7b9pqYZBgAA.IPTKkKbXN8jz6A7aOCNIXlzk3nCyMME2M-LK8Vf7mdxaWaf6nLdYj-YkNuc0lnGp_bqy0DTUBnpqHbKd8yPGtEcUOd-H9l3VuuRanwTkgREXCPQIxCp168RmT8qx3zSM3r88KXofuagrqdJ-yus50noU_Y59CDVUpZZmw2q7-aeRo5rR_WKxgfkuZpUEONZMmBQiL8XUayVCvNWFVRvWxGNDcPnmk6y5mu6tveGBQvirEY3k3es_6-IhJ1ALRZ-U1x4cWyEnqaZAu1PEwhx3Q1ccmH78ZUA5aTDR2agXMwmK2hdFu3xM35JESlmzEEt8xBDP3m96WHvkwycEbTvEzDC8M5XRtWD2-Md5PnCS-XE_EkhTopJCVDAlO2Tk4YzKjkHTjdNGds8x-g2ONKT4L5vbEnXSn8hjUMuUoiY4rq6kUtzfJTB69hKiPmHEttX4gsTU9LoskOd-7dTKgYzohBtZ5u60YQAk_vMuNUf3JosP0G5f5nTgmHp7GkrNRY3ZUoqn2jhWsXxtrXX8WM-hCsHObMJVuFjhoaj0ckhBI7RwWrmSZuM5C8Hg8Y7PkQVGx1b7GRgL6r7lB8kld2lTZPlKt0i88Lb7UVjoN18A7vtpU2YkKIaBzs46pejnrvzTU2t38rVMhJSteVk9Dg14WztwZ0sFpWnemVOjmbpDoLw"

}



