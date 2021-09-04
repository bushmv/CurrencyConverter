package com.example.bushv.currencyconverter.presentation.load_data

import java.lang.RuntimeException

class LoadingException(message: String, cause: Throwable): RuntimeException(message, cause)