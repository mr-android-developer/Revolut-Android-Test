package com.example.revolut.repository

import com.example.revolut.repository.data.Response

interface ResponseParser {
    fun parse(json: String): Response
}