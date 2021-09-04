package com.example.bushv.currencyconverter.domain

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

class ConverterTest {

    private val converter = Converter()

    private fun createCurrency(nominal: Int, value: Float): Currency {
        return Currency("-1", -1, "", nominal, "", value)
    }

    @Test
    fun `convert from rate less_then_one to rubles with nominals 1 and count 1`() {
        val from = createCurrency(1, 0.24f)
        val to = createCurrency(1, 1f)

        val result = converter.convert(from, to, 1f)

        assertEquals(0.24f, result)
    }

    @Test
    fun `convert from rate more_then_one to rubles with nominals 1 and count 1`() {
        val from = createCurrency(1, 37.15f)
        val to = createCurrency(1, 1f)

        val result = converter.convert(from, to, 1f)

        assertEquals(37.15f, result)
    }

    @Test
    fun `convert from rubles to rate_less_then_one with nominals 1 and count 1`() {
        val from = createCurrency(1, 1f)
        val to = createCurrency(1, 0.25f)

        val result = converter.convert(from, to, 1f)

        assertEquals(4.0f, result)
    }

    @Test
    fun `convert from rubles to rate_more_then_one with nominals 1 and count 1`() {
        val from = createCurrency(1, 1f)
        val to = createCurrency(1, 31.25f)

        val result = converter.convert(from, to, 1f)

        assertEquals(0.032f, result)
    }

    @Test
    fun `convert from rate_less_then_one to rate_more_then_one with nominals 1 and count 1`() {
        val from = createCurrency(1, 0.25f)
        val to = createCurrency(1, 31.25f)

        val result = converter.convert(from, to, 1f)

        assertEquals(0.008f, result)
    }

    @Test
    fun `convert from rate_more_then_one to rate_less_then_one with nominals 1 and count 1`() {
        val from = createCurrency(1, 31.25f)
        val to = createCurrency(1, 0.25f)

        val result = converter.convert(from, to, 1f)

        assertEquals(125f, result)
    }

    @Test
    fun `convert with nominal more then 1`() {
        val from = createCurrency(1, 2f)
        val to = createCurrency(10, 12f)

        val result = converter.convert(from, to, 1f)

        assertEquals(0.016666668f, result)
    }

    @Test
    fun `a negative value must cause an exception`() {
        val from = createCurrency(1, 1f)
        val to = createCurrency(1, 1f)

        assertThrows(IllegalArgumentException::class.java) { converter.convert(from, to, -1f) }
    }

    @Test
    fun `test for count more then one`() {
        val from = createCurrency(1, 2f)
        val to = createCurrency(1, 12f)

        val result = converter.convert(from, to, 10f)

        assertEquals(1.6666666f, result)
    }
}