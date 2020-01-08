package com.example.revolut.presenter

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.revolut.R
import com.example.revolut.repository.data.Code

class IconProviderImpl(context: Context) : IconProvider {

    private val appContext = context.applicationContext

    override fun getIcon(currencyCode: String): Drawable? =
        when(currencyCode) {
            Code.AUD.name -> appContext.resources.getDrawable(R.drawable.au, appContext.theme)
            Code.BGN.name -> appContext.resources.getDrawable(R.drawable.bg, appContext.theme)
            Code.BRL.name -> appContext.resources.getDrawable(R.drawable.br, appContext.theme)
            Code.CAD.name -> appContext.resources.getDrawable(R.drawable.ca, appContext.theme)
            Code.CHF.name -> appContext.resources.getDrawable(R.drawable.ch, appContext.theme)
            Code.CNY.name -> appContext.resources.getDrawable(R.drawable.cn, appContext.theme)
            Code.CZK.name -> appContext.resources.getDrawable(R.drawable.cz, appContext.theme)
            Code.DKK.name -> appContext.resources.getDrawable(R.drawable.dk, appContext.theme)
            Code.GBP.name -> appContext.resources.getDrawable(R.drawable.gb, appContext.theme)
            Code.HKD.name -> appContext.resources.getDrawable(R.drawable.hk, appContext.theme)
            Code.HRK.name -> appContext.resources.getDrawable(R.drawable.hr, appContext.theme)
            Code.HUF.name -> appContext.resources.getDrawable(R.drawable.hu, appContext.theme)
            Code.IDR.name -> appContext.resources.getDrawable(R.drawable.id, appContext.theme)
            Code.ILS.name -> appContext.resources.getDrawable(R.drawable.il, appContext.theme)
            Code.INR.name -> appContext.resources.getDrawable(R.drawable.`in`, appContext.theme)
            Code.ISK.name -> appContext.resources.getDrawable(R.drawable.`is`, appContext.theme)
            Code.JPY.name -> appContext.resources.getDrawable(R.drawable.jp, appContext.theme)
            Code.KRW.name -> appContext.resources.getDrawable(R.drawable.kr, appContext.theme)
            Code.MXN.name -> appContext.resources.getDrawable(R.drawable.mx, appContext.theme)
            Code.MYR.name -> appContext.resources.getDrawable(R.drawable.my, appContext.theme)
            Code.NOK.name -> appContext.resources.getDrawable(R.drawable.no, appContext.theme)
            Code.NZD.name -> appContext.resources.getDrawable(R.drawable.nz, appContext.theme)
            Code.PHP.name -> appContext.resources.getDrawable(R.drawable.ph, appContext.theme)
            Code.PLN.name -> appContext.resources.getDrawable(R.drawable.pl, appContext.theme)
            Code.RON.name -> appContext.resources.getDrawable(R.drawable.ro, appContext.theme)
            Code.RUB.name -> appContext.resources.getDrawable(R.drawable.ru, appContext.theme)
            Code.SEK.name -> appContext.resources.getDrawable(R.drawable.se, appContext.theme)
            Code.SGD.name -> appContext.resources.getDrawable(R.drawable.sg, appContext.theme)
            Code.THB.name -> appContext.resources.getDrawable(R.drawable.th, appContext.theme)
            Code.TRY.name -> appContext.resources.getDrawable(R.drawable.tr, appContext.theme)
            Code.USD.name -> appContext.resources.getDrawable(R.drawable.us, appContext.theme)
            Code.ZAR.name -> appContext.resources.getDrawable(R.drawable.za, appContext.theme)
            Code.EUR.name -> appContext.resources.getDrawable(R.drawable.european_union, appContext.theme)
            else -> null
        }
}