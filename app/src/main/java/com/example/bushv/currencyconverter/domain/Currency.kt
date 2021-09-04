package com.example.bushv.currencyconverter.domain

data class Currency(
    val id: String,
    val numCode: Int,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Float
) {
    constructor(builder: CurrencyBuilder) : this(
        builder.id,
        builder.numCode,
        builder.charCode,
        builder.nominal,
        builder.name,
        builder.value
    )
}