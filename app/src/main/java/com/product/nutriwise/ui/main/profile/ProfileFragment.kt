package com.product.nutriwise.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.FragmentProfileBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.signup.inputProfile.InputProfileActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[ProfileViewModel::class.java]

        viewModel.getSession().observe(viewLifecycleOwner) {
            binding.apply {
                tvNameProfile.text = it.name
                val token = BR + it.token
                ivEdit.setOnClickListener {
                    changeName(token)
                }
            }
        }
        viewModel.getProfile().observe(viewLifecycleOwner) {
            val genderMap = mapOf(
                true to "Laki-laki",
                false to "Perempuan"
            )

            val activityMap = mapOf(
                1 to "sangat jarang berolahraga",
                2 to "jarang berolahraga (1-3 kali perminggu)",
                3 to "cukup berolahraga (3-5 kali perminggu)",
                4 to "sering berolahraga (6-7 kali perminggu)",
                5 to "sangat sering berolahraga(2 kali sehari)"
            )
            binding.apply {
                tvTbProfile2.text = it.tinggibadan.toString()
                tvBbProfile2.text = it.beratbadan.toString()
                tvGenderProfile2.text = genderMap[it.gender]
                tvActivityProfile2.text = activityMap[it.aktivitas]
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearProfile()
            viewModel.clearCalorie()
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.btnWebview.setOnClickListener {

        }

        binding.btnEdtProfile.setOnClickListener {
            val intent = Intent(requireContext(), InputProfileActivity::class.java)
            intent.putExtra(EK, 1)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeName(token: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Name")

        val input = EditText(requireContext())
        input.hint = "Enter new name"
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val newName = input.text.toString().trim()
            val apiService = ApiConfig.getApiService()
            lifecycleScope.launch {
                try {
                    val response = apiService.updateName(token, newName)
                    showToast(response.message.toString())
                    viewModel.updateName(newName)
                    binding.tvNameProfile.text = newName
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    showToast(errorResponse.message.toString())
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showToast(message: String) {
        makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val BR = "Bearer "
        const val EK = "EDIT_KEY"
    }
}