package com.example.revolut.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import com.example.revolut.R
import com.example.revolut.presenter.EnterValueDialogPresenterImpl
import com.example.revolut.presenter.IconProviderImpl

class EnterValueDialogViewImpl(
    private val context: Context,
    code: String,
    private var value: String,
    private val onResult: (Float?) -> Unit
) : EnterValueDialogView {

    private val enterValueDialogPresenter = EnterValueDialogPresenterImpl(code, IconProviderImpl(context))

    private lateinit var invalidValueView: View

    private lateinit var iconView: ImageView

    private lateinit var valueView: EditText

    private lateinit var okView: View

    private var enterValueDialog: AlertDialog? = null

    fun show() {
        val dialog = AlertDialog.Builder(context)
            .setView(R.layout.enter_value_dialog)
            .setCancelable(true)
            .show()
        iconView = dialog.findViewById(R.id.iconView)
        valueView = dialog.findViewById(R.id.valueView)
        valueView.addTextChangedListener { enterValueDialogPresenter.onEnterValue(it.toString()) }
        valueView.setText(value)
        okView = dialog.findViewById<View>(R.id.okView)
        okView.setOnClickListener { enterValueDialogPresenter.onOkClick() }
        invalidValueView = dialog.findViewById(R.id.invalidValueView)
        invalidValueView = dialog.findViewById(R.id.invalidValueView)
        enterValueDialog = dialog
        enterValueDialogPresenter.onShowDialog(this)
    }

    override fun showIcon(icon: Drawable?) {
        iconView.setImageDrawable(icon)
    }

    override fun showValue(value: String) {
        valueView.setText(value)
        valueView.addTextChangedListener {
            enterValueDialogPresenter.onEnterValue(it.toString())
        }
    }

    override fun showInvalidValue() {
        invalidValueView.visibility = View.VISIBLE
    }

    override fun hideInvalidValue() {
        invalidValueView.visibility = View.INVISIBLE
    }

    override fun notifyResult(result: Float?) {
        onResult(result)
    }

    override fun close() {
        enterValueDialog?.dismiss()
    }
}