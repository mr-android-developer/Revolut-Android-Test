package com.example.revolut.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.revolut.*
import com.example.revolut.presenter.CurrenciesPresenter
import com.example.revolut.presenter.CurrencyItem

class CurrenciesViewImpl(
    private val currenciesPresenter: CurrenciesPresenter,
    private val currencyListener: CurrencyListener
) : CurrenciesView {

    private lateinit var mainCurrencyIcon: ImageView

    private lateinit var mainCurrencyCode: TextView

    private lateinit var mainCurrencyName: TextView

    private lateinit var mainCurrencyValue: TextView

    private lateinit var context: Context

    private lateinit var ratesView: RecyclerView

    private lateinit var adapter: CurrenciesAdapter

    private lateinit var progressBarView: ProgressBar

    fun onCreate(rootView: View, savedInstance: Bundle?) {
        context = rootView.context
        mainCurrencyIcon = rootView.findViewById(R.id.mainCurrencyIcon)
        mainCurrencyCode = rootView.findViewById(R.id.mainCurrencyCode)
        mainCurrencyName = rootView.findViewById(R.id.mainCurrencyName)
        mainCurrencyValue = rootView.findViewById(R.id.mainCurrencyValue)
        ratesView = rootView.findViewById(R.id.rates)
        progressBarView = rootView.findViewById(R.id.progressBar)
        ratesView.layoutManager = LinearLayoutManager(context)
        (ratesView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rootView.findViewById<View>(R.id.mainCurrency).setOnClickListener {
            currenciesPresenter.onClickMainCurrency()
        }
        currenciesPresenter.onCreate(this, savedInstance)
    }

    override fun showProgressBar() {
        progressBarView.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBarView.visibility = View.GONE
    }

    override fun showMainCurrency(icon: Drawable?, code: String, name: String, value: String) {
        mainCurrencyIcon.setImageDrawable(icon)
        mainCurrencyCode.text = code
        mainCurrencyName.text = name
        mainCurrencyValue.text = value
    }

    override fun showCurrencies(currencies: List<CurrencyItem>) {
        adapter = CurrenciesAdapter(currencyListener, currencies)
        ratesView.adapter = adapter
    }

    override fun notifyRates() {
        adapter.notifyDataSetChanged()
    }

    override fun removeCurrencyAt(position: Int) {
        adapter.removeCurrencyAt(position)
    }

    override fun addCurrencyAt(position: Int, currencyItem: CurrencyItem) {
        adapter.addCurrencyAt(position, currencyItem)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showEnterValueDialog(code: String, value: String) {
        EnterValueDialogViewImpl(context, code, value) { result ->
            currenciesPresenter.onEnterValue(code, result)
        }.show()
    }

    fun onSaveInstanceState(outState: Bundle) {
        currenciesPresenter.onSaveInstanceState(outState)
    }

    fun onDestroy() {
        currenciesPresenter.onDestroy()
    }
}