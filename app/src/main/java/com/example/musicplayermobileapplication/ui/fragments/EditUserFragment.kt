package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.PublicUserDetails
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.databinding.FragmentEditUserBinding
import com.example.musicplayermobileapplication.ui.viewmodels.EditUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditUserFragment : Fragment() {
    private lateinit var binding: FragmentEditUserBinding
    private val viewModel: EditUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Since this is only to update user, we have to set up their details, and they
        // can see their original details.
        binding.run {
            npAge.minValue = 1
            npAge.maxValue = 150
            mbSignUp.setOnClickListener { signUpHandler() }
        }
        viewModel.run {
            lifecycleScope.launch {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { findNavController().popBackStack() }
            }
            lifecycleScope.launch {
                getUserById().collect {
                    it?.let {
                        setupDetails(it)
                        setupOriginal(it)
                    }
                }
            }
        }
    }
    private fun setupDetails(user: PublicUserDetails) {
        binding.run {
            etUsername.setText(user.username)
            spGender.setSelection(
                when(user.gender) {
                    Genders.MALE -> 1
                    Genders.FEMALE -> 2
                    Genders.NON_BINARY -> 3
                    else -> 0
                }
            )
            npAge.value = user.age
            etBio.setText(user.bio)
        }
    }
    private fun signUpHandler() {
        binding.run {
            val gender = when(spGender.selectedItem) {
                "Male" -> Genders.MALE
                "Female" -> Genders.FEMALE
                "Non-Binary" -> Genders.NON_BINARY
                else -> Genders.UNDISCLOSED
            }
            viewModel.validateUpdateDetails(
                etUsername.text.toString(),
                npAge.value,
                gender,
                etBio.text.toString(),
                etPass.text.toString(),
                etPass2.text.toString()
            )
        }
    }
}