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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val fileanme = "myfile.txt"
    private var searchText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvcount:TextView = findViewById(R.id.tvcount)
        var textscrool = ""
        val tvscrool: TextView =  findViewById(R.id.tvscrool)

        searchText = findViewById<TextInputEditText>(R.id.searchtext)

        tvcount.text = "0"

        try {
            textscrool = readfile()

        } catch (e: Exception) {
            Log.e(TAG, "Файл не прочтен")
        }
        tvscrool.text = textscrool

        val dispose = getFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(700L, TimeUnit.MILLISECONDS).subscribe({
                    findinstory(it)
        },{
            Log.e(TAG, "Throwable")
        },{})
    }

    private fun readfile(): String {
        var t = ""
        val inPut = assets.open(fileanme)
        inPut.bufferedReader().forEachLine {
            t  += it
        }
        return t
    }

    private fun getFlowable(): Flowable<String>{
        return  Flowable.create({subscriber ->
            searchText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    subscriber.onNext(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {}
            })

        }, BackpressureStrategy.BUFFER)
    }

    fun findinstory(search: String){

        val regex = Regex("\b|\\s|\\W")
        val textstory = tvscrool.text.toString()
        val list = textstory.split(regex)
        var count = 0
        list.forEach{
            count += findinword(it, search)
            if (search.length == 0)
                tvcount?.text = "Всего в тексте $count символов"
                else
                tvcount?.text = "Количество совпадений - $count"

        }
    }
    private fun findinword(word: String, search: String): Int{

        val list = word.toLowerCase().split(search)
        return list.size-1

    }
    companion object{
        val TAG = "mytag"
    }
}