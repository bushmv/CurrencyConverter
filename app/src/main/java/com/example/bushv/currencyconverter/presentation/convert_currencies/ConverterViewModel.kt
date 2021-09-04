package com.example.bushv.currencyconverter.presentation.convert_currencies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bushv.currencyconverter.domain.Converter
import com.example.bushv.currencyconverter.domain.Currency
import com.example.bushv.currencyconverter.presentation.change_currency.CurrencySide

class ConverterViewModel : ViewModel() {

    private val converter = Converter()

    val leftCurrency: MutableLiveData<Currency> = MutableLiveData()
    val rightCurrency: MutableLiveData<Currency> = MutableLiveData()

    val leftValue: MutableLiveData<Float> = MutableLiveData()
    val rightValue: MutableLiveData<Float> = MutableLiveData()

    val infoMessages: MutableLiveData<InfoMessageType> = MutableLiveData()

    private fun convert(direction: ConversionDirection, amount: Float) {
        when (direction) {
            ConversionDirection.LEFT_TO_RIGHT -> {
                val from = leftCurrency.value
                    ?: throw IllegalStateException("leftCurrency.value must not be null")
                val to = rightCurrency.value
                    ?: throw IllegalStateException("rightCurrency.value must not be null")
                val convertedValue = converter.convert(from, to, amount)
                rightValue.value = convertedValue
            }
            ConversionDirection.RIGHT_TO_LEFT -> {
                val from = rightCurrency.value
                    ?: throw IllegalStateException("rightCurrency.value must not be null")
                val to = leftCurrency.value
                    ?: throw IllegalStateException("leftCurrency.value must not be null")
                val convertedValue = converter.convert(from, to, amount)
                leftValue.value = convertedValue
            }
        }
    }

    fun currencyChanged(side: CurrencySide, newCurrency: Currency) {
        when (side) {
            CurrencySide.LEFT -> {
                leftCurrency.value = newCurrency
                val rightFloatValue = rightValue.value
                if (rightFloatValue != null && bothCurrenciesAreSet()) {
                    convert(ConversionDirection.RIGHT_TO_LEFT, rightFloatValue)
                } else {
                    sendInfoMessage()
                }
            }
            CurrencySide.RIGHT -> {
                rightCurrency.value = newCurrency
                val leftFloatValue = leftValue.value
                if (leftFloatValue != null && bothCurrenciesAreSet()) {
                    convert(ConversionDirection.LEFT_TO_RIGHT, leftFloatValue)
                } else {
                    sendInfoMessage()
                }
            }
        }
    }

    private fun bothCurrenciesAreSet(): Boolean =
        leftCurrency.value != null && rightCurrency.value != null

    fun leftValueChanged(input: String) {
        val value = stringToFloat(input)
        leftValue.value = value
        if (bothCurrenciesAreSet()) {

            convert(ConversionDirection.LEFT_TO_RIGHT, value)
        } else {
            sendInfoMessage()
        }

    }

    fun rightValueChanged(input: String) {
        val value = stringToFloat(input)
        rightValue.value = value
        if (bothCurrenciesAreSet()) {
            convert(ConversionDirection.RIGHT_TO_LEFT, value)
        } else {
            sendInfoMessage()
        }
    }

    // in rare cases user input cannot be converted into float (e.g. a dot), in such cases we return 0
    private fun stringToFloat(string: String): Float {
        return try {
            string.toFloat()
        } catch (e: NumberFormatException) {
            0f
        }
    }

    private fun sendInfoMessage() {
        if (leftCurrency.value == null || rightCurrency.value == null) {
            infoMessages.value = InfoMessageType.SET_FROM_AND_TO_CURRENCY
        } else if (leftValue.value == null && rightValue.value == null) {
            infoMessages.value = InfoMessageType.SET_VALUE_FOR_CONVERTING
        }
    }

}