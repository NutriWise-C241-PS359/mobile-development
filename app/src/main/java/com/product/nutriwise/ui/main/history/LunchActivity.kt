package com.product.nutriwise.ui.main.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.product.nutriwise.R

class LunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)

        supportActionBar?.setTitle("Sarapan")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}