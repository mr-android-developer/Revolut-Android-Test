package com.example.revolut.repository

import android.util.Log
import com.example.revolut.repository.data.ErrorResponse
import com.example.revolut.repository.data.Response
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RepositoryImpl(
    private val responseParser: ResponseParser
) : Repository {

    private val okHttpClient = OkHttpClient()

    override suspend fun getRates(base: String): Response = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(URL + base).build()
        try {
            val result = okHttpClient.newCall(request).execute()
            ensureActive()
            result.body()?.string()?.let(responseParser::parse)
                ?: ErrorResponse("The server returned empty body.")
        } catch (e: IOException) {
            Log.d(TAG, e.toString())
            ensureActive()
            ErrorResponse("The request cannot be executed.")
        }
    }

    companion object {

        private const val TAG = "Repository"

        private const val URL = "https://revolut.duckdns.org/latest?base="
    }
}