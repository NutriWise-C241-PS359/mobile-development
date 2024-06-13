package com.product.nutriwise.ui.webView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.product.nutriwise.R
import com.product.nutriwise.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
    }
}