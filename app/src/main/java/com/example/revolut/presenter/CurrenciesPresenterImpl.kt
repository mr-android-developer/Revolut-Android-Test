package com.example.revolut.presenter

import android.os.Bundle
import android.os.Handler
import com.example.revolut.repository.Repository
import com.example.revolut.repository.data.Code
import com.example.revolut.repository.data.ErrorResponse
import com.example.revolut.repository.data.SuccessResponse
import com.example.revolut.view.CurrenciesView
import com.example.revolut.view.CurrencyListener
import kotlinx.coroutines.*
import java.lang.Runnable
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class CurrenciesPresenterImpl(
    private val repository: Repository,
    private val iconProvider: IconProvider
) : CurrenciesPresenter, CurrencyListener {

    private var currenciesView: CurrenciesView? = null

    private val decimalFormat = NumberFormat.getInstance()

    private var job: Job? = null

    private val scope = CoroutineScope(Dispatchers.Main)

    private val timer = Handler()

    private val runnable = object : Runnable {
        override fun run() {
            job?.cancel()
            loadRates(mainCurrency.code, false)
            timer.postDelayed(this, DELAY)
        }
    }

    private var mainCurrency = CurrencyItem(
        Code.EUR.name,
        Currency.getInstance(Code.EUR.name).displayName, DEFAULT_RATE,
        iconProvider.getIcon(Code.EUR.name)
    )

    private var selectedCurrency = mainCurrency

    private var rates = HashMap<String, Float>()

    private var isCurrencyShown = false

    override fun onCreate(currenciesView: CurrenciesView, savedInstanceState: Bundle?) {
        this.currenciesView = currenciesView
        restoreState(savedInstanceState)
        showMainCurrency()
        loadRates(mainCurrency.code, true)
        currenciesView.showProgressBar()
    }

    @Suppress("UNCHECKED_CAST")
    private fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { state ->
            mainCurrency = state.getParcelable(MAIN_CURRENCY)!!
            mainCurrency.icon = iconProvider.getIcon(mainCurrency.code)
            selectedCurrency = state.getParcelable(SELECTED_CURRENCY)!!
            selectedCurrency.icon = iconProvider.getIcon(selectedCurrency.code)
            rates = state.getSerializable(RATES) as HashMap<String, Float>
            currenciesView?.showCurrencies(prepareCurrencyItems())
        }
    }

    override fun onClickMainCurrency() {
        selectedCurrency = mainCurrency
        currenciesView?.showEnterValueDialog(
            mainCurrency.code,
            mainCurrency.value?.toString() ?: ""
        )
    }

    override fun onEnterValue(code: String, value: Float?) {
        selectedCurrency.value = value
        showMainCurrency()
        notifyRates()
    }

    override fun onClickCurrencyItem(currencyItem: CurrencyItem, position: Int) {
        val oldMainCurrency = mainCurrency
        mainCurrency = currencyItem
        selectedCurrency = mainCurrency
        showMainCurrency()
        currenciesView?.removeCurrencyAt(position)
        currenciesView?.addCurrencyAt(0, oldMainCurrency)
        timer.removeCallbacks(runnable)
        loadRates(mainCurrency.code, true)
        rates = HashMap()
    }

    override fun onClickRateView(currencyItem: CurrencyItem) {
        selectedCurrency = currencyItem
        currenciesView?.showEnterValueDialog(selectedCurrency.code, currencyItem.value?.toString() ?: "")
    }

    override fun onCalculateRate(currencyItem: CurrencyItem): String {
        val value = mainCurrency.value
        if (currencyItem.code != selectedCurrency.code) {
            if (rates.isNotEmpty()) {
                val rate = getRate(currencyItem.code)
                currencyItem.value = value?.let { it * rate }
            } else {
                currencyItem.value = null
            }
        }
        return currencyItem.value?.let { decimalFormat.format(it) } ?: ""
    }

    override fun isSelectedCurrency(currencyItem: CurrencyItem): Boolean =
        currencyItem.code == selectedCurrency.code

    private fun loadRates(code: String, restartTimer: Boolean) {
        job = scope.launch {
            when (val response = repository.getRates(code)) {
                is SuccessResponse -> {
                    if (mainCurrency.code == response.base) {
                        rates = response.rates as HashMap<String, Float>
                        if (!isCurrencyShown) {
                            isCurrencyShown = true
                            currenciesView?.showCurrencies(prepareCurrencyItems())
                        }
                        if (restartTimer) {
                            timer.postDelayed(runnable, DELAY)
                        }
                        showMainCurrency()
                        notifyRates()
                    }
                }
                is ErrorResponse -> {
                    currenciesView?.showError(response.errorDescr)
                }
            }
            currenciesView?.hideProgressBar()
        }
    }

    private fun prepareCurrencyItems(): List<CurrencyItem> = rates.map {
        if (it.key != selectedCurrency.code) {
            val code = it.key
            val rate = it.value
            val currency = Currency.getInstance(code)
            CurrencyItem(code, currency.displayName, rate, iconProvider.getIcon(code))
        } else {
            selectedCurrency
        }
    }

    private fun notifyRates() {
        currenciesView?.notifyRates()
    }

    private fun getRate(currencyCode: String): Float =
        if (currencyCode == mainCurrency.code) {
            DEFAULT_RATE
        } else {
            rates[currencyCode] ?: error("Couldn't find rate for code $currencyCode")
        }

    private fun showMainCurrency() {
        if (mainCurrency.code != selectedCurrency.code) {
            val rate = getRate(selectedCurrency.code)
            mainCurrency.value = selectedCurrency.value?.let { it / rate }
        }
        currenciesView?.showMainCurrency(iconProvider.getIcon(mainCurrency.code), mainCurrency.code,
            mainCurrency.name, mainCurrency.value?.let { decimalFormat.format(it) } ?: "")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(MAIN_CURRENCY, mainCurrency)
        outState.putParcelable(SELECTED_CURRENCY, selectedCurrency)
        outState.putSerializable(RATES, rates)
    }

    override fun onDestroy() {
        if (scope.isActive) {
            scope.cancel()
        }
        currenciesView = null
    }

    companion object {
        private const val DELAY = 1000L

        private const val DEFAULT_RATE = 1f

        private const val MAIN_CURRENCY = "mainCurrency"

        private const val SELECTED_CURRENCY = "selectedCurrency"

        private const val RATES = "rates"
    }
}