package com.boyko.rxjavasearch


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var tvscrool:TextView
        get() = findViewById<TextInputEditText>(R.id.tvscrool)
        set(value) { }
    private var tvcount:TextView
        get() = findViewById<TextInputEditText>(R.id.tvcount)
        set(value) { }
    private var searchText: EditText
        get() = findViewById<TextInputEditText>(R.id.searchtext)
        set(value) {}
    private var text : String = ""
    private var fileanme : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileanme = "myfile.txt"
        tvcount = findViewById(R.id.tvcount)
        tvscrool = findViewById(R.id.tvscrool)
        tvcount.text = "0"

        try {
            readfile()
        } catch (e: Exception) {
            Log.e(TAG, "Файл не прочтен")
        }
        tvscrool.text = text
        val dispose = getFlowable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(700L, TimeUnit.MILLISECONDS).subscribe({
                    find(it)
        },{
            Log.e(TAG, "Throwable")
        },{})
    }

    private fun readfile() {
        var t: String
        val inPut = assets.open(fileanme)
        inPut.bufferedReader().forEachLine {
            text += it
        }
    }

    fun getFlowable(): Flowable<String>{
        return  Flowable.create({subscriber ->
            searchText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    subscriber.onNext(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {}
            })

        }, BackpressureStrategy.LATEST)
    }

    fun find(search: String){
                tvcount?.text = "Количество совпадений - " + (text.split(search).size - 1).toString()
    }
    companion object{
        val TAG = "mytag"
    }
}