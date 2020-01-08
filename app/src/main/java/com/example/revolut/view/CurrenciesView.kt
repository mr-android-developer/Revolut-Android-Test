package com.example.revolut.view

import android.graphics.drawable.Drawable
import com.example.revolut.presenter.CurrencyItem


interface CurrenciesView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showMainCurrency(icon: Drawable?, code: String, name: String, value: String)

    fun showCurrencies(currencies: List<CurrencyItem>)

    fun showError(error: String)

    fun notifyRates()

    fun removeCurrencyAt(position: Int)

    fun addCurrencyAt(position: Int, currencyItem: CurrencyItem)

    fun showEnterValueDialog(code: String, value: String)
}