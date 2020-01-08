package com.example.revolut.repository

import com.example.revolut.repository.data.Response

interface Repository {
    suspend fun getRates(base: String): Response
}