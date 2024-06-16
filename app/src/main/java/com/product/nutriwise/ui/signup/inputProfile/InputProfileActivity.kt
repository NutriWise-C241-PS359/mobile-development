package com.product.nutriwise.ui.signup.inputProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.UserModel
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityInputProfileBinding
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.ui.signup.inputTarget.InputTargetActivity
import kotlinx.coroutines.launch
import retrofit2.http.Field

class InputProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genderList = listOf("Laki-laki", "Perempuan")
        val adapterGender = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)
        binding.dbGender.setAdapter(adapterGender)

        val activityList = listOf("1", "2", "3", "4", "5")
        val adapterActivity = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, activityList)
        binding.dbActivity.setAdapter(adapterActivity)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, InputTargetActivity::class.java))
            finish()
        }
    }

    private fun updateProfile(usia: Int, gender: String, tinggibandan: Int, beratbadan: Int, aktivitas: Int){
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            val response = apiService.updateUser(usia, gender, tinggibandan, beratbadan, aktivitas)
            showToast(response.message.toString())
            val intent = Intent(this@InputProfileActivity, InputTargetActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}