package com.example.revolut.repository.data

sealed class Response
data class SuccessResponse(val base: String, val rates: Map<String, Float>): Response()
data class ErrorResponse(val errorDescr: String): Response()