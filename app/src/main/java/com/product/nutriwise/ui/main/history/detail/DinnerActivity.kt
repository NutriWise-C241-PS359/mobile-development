package com.product.nutriwise.ui.main.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.product.nutriwise.R

class DinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner)

        supportActionBar?.setTitle("Sarapan")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}