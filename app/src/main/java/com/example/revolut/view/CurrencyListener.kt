package com.example.revolut.view

import com.example.revolut.presenter.CurrencyItem

interface CurrencyListener {

    fun onClickCurrencyItem(currencyItem: CurrencyItem, position: Int)

    fun onClickRateView(currencyItem: CurrencyItem)

    fun onCalculateRate(currencyItem: CurrencyItem): String

    fun isSelectedCurrency(currencyItem: CurrencyItem): Boolean
}