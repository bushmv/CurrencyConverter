package com.example.bushv.currencyconverter.data.data_source

interface DataSource<T> {
    fun get(): T
}