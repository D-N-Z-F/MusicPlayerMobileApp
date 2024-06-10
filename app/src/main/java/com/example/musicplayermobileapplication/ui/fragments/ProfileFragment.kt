package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.format
import com.example.musicplayermobileapplication.data.model.PublicUserDetails
import com.example.musicplayermobileapplication.data.model.Statuses
import com.example.musicplayermobileapplication.databinding.FragmentProfileBinding
import com.example.musicplayermobileapplication.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: SharedViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            lifecycleScope.launch {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { checkLoginStatus() }
            }
            lifecycleScope.launch {
                getUserById().collect {
                    it?.let { setupUserDetails(it) }
                }
            }
            binding.run {
                ivEdit.setOnClickListener {
                    findNavController().navigate(ContainerFragmentDirections.containerToEditUser())
                }
                mbLogout.setOnClickListener { logout() }
            }
        }
    }
    private fun setupUserDetails(data: PublicUserDetails) {
        binding.run {
            data.let {
                val image = File(it.imagePath)
                if(image.exists()) {
                    Glide.with(this.root)
                        .load(image)
                        .into(ivImage)
                }
                tvUsername.text = it.username
                tvBio.text = it.bio.ifBlank { getString(R.string.bio_placeholder) }
                tvAge.text = getString(R.string.age, it.age)
                tvGender.text = getString(R.string.gender, it.gender.format())
                tvJoinedAt.text = getString(
                    R.string.joined_at, it.joinedAt.toString().slice(0 ..< 10)
                )
                status.tvStatus.text = it.status.format()
                status.rlStatus.setBackgroundResource(
                    if(it.status == Statuses.PREMIUM) R.drawable.gold_bg else R.drawable.silver_bg
                )
            }
        }
    }
    private fun checkLoginStatus() {
        if(
            !viewModel.isLoggedIn()
            && findNavController().currentDestination?.id == R.id.containerFragment
            ) {
            findNavController().navigate(ContainerFragmentDirections.containerToLogin())
        }
    }
}