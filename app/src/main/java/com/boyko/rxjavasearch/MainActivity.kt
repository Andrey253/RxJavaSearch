package com.boyko.rxjavasearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.boyko.rxjavasearch.Text.Companion.TEXT
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var tvfail:TextView? = null
    private var tvcount:TextView? = null
    private var searchText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvcount = findViewById<TextView>(R.id.tvcount)
        tvfail = findViewById<TextView>(R.id.tvfail)
        tvfail?.text = TEXT
        tvcount?.text = "0"
        searchText = findViewById<TextInputEditText>(R.id.searchtext)

        searchText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onTextChanged: $s")
                find(s.toString())
            }
            override fun afterTextChanged(s: Editable?) { }
        })
    }

    fun find(search: String){
        tvcount?.text = (TEXT+ "смутно").split("смутно").size.toString()
        Log.d(TAG, "find: ${(TEXT+ "смутно").split("смутно").size}")
    }

    companion object{
        val TAG = "mytag"
    }
}