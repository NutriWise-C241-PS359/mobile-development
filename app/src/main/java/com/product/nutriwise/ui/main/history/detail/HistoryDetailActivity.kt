package com.product.nutriwise.ui.main.history.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.adapter.HistoryDetailAdapter
import com.product.nutriwise.data.remote.response.DataItem
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.ResultItem
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityHistoryDetailBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.login.LoginViewModel
import com.product.nutriwise.utils.Utils
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HistoryDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<HistoryDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var rv: RecyclerView
    private lateinit var binding: ActivityHistoryDetailBinding
    private val list = ArrayList<DataItem>()
    private lateinit var dateString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val label = intent.getStringExtra("label")
        dateString = intent.getStringExtra("date").toString()
        val dateString2 = Utils.convertDateFormat(dateString) + " ($label)"

        rv = binding.rvDetail
        rv.setHasFixedSize(true)

        showLoading(true)

        viewModel.getSession().observe(this){
            val token = BR+it.token
            getListHistoryDetail(token,label.toString(), dateString.toString() )
        }

        supportActionBar?.setTitle(dateString2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showRecyclerList(){
        showLoading(false)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = HistoryDetailAdapter(list)
    }

    private fun getListHistoryDetail(token: String, label: String, date: String){
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.historyDetail(token, label, date)
                list.clear()
                response.data?.let { list.addAll(it.filterNotNull()) }
                showRecyclerList()

            }catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_breakfast -> {
                val intent = Intent(this, HistoryDetailActivity::class.java)
                intent.putExtra("date", dateString)
                intent.putExtra("label", "breakfast")
                startActivity(intent)
                true
            }
            R.id.option_lunch -> {
                val intent = Intent(this, HistoryDetailActivity::class.java)
                intent.putExtra("date", dateString)
                intent.putExtra("label", "lunch")
                startActivity(intent)
                true
            }
            R.id.option_dinner -> {
                val intent = Intent(this, HistoryDetailActivity::class.java)
                intent.putExtra("date", dateString)
                intent.putExtra("label", "diner")
                startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbHistorydetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val BR = "Bearer "
    }
}