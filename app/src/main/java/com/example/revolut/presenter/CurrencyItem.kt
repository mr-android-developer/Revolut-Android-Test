package com.example.revolut.presenter

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable

data class CurrencyItem(
    val code: String,
    val name: String,
    var value: Float?,
    var icon: Drawable?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyItem> {
        override fun createFromParcel(parcel: Parcel): CurrencyItem {
            return CurrencyItem(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyItem?> {
            return arrayOfNulls(size)
        }
    }
}