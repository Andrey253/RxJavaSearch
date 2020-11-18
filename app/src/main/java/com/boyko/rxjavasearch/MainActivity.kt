package com.boyko.rxjavasearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.boyko.rxjavasearch.Text.Companion.TEXT

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.textView4).text = TEXT
    }
}