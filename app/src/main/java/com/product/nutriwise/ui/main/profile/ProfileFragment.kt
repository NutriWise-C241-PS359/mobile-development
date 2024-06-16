package com.product.nutriwise.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.product.nutriwise.R
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.databinding.FragmentProfileBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.webView.WebViewActivity

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(ProfileViewModel::class.java)

        viewModel.getSession().observe(viewLifecycleOwner){
            binding.tvNameProfile.setText(it.name)
        }
        viewModel.getProfile().observe(viewLifecycleOwner){
            binding.apply {
                tvTbProfile2.setText(it.tinggibadan.toString())
                tvBbProfile2.setText(it.beratbadan.toString())
                tvGenderProfile2.setText(it.gender.toString())
                tvActivityProfile2.setText(it.aktivitas.toString())
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearProfile()
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.btnWebview.setOnClickListener {
            startActivity((Intent(requireContext(),WebViewActivity::class.java)))
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
}