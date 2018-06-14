package com.example.vmedvediev.currencyapp.view

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.vmedvediev.currencyapp.R
import com.example.vmedvediev.currencyapp.model.Currency
import com.example.vmedvediev.currencyapp.presenter.MainPresenter
import com.example.vmedvediev.currencyapp.view.adapters.CurrencyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this)
    }
    private var initialCurrencyCode: String? = null
    private var convertedCurrencyCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.makeGetCurrencyCodesRequest()

        convertButton.setOnClickListener {
            val convertingValue = convertingValueEditText?.text
            presenter.prepareConvertRequest(initialCurrencyCode, convertingValue, convertedCurrencyCode)
        }
    }

    override fun hideLoading() {
        loadingCurrencyCodesProgressBar?.visibility = View.GONE
        loadingCurrencyCodesTexiView?.visibility = View.GONE
    }

    override fun showNoCurrencyCodesError() {
        Toast.makeText(this, "Error Loading Currency Codes!", Toast.LENGTH_SHORT).show()
    }

    override fun showConvertingLoading() {
        loadingCurrencyCodesProgressBar?.visibility = View.VISIBLE
    }

    override fun showConvertedValue(convertedValue: Double) {
        convertedValueTextView?.text = convertedValue.toString()
    }

    override fun showConvertingError(error: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error!")
                .setMessage(error)
                .setCancelable(false)
                .setNegativeButton("Try another currencies",
                        DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.create().show()
    }

    override fun showNoInputValueError() {
        convertingValueEditText.error = "Input value for converting!"
    }

    override fun showCurrencyCodes(currencies: ArrayList<Currency>) {
        Toast.makeText(this, "Currency Codes Are Downloaded!", Toast.LENGTH_SHORT).show()

        fromCurrencySpinner?.apply {
            adapter = CurrencyAdapter(this@MainActivity, 0, currencies)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    initialCurrencyCode = (selectedItem as Currency).code
                }
            }
        }
        toCurrencySpinner?.apply {
            adapter = CurrencyAdapter(this@MainActivity, 0, currencies)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    convertedCurrencyCode = (selectedItem as Currency).code
                }
            }
        }
    }
}
