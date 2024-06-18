package com.product.nutriwise.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.product.nutriwise.R
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.home.recomendation.RecomendationActivity
import com.product.nutriwise.ui.signup.SignupActivity
import com.product.nutriwise.ui.signup.inputProfile.InputProfileActivity
import com.product.nutriwise.utils.Utils
import kotlin.math.max

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var maxCalorie: Double? = null
    private var proCalorie: Double? = null
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
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]

        binding.apply {

            viewModel.getProfile().observe(viewLifecycleOwner) {
                if (it.beratbadan == 0.0) {
                    showErrorDialog("Belum isi data profile")
                }
                val bmi = Utils.calculateBMI(it.beratbadan, it.tinggibadan)
                val categoryBMI = Utils.getBMICategory(bmi, it.gender)

                tvTitleBmi2.text = String.format(" %.2f ", bmi)
                tvTitleBmi3.text = "($categoryBMI)"
            }

            viewModel.getCalorie().observe(viewLifecycleOwner) {
                maxCalorie = it.dailyCalories
                proCalorie =
                    (it.addCalorieB ?: 0.0) + (it.addCalorieL ?: 0.0) + (it.addCalorieD ?: 0.0)
                tvNumOfCal.text = String.format("%.2f/${maxCalorie?.toInt()}", proCalorie)
                piCalorie.max = maxCalorie?.toInt() ?: 0
                piCalorie.progress = proCalorie?.toInt() ?: 0

                //Breakfast
                needcalBreakfast2.text = String.format(
                    " %.2f kkal",
                    max(0.0, (it.caloriesB ?: 0.0) - (it.addCalorieB ?: 0.0))
                )
                fillcalBreakfast2.text = String.format(" %.2f kkal", it.addCalorieB ?: 0.0)
                addfoodBreakfast.setOnClickListener { _ ->
                    val tmp = it.addCalorieB ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 1)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesB)
                    intent.putExtra("car", it.carbohydratesB)
                    intent.putExtra("fat", it.fatsB)
                    intent.putExtra("pro", it.proteinsB)
                    startActivity(intent)
                }
                //Lunch
                needcalLunch2.text = String.format(
                    " %.2f kkal",
                    max(0.0, (it.caloriesL ?: 0.0) - (it.addCalorieL ?: 0.0))
                )
                fillcalLunch2.text = String.format(" %.2f kkal", it.addCalorieL ?: 0.0)
                addfoodLunch.setOnClickListener { _ ->
                    val tmp = it.addCalorieL ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 2)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesL)
                    intent.putExtra("car", it.carbohydratesL)
                    intent.putExtra("fat", it.fatsL)
                    intent.putExtra("pro", it.proteinsL)
                    startActivity(intent)
                }

                //Diner
                needcalDinner2.text = String.format(
                    " %.2f kkal",
                    max(0.0, (it.caloriesD ?: 0.0) - (it.addCalorieD ?: 0.0))
                )
                fillcalDinner2.text = String.format(" %.2f kkal", it.addCalorieD ?: 0.0)
                addfoodDinner.setOnClickListener { _ ->
                    val tmp = it.addCalorieD ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 3)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesD)
                    intent.putExtra("car", it.carbohydratesD)
                    intent.putExtra("fat", it.fatsD)
                    intent.putExtra("pro", it.proteinsD)
                    startActivity(intent)
                }
            }

            viewModel.getSession().observe(viewLifecycleOwner) {
                tvName.text = "Hi, ${it.name}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(requireContext(), InputProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(SignupActivity.EK, 0)
                startActivity(intent)
            }
            .setCancelable(false)
            .show()
    }
}
