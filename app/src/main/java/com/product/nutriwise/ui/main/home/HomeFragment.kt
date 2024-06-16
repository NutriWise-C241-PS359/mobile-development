package com.product.nutriwise.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.home.recomendation.RecomendationActivity
import com.product.nutriwise.utils.Utils

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var maxCalorie: Int = 0
    private var proCalorie: Int = 0
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
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HomeViewModel::class.java)

        binding.apply {

            viewModel.getProfile().observe(viewLifecycleOwner){
                val bmi = Utils.calculateBMI(it.beratbadan.toDouble(),it.tinggibadan.toDouble())
                val categoryBMI = Utils.getBMICategory(bmi, it.gender)

                tvTitleBmi2.text = String.format(" %.2f ", bmi)
                tvTitleBmi3.setText("($categoryBMI)")
            }

            maxCalorie = 5000
            proCalorie = 2500

            tvNumOfCal.text = "$proCalorie/$maxCalorie"
            piCalorie.max = maxCalorie
            piCalorie.progress = proCalorie

            viewModel.getSession().observe(viewLifecycleOwner) {
                tvName.text = "Hi, ${it.name}"
            }

            addfoodBreakfast.setOnClickListener {
                val addBreakfast = Intent(activity, RecomendationActivity::class.java)
                activity?.startActivity(addBreakfast)
            }

            addfoodLunch.setOnClickListener {
                val addLunch = Intent(activity, RecomendationActivity::class.java)
                activity?.startActivity(addLunch)
            }

            addfoodDinner.setOnClickListener {
                val addDinner = Intent(activity, RecomendationActivity::class.java)
                activity?.startActivity(addDinner)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
