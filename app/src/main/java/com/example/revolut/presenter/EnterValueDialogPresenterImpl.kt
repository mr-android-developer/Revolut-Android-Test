package com.example.revolut.presenter

import com.example.revolut.view.EnterValueDialogView

class EnterValueDialogPresenterImpl(
    private val code: String,
    private val iconProvider: IconProvider
) : EnterValueDialogPresenter {

    private var enterValueDialog: EnterValueDialogView? = null

    private var enteredValue: Float? = null

    override fun onShowDialog(dialog: EnterValueDialogView) {
        this.enterValueDialog = dialog
        enterValueDialog?.showIcon(iconProvider.getIcon(code))
    }

    override fun onEnterValue(value: String) {
        enterValueDialog?.hideInvalidValue()
        if (value.isNotEmpty()) {
            enteredValue = value.toFloatOrNull()
            if (enteredValue == null) {
                enterValueDialog?.showInvalidValue()
            }
        } else {
            enteredValue = null
        }
    }

    override fun onOkClick() {
        enterValueDialog?.notifyResult(enteredValue)
        enterValueDialog?.close()
    }
}
