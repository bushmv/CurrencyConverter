package com.example.bushv.currencyconverter.presentation.load_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bushv.currencyconverter.data.Repository
import com.example.bushv.currencyconverter.data.XmlDocumentParser
import com.example.bushv.currencyconverter.data.data_source.DataSource
import com.example.bushv.currencyconverter.data.data_source.RemoteDataSource
import com.example.bushv.currencyconverter.domain.Currency
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Document

class LoadingViewModel : ViewModel() {

    private val _loadingResult: MutableLiveData<Result<Boolean, LoadingException>> =
        MutableLiveData()
    val loadingResult: LiveData<Result<Boolean, LoadingException>> = _loadingResult

    private val dataSource: DataSource<Document> = RemoteDataSource()
    private val parser = XmlDocumentParser()

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _loadingResult.postValue(Result.Error(false, LoadingException("Can't load data from server", error)))
    }

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            val document = dataSource.get()
            val currencies = parser.parse(document)
            addLoadedCurrencyToRepository(currencies)

            withContext(Dispatchers.Main) {
                _loadingResult.value = Result.Success(true)
            }
        }
    }

    private fun addLoadedCurrencyToRepository(currencies: List<Currency>) {
        Repository.currencyCharCodeList = currencies
    }
}