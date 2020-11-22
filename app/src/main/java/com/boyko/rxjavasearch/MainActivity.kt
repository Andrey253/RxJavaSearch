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
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

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

        getSource().debounce(700L, TimeUnit.MILLISECONDS).subscribe({
            Log.e(TAG, "onNext $it")
            find(it)
        },{
            Log.e(TAG, "Trowable ")
        },{

        })
    }

    fun getSource(): Flowable<String>{
        return  Flowable.create({subscriber ->
            searchText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    subscriber.onNext(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {}
            })

        }, BackpressureStrategy.DROP)
    }

    fun find(search: String){
                tvcount?.text = "Количество совпадений - " + (TEXT.split(search).size - 1).toString()
    }
    companion object{
        val TAG = "mytag"
    }
}