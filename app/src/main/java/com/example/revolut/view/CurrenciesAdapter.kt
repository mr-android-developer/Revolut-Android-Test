package com.example.revolut.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut.R
import com.example.revolut.presenter.CurrencyItem

class CurrenciesAdapter(
    private val currencyListener: CurrencyListener,
    currencies: List<CurrencyItem>
) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    private val currencyItems = currencies.toMutableList()

    private var selectedIndex: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rate_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyItem = currencyItems[position]
        holder.codeView.text = currencyItem.code
        holder.nameView.text = currencyItem.name
        holder.flagView.setImageDrawable(currencyItem.icon)
        holder.rateView.text = currencyListener.onCalculateRate(currencyItem)
        holder.itemView.setOnClickListener {
            currencyListener.onClickCurrencyItem(currencyItem, position)
        }
        holder.rateView.setOnClickListener {
            currencyListener.onClickRateView(currencyItem)
        }
        if (currencyListener.isSelectedCurrency(currencyItem)) {
            holder.underlineView.visibility = View.VISIBLE
        } else {
            holder.underlineView.visibility = View.GONE
        }
    }

    fun addCurrencyAt(position: Int, currencyItem: CurrencyItem) {
        currencyItems.add(position, currencyItem)
        notifyItemInserted(position)
    }

    fun removeCurrencyAt(position: Int) {
        currencyItems.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val flagView: ImageView = view.findViewById(R.id.flagView)

        val codeView: TextView = view.findViewById(R.id.codeView)

        val nameView: TextView = view.findViewById(R.id.nameView)

        val rateView: TextView = view.findViewById(R.id.rateView)

        val underlineView: View = view.findViewById(R.id.underlineView)
    }
}