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
import com.example.bushv.currencyconverter.databinding.FragConverterBinding

class ConvertFragment : Fragment() {

    private enum class InputFrom { USER, SYSTEM }

    private lateinit var binding: FragConverterBinding
    private var inputFrom: InputFrom = InputFrom.USER

    private val leftTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (inputFrom == InputFrom.USER) {
                // TODO update view model
            }
        }
    }

    private val rightTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (inputFrom == InputFrom.USER) {
                // TODO update view model
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
        // TODO: add click listeners for change currencies
    }

    private fun setTextWatchers() {
        binding.apply {
            leftCurrencyValue.addTextChangedListener(leftTextWatcher)
            rightCurrencyValue.addTextChangedListener(rightTextWatcher)
        }
    }

    private fun setViewModelObservers() {
        // TODO: subscribe on livedata from view model
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