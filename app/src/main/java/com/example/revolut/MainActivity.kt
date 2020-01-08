package com.example.revolut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.revolut.presenter.IconProviderImpl
import com.example.revolut.presenter.CurrenciesPresenterImpl
import com.example.revolut.view.CurrenciesViewImpl

class MainActivity : AppCompatActivity() {

    private lateinit var ratesView: CurrenciesViewImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ratesPresenter = CurrenciesPresenterImpl(App.instance.repository, IconProviderImpl(this))
        ratesView = CurrenciesViewImpl(ratesPresenter, ratesPresenter)
        ratesView.onCreate(findViewById(R.id.root), savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        ratesView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        ratesView.onDestroy()
    }

}
