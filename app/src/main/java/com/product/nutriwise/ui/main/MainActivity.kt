package com.product.nutriwise.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.product.nutriwise.R
import com.product.nutriwise.databinding.ActivityMainBinding
import com.product.nutriwise.ui.main.history.HistoryFragment
import com.product.nutriwise.ui.main.home.HomeFragment
import com.product.nutriwise.ui.main.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        @Suppress("DEPRECATION")
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> fragment = HomeFragment()
                R.id.navigation_profile -> fragment = ProfileFragment()
                R.id.navigation_history -> fragment = HistoryFragment()
            }
            if (fragment != null) {
                loadFragment(fragment)
                true
            } else {
                false
            }
        }
    }

    private fun setActionBar() {
        supportActionBar?.hide()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            return
        }

        this.backPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        // Reset the backPressedOnce flag after 2 seconds
        android.os.Handler().postDelayed({ backPressedOnce = false }, 2000)
    }
}
