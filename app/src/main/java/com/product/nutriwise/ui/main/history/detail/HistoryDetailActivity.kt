package com.product.nutriwise.ui.main.history.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.product.nutriwise.R

class HistoryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)

        supportActionBar?.setTitle("DD/MM/YYYY")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_breakfast -> {
                val intent = Intent(this, BreakfastActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.option_lunch -> {
                val intent = Intent(this, LunchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.option_dinner -> {
                val intent = Intent(this, DinnerActivity::class.java)
                startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}