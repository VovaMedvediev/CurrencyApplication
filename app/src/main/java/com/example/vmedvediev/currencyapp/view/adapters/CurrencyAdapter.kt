package com.example.vmedvediev.currencyapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.vmedvediev.currencyapp.R
import com.example.vmedvediev.currencyapp.R.id.nameTextView
import com.example.vmedvediev.currencyapp.inflate
import com.example.vmedvediev.currencyapp.model.Currency

class CurrencyAdapter(context: Context?, resource: Int, currencyList: ArrayList<Currency>) : ArrayAdapter<Currency>(context, resource, currencyList) {

    private val currencies = currencyList
    private val initialContext = context

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getCustomView(position, parent)
    }

    private fun getCustomView(position: Int, parent: ViewGroup?): View? {
        val row = parent?.inflate(R.layout.currency_item, false)
        val name =  row?.findViewById<TextView>(R.id.nameTextView)
        name?.text = currencies[position].name
        return row
    }
}