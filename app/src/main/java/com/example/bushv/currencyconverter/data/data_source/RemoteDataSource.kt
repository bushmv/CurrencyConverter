package com.example.bushv.currencyconverter.data.data_source

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

private const val cbr_url = "https://www.cbr.ru/scripts/XML_daily.asp"

class RemoteDataSource: DataSource<Document> {

    override fun get(): Document {
        val url = URL(cbr_url)
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val document = documentBuilder.parse(InputSource(url.openStream()))
        document.documentElement.normalize()
        return document
    }
}