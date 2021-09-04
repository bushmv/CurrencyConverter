package com.example.bushv.currencyconverter.domain

// mutable version Currency class, need only for build immutable Currency object
data class CurrencyBuilder(
    var id: String = "",
    var numCode: Int = -1,
    var charCode: String = "",
    var nominal: Int = -1,
    var name: String = "",
    var value: Float = -1f
)