package com.example.bushv.currencyconverter.presentation.load_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bushv.currencyconverter.R
import com.example.bushv.currencyconverter.databinding.FragLoadingBinding

class LoadingFragment: Fragment() {

    private lateinit var binding: FragLoadingBinding
    private val viewModel by viewModels<LoadingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Error -> showErrorLoadingMessage()
                is Result.Success ->  {}// TODO: navigate to converter fragment
            }
        }
    }

    private fun showErrorLoadingMessage() {
        Toast.makeText(requireContext(), getString(R.string.loading_error_message), Toast.LENGTH_LONG).show()
    }

}