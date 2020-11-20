package com.boyko.rxjavasearch

import android.database.Observable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.boyko.rxjavasearch.Text.Companion.TEXT
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Flowable
import java.util.*
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
//        searchText.addTextChangedListener() { it ->
//        Подписаться на изменение текста получилось, но не могу сделать Flowable.debounce для этого едит текста
//        ПРОШУ ПОМОЩИ
//        }
        val flowable = Flowable.just(searchText)
        flowable.debounce(1L, TimeUnit.SECONDS).subscribe {
            println("mytag $it")
            // println("mytag ${searchText.text}")
        }
    }
}