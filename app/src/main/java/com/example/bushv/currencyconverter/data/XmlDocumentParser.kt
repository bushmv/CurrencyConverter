package com.example.bushv.currencyconverter.data

import com.example.bushv.currencyconverter.domain.Currency
import com.example.bushv.currencyconverter.domain.CurrencyBuilder
import org.w3c.dom.Document
import org.w3c.dom.Node

private const val base_xml_currency_node_name = "Valute"

class XmlDocumentParser {

    fun parse(document: Document): List<Currency> {
        val result = ArrayList<Currency>()

        val currencyXmlNodes = document.getElementsByTagName(base_xml_currency_node_name)
        for (i in 0 until currencyXmlNodes.length) {
            val currencyBuilder = formCurrencyFromXmlNode(currencyXmlNodes.item(i))
            result.add(Currency(currencyBuilder))
        }

        addCurrencyRuble(result)

        return result
    }

    private fun formCurrencyFromXmlNode(currencyNode: Node): CurrencyBuilder {
        val tempCurrency = CurrencyBuilder()
        setCurrencyId(currencyNode, tempCurrency)
        for (i in 0 until currencyNode.childNodes.length) {
            setCurrencyFields(currencyNode.childNodes.item(i), tempCurrency)
        }
        return tempCurrency
    }

    private fun setCurrencyId(currencyNode: Node, tempCurrency: CurrencyBuilder) {
        if (currencyNode.attributes.length < 1) {
            throw IllegalArgumentException("Node ${currencyNode.nodeName} should contains attributes " +
                    "for \"id\" field, but node not contains attributes.")
        }
        tempCurrency.id = currencyNode.attributes.item(0).textContent
    }

    private fun setCurrencyFields(currencyAttribute: Node, tempCurrency: CurrencyBuilder) {
        when (currencyAttribute.nodeName) {
            "NumCode" -> tempCurrency.numCode = currencyAttribute.textContent.toInt()
            "CharCode" -> tempCurrency.charCode = currencyAttribute.textContent
            "Nominal" -> tempCurrency.nominal = currencyAttribute.textContent.toInt()
            "Name" -> tempCurrency.name = currencyAttribute.textContent
            "Value" -> tempCurrency.value =
                currencyAttribute.textContent.replace(",", ".").toFloat()
            else -> throw IllegalStateException(
                "Looks like the scheme for xml file from server when you get data changed. " +
                        "Domain class Currency doesn't contains field with name ${currencyAttribute.nodeName}"
            )
        }
    }

    // currency ruble doesn't present in data on server, but it should be present in conversion
    private fun addCurrencyRuble(result: ArrayList<Currency>) {
        result.add(Currency("no_id", 643, "RUB", 1, "Российский рубль", 1f))
    }

}