package com.example.bushv.currencyconverter.presentation.convert_currencies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bushv.currencyconverter.MainActivity
import com.example.bushv.currencyconverter.R
import com.example.bushv.currencyconverter.databinding.FragConverterBinding
import com.example.bushv.currencyconverter.presentation.change_currency.ChooserCurrencyDialogFragment

class ConvertFragment : Fragment() {

    private enum class InputFrom { USER, SYSTEM }

    private lateinit var binding: FragConverterBinding
    private var inputFrom: InputFrom = InputFrom.USER
    private val viewModel by activityViewModels<ConverterViewModel>()

    private val leftTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (inputFrom == InputFrom.USER) {
                viewModel.leftValueChanged(s.toString())
            }
        }
    }

    private val rightTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (inputFrom == InputFrom.USER) {
                viewModel.rightValueChanged(s.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setTextWatchers()
        setViewModelObservers()
    }

    private fun setOnClickListeners() {
        binding.apply {
            changeLeftCurrency.setOnClickListener {
                val currentCurrencyCharCode = viewModel.leftCurrency.value?.charCode ?: ""
                ChooserCurrencyDialogFragment.create(0, currentCurrencyCharCode)
                    .show((context as MainActivity).supportFragmentManager, "")
            }
            changeRightCurrency.setOnClickListener {
                val currentCurrencyCharCode = viewModel.rightCurrency.value?.charCode ?: ""
                ChooserCurrencyDialogFragment.create(1, currentCurrencyCharCode)
                    .show((context as MainActivity).supportFragmentManager, "")
            }
        }
    }

    private fun setTextWatchers() {
        binding.apply {
            leftCurrencyValue.addTextChangedListener(leftTextWatcher)
            rightCurrencyValue.addTextChangedListener(rightTextWatcher)
        }
    }

    private fun setViewModelObservers() {
        viewModel.leftCurrency.observe(viewLifecycleOwner) {
            binding.fromCurrencyName.text = it.charCode
        }
        viewModel.rightCurrency.observe(viewLifecycleOwner) {
            binding.toCurrencyName.text = it.charCode
        }
        viewModel.leftValue.observe(viewLifecycleOwner) {
            if (binding.leftCurrencyValue.text.toString() != it.toString()) {
                updateEditTextWithoutWatcherObserving(it.toString(), binding.leftCurrencyValue)
            }
        }
        viewModel.rightValue.observe(viewLifecycleOwner) {
            if (binding.rightCurrencyValue.text.toString() != it.toString()) {
                updateEditTextWithoutWatcherObserving(it.toString(), binding.rightCurrencyValue)
            }
        }
        viewModel.infoMessages.observe(viewLifecycleOwner) {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                InfoMessageType.SET_FROM_AND_TO_CURRENCY -> showInfoMessage(getString(R.string.point_from_and_to_currency))
                InfoMessageType.SET_VALUE_FOR_CONVERTING -> showInfoMessage(getString(R.string.point_currency_values))
            }

        }
    }

    private fun updateEditTextWithoutWatcherObserving(text: String, editText: EditText) {
        inputFrom = InputFrom.SYSTEM
        editText.setText(text)
        inputFrom = InputFrom.USER
    }

    private fun showInfoMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}