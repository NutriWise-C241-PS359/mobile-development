package com.product.nutriwise.ui.webView

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.product.nutriwise.R
import com.product.nutriwise.databinding.ActivitySignupBinding
import com.product.nutriwise.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("QueryPermissionsNeeded", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyWord = "Nasi Goreng"

        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.loadUrl("https://www.google.co.id/maps/search/" + keyWord)

        val addressUri = Uri.parse("geo:0,0?q=$keyWord")
        val mapIntent = Intent(Intent.ACTION_VIEW, addressUri)
        mapIntent.setPackage("com.google.android.apps.maps")


        val packageManager: PackageManager = packageManager
        val resolveInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)

        // Periksa apakah ada aplikasi yang dapat menangani intent ini
        if (resolveInfoList.isNotEmpty()) {
            // Buat daftar nama aplikasi
            val appNames = resolveInfoList.map { it.loadLabel(packageManager).toString() }
            val appIntents = resolveInfoList.map {
                Intent(mapIntent).setPackage(it.activityInfo.packageName)
            }

        }
    }
}



