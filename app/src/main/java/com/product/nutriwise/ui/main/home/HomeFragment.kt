package com.product.nutriwise.ui.main.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.main.home.recomendation.RecomendationActivity
import kotlin.properties.Delegates

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private var maxCalorie: Int? = null
    private var proCalorie: Int? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maxCalorie = 5000
        proCalorie = 2500

        binding.tvNumOfCal.setText("$proCalorie/$maxCalorie")
        binding.piCalorie.max = maxCalorie as Int
        binding.piCalorie.progress = proCalorie as Int
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}