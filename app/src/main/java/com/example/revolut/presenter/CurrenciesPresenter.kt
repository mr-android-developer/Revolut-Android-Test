package com.example.revolut.presenter

import android.os.Bundle
import com.example.revolut.view.CurrenciesView

interface CurrenciesPresenter {

    fun onCreate(currenciesView: CurrenciesView, savedInstanceState: Bundle?)

    fun onClickMainCurrency()

    fun onEnterValue(code: String, value: Float?)

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroy()
}