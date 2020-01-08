package com.example.revolut.presenter

import android.graphics.drawable.Drawable

interface IconProvider {
    fun getIcon(currencyCode: String): Drawable?
}