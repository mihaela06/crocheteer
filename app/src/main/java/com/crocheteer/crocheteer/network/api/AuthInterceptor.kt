package com.crocheteer.crocheteer.network.api

import okhttp3.Credentials
import okhttp3.Interceptor
import java.io.IOException

class AuthInterceptor(user: String, password: String) : Interceptor {
    private var credentials: String

    init {
        credentials = Credentials.basic(user, password)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val builder = request.newBuilder().header("Authorization", credentials)
        return chain.proceed(builder.build())
    }
}