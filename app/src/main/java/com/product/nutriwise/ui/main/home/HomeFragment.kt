package com.product.nutriwise.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.product.nutriwise.R
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.ui.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var maxCalorie = 5000
    private var proCalorie = 2500

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HomeViewModel::class.java)

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            binding.tvName.text = "Hi, " + session.name
            Log.d("HomeFragment", "onViewCreated: ${session.token}")
        }

        binding.tvNumOfCal.text = "$proCalorie/$maxCalorie"
        binding.piCalorie.max = maxCalorie
        binding.piCalorie.progress = proCalorie
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
