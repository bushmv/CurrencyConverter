package com.example.bushv.currencyconverter.presentation.load_data

sealed class Result<T, E: Throwable?> {
    class Success<T, E: Throwable>(val data: T, error: E? = null): Result<T, E>()
    class Error<T, E: Throwable>(data: T? = null, val error: E): Result<T, E>()
}