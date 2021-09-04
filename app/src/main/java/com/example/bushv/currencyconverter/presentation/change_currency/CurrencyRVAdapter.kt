package com.example.bushv.currencyconverter.presentation.change_currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bushv.currencyconverter.R
import com.example.bushv.currencyconverter.databinding.RvItemCurrencyBinding
import com.example.bushv.currencyconverter.domain.Currency

class CurrencyRVAdapter
    (
    private val currencyList: List<Currency>,
    private val currentCurrencyCharCode: String,
    private val onClick: (Currency) -> Unit
) :
    RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(private val binding: RvItemCurrencyBinding, private val onClick: (Currency) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency) {
            binding.apply {
                currencyName.text = currency.name
                currencyCharCode.text = currency.charCode.uppercase()
                root.setOnClickListener { onClick(currency) }
            }
        }

        fun markCurrencyAsCurrent() {
            binding.apply {
                currencyIsChosen.setImageResource(R.drawable.ic_done)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemCurrencyBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencyList[position])
        if (currencyList[position].charCode == currentCurrencyCharCode) {
            holder.markCurrencyAsCurrent()
        }
    }

    override fun getItemCount(): Int = currencyList.size

}