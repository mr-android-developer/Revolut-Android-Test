package com.example.revolut.view

import android.graphics.drawable.Drawable

interface EnterValueDialogView {

    fun showIcon(icon: Drawable?)

    fun showValue(value: String)

    fun showInvalidValue()

    fun hideInvalidValue()

    fun notifyResult(result: Float?)

    fun close()
}