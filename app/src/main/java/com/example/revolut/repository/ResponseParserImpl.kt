package com.example.revolut.repository

import com.example.revolut.repository.data.ErrorResponse
import com.example.revolut.repository.data.Response
import com.example.revolut.repository.data.SuccessResponse
import org.json.JSONObject

class ResponseParserImpl : ResponseParser {

    override fun parse(json: String): Response {
        val jsonObject = JSONObject(json)
        val error = jsonObject.optString(ERROR)
        if (error.isEmpty()) {
            val base = jsonObject.getString(BASE)
            val jsonRates = jsonObject.getJSONObject(RATES)
            val rates = HashMap<String, Float>()
            jsonRates.keys().forEach {
                rates += it to jsonRates.getDouble(it).toFloat()
            }
            return SuccessResponse(base, rates)
        }
        return ErrorResponse(error)
    }

    companion object {
        private const val BASE = "base"

        private const val RATES = "rates"

        private const val ERROR = "error"
    }
}