package com.boyko.rxjavasearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.boyko.rxjavasearch.Text.Companion.TEXT
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var edtv:TextView
    lateinit var searchText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtv = findViewById<TextView>(R.id.textView4)
        edtv.text = TEXT
        searchText = findViewById<TextInputEditText>(R.id.searchtext)

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("mytag $s")
            }
            override fun afterTextChanged(s: Editable?) {            }

        })
        val myfl = Observable.create<TextWatcher>{
            println("mytag $it")
        }.debounce ( 1L, TimeUnit.SECONDS )
        myfl.subscribe(){
            println("mytag $it")
        }
    }
}