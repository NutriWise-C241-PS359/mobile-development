package com.product.nutriwise.ui.main.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.product.nutriwise.R

class BreakfastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakfast)

        supportActionBar?.setTitle("Sarapan")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}