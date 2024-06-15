//package com.product.nutriwise.ui.main.home.recomendation
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.product.nutriwise.R
//import com.product.nutriwise.databinding.ActivityRecomendationBinding
//import com.product.nutriwise.ui.main.home.recomendation.webView.WebViewActivity
//
//class RecomendationActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityRecomendationBinding
//    private lateinit var rvRecomendation: RecyclerView
//    private val list = ArrayList<Recomendation>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecomendationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        rvRecomendation = binding.rvRecomendation
//        rvRecomendation.setHasFixedSize(true)
//
//        list.addAll(getListRecomendation())
//        showRecyclerList()
//
//        supportActionBar?.setTitle("Recomendation")
//    }
//
//    private fun showRecyclerList() {
//        rvRecomendation.layoutManager = LinearLayoutManager(this)
//        val listRecomendationAdapter = ListRecomendationAdapter(list)
//        rvRecomendation.adapter = listRecomendationAdapter
//
//        listRecomendationAdapter.setOnItemClickCallback(object : ListRecomendationAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Recomendation) {
//                val intent = Intent(this@RecomendationActivity, WebViewActivity::class.java)
//                startActivity(intent)
//            }
//        })
//    }
//
//    private fun getListRecomendation(): ArrayList<Recomendation> {
//    }
//}