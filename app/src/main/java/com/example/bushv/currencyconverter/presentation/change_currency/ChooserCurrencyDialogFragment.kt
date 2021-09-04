package com.example.bushv.currencyconverter.presentation.change_currency

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bushv.currencyconverter.data.Repository
import com.example.bushv.currencyconverter.databinding.FragDialogChooseCurrencyBinding
import com.example.bushv.currencyconverter.domain.Currency
import com.example.bushv.currencyconverter.presentation.convert_currencies.ConverterViewModel

private const val SIDE_KEY = "com.example.bushv.currencyconverter.presentation.change_currency.side"
private const val CHAR_CODE_KEY = "com.example.bushv.currencyconverter.presentation.change_currency.currentCurrencyCharCode"

class ChooserCurrencyDialogFragment : DialogFragment() {

    private lateinit var binding: FragDialogChooseCurrencyBinding
    private lateinit var adapter: CurrencyRVAdapter
    private val viewModel by activityViewModels<ConverterViewModel>()
    private lateinit var currencySide: CurrencySide
    private lateinit var currentCurrencyCharCode: String

    companion object {
        fun create(ordinal: Int, currentCurrencyCharCode: String): ChooserCurrencyDialogFragment {
            if (ordinal != 0 && ordinal != 1) {
                throw IllegalArgumentException(
                    "Ordinal should or 0, or 1 " +
                            " for create CurrencySide enum instance, but ordinal = $ordinal"
                )
            }
            val dialog = ChooserCurrencyDialogFragment()
            val arguments = Bundle().apply {
                putInt(SIDE_KEY, ordinal)
                putString(CHAR_CODE_KEY, currentCurrencyCharCode)
            }
            dialog.arguments = arguments
            return dialog

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val directionOrdinal = getInt(SIDE_KEY, -1)
            currencySide = CurrencySide.values()[directionOrdinal]
            currentCurrencyCharCode = getString(CHAR_CODE_KEY, "")
        } ?: throw IllegalStateException("arguments should not be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragDialogChooseCurrencyBinding.inflate(inflater, null, false)
        binding.apply {
            adapter =
                CurrencyRVAdapter(Repository.currencyCharCodeList, currentCurrencyCharCode) { onClickHandler(it) }
            currencyRecyclerView.adapter = adapter
            currencyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    private fun onClickHandler(currency: Currency) {
        requireDialog().dismiss()
        viewModel.currencyChanged(currencySide, currency)
    }

    override fun onResume() {
        super.onResume()
        setSizeInPercent(80, 80)
    }

    @Suppress("SameParameterValue")
    private fun setSizeInPercent(widthPercentInInt: Int, heightPercentInInt: Int) {
        val widthPercent = widthPercentInInt.toFloat() / 100
        val heightPercent = heightPercentInInt.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val width = rect.width() * widthPercent
        val height = rect.height() * heightPercent
        dialog?.window?.setLayout(width.toInt(), height.toInt())
    }
}