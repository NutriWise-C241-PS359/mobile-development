package com.product.nutriwise.ui.main.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.adapter.HistoryAdapter
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.HistoryItem
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.FragmentHistoryBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.history.detail.HistoryDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private lateinit var viewModel: HistoryViewModel
    private val binding get() = _binding!!
    private val list = ArrayList<HistoryItem>()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[HistoryViewModel::class.java]

        historyAdapter = HistoryAdapter(list)
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }
        viewModel.getSession().observe(viewLifecycleOwner) {
            it?.let {
                token = BR + it.token
                fetchHistoryData(token)
            }
        }
        historyAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: HistoryItem) {
                val intent = Intent(requireContext(), HistoryDetailActivity::class.java)
                intent.putExtra("date", data.date)
                intent.putExtra("label", "breakfast")
                startActivity(intent)
            }
        })
    }

    private fun fetchHistoryData(token: String) {
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.history(token)
                withContext(Dispatchers.Main) {
                    list.clear()
                    response.history?.let { list.addAll(it.filterNotNull()) }
                    historyAdapter.notifyDataSetChanged()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        const val BR = "Bearer "
    }
}
