package com.product.nutriwise.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
                if (it.beratbadan == 0.0){
                    showErrorDialog("Belum isi data profile")
                }
                val bmi = Utils.calculateBMI(it.beratbadan,it.tinggibadan)
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

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                val intent = Intent(requireContext(), InputProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(SignupActivity.EK, 0)
                startActivity(intent)
            }
            .setCancelable(false)
            .show()
    }
}
