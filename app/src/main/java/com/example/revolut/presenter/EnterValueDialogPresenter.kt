package com.example.revolut.presenter

import com.example.revolut.view.EnterValueDialogView

interface EnterValueDialogPresenter {

    fun onShowDialog(dialog: EnterValueDialogView)

    fun onEnterValue(value: String)

    fun onOkClick()
}