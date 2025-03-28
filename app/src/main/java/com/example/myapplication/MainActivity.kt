package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountInput1 : EditText = findViewById(R.id.editTextNumberDecimal)
        val amount2 : TextView = findViewById(R.id.textView6)
        val currencySymbol1 : TextView = findViewById(R.id.textView3)
        val currencySymbol2 : TextView = findViewById(R.id.textView5)
        val currencySpinner1 : Spinner = findViewById(R.id.spinner2)
        val currencySpinner2: Spinner = findViewById(R.id.spinner3)
        val textUnit : TextView = findViewById(R.id.textView2)

        val currencies = mutableListOf("Vietnam - Dong", "Thailand - Bath", "Europe - Euro", "Japan - Yen", "China - Yuan")
        val symbols = mutableListOf("₫", "฿", "€", "¥", "¥")
        val exchangeRates = mapOf(
            "Vietnam - Dong" to Pair(27700.0, "VND"),  // 1 EUR ≈ 27,000 VND
            "Thailand - Bath" to Pair(36.77, "THB"),    // 1 EUR ≈ 38.5 THB
            "Europe - Euro" to Pair(1.0, "EUR"),       // 1 EUR = 1 EUR
            "Japan - Yen" to Pair(162.3, "JYP"),       // 1 EUR ≈ 160 JPY
            "China - Yuan" to Pair(7.866, "CNY")         // 1 EUR ≈ 7.8 CNY
        )

        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.custom_dropdown,
            currencies
        )
        arrayAdapter.setDropDownViewResource(R.layout.custom_dropdown)

        fun convertCurrency(){
            val amountText = amountInput1.text.toString()
            if (amountText.isEmpty() == false){
                val amount = amountText.toDouble()
                val fromKey = currencies[currencySpinner1.selectedItemPosition]
                val toKey = currencies[currencySpinner2.selectedItemPosition]
                val result = amount * exchangeRates[toKey]!!.first / exchangeRates[fromKey]!!.first
                amount2.text = String.format("%.3f", result)
            }
            else{
                amount2.text = "0"
            }
        }

        currencySpinner1.run{
            adapter = arrayAdapter
            onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (view as? TextView)?.setTypeface(null, android.graphics.Typeface.BOLD)
                    currencySymbol1.text = symbols[position]
                    val fromKey = currencies[currencySpinner1.selectedItemPosition]
                    val toKey = currencies[currencySpinner2.selectedItemPosition]
                    val toKeyVal = exchangeRates[toKey]!!.first / exchangeRates[fromKey]!!.first
                    textUnit.text = "1.0 ${exchangeRates[fromKey]!!.second} = ${String.format("%.4f", toKeyVal)} ${exchangeRates[toKey]!!.second}"
                    convertCurrency()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        currencySpinner2.run{
            adapter = arrayAdapter
            onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (view as? TextView)?.setTypeface(null, android.graphics.Typeface.BOLD)
                    currencySymbol2.text = symbols[position]
                    val fromKey = currencies[currencySpinner1.selectedItemPosition]
                    val toKey = currencies[currencySpinner2.selectedItemPosition]
                    val toKeyVal = exchangeRates[toKey]!!.first / exchangeRates[fromKey]!!.first
                    textUnit.text = "1.0 ${exchangeRates[fromKey]!!.second} = ${String.format("%.4f", toKeyVal)} ${exchangeRates[toKey]!!.second}"
                    convertCurrency()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        amountInput1.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
