package com.example.bushv.currencyconverter.data

import com.example.bushv.currencyconverter.domain.Currency

object Repository {
    val currencyMap: MutableMap<String, Currency> = HashMap()

    var currencyCharCodeList: ArrayList<Currency> = ArrayList()
    get() {
        return if (field.size == currencyMap.size) {
            field
        } else {
            field.clear()
            currencyMap.values.forEach {
                field.add(it)
            }
            field
        }
    }

}