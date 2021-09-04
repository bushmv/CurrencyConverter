package com.example.bushv.currencyconverter.domain

class Converter {

    // count * from_currency_value * from_nominal = result * to_currency_nominal * to_nominal =>
    // result = (count * from_currency_value * to_nominal) / (to_currency_nominal * from_nominal)
    fun convert(from: Currency, to: Currency, count: Float): Float {
        if (count < 0 ) throw IllegalArgumentException("Count should not be less then zero, but it = $count")
        if (from.value == to.value) return count
        return (count * from.value * to.nominal) / (to.value * from.nominal)
    }

}