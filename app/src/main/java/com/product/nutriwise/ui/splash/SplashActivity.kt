package com.product.nutriwise.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.R
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.login.LoginViewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setActionBar()

        Handler().postDelayed({
            viewModel.getSession().observe(this){
                if (it.isLogin){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun setActionBar() {
        supportActionBar?.hide()
    }
}