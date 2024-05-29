package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicplayermobileapplication.databinding.FragmentLoginSignupBinding
import com.example.musicplayermobileapplication.ui.viewmodels.LoginSignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginSignUpFragment : Fragment() {
    private lateinit var binding: FragmentLoginSignupBinding
    private val viewModel: LoginSignUpViewModel by viewModels()
    private var isRegister: Boolean = false;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLoginStatus()
        lifecycleScope.launch {
            viewModel.run {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { checkLoginStatus() }
            }
        }
        binding.viewModel = viewModel
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            mbSignUp.setOnClickListener {
                npAge.minValue = 1
                npAge.maxValue = 150
                toggleDetails(true)
            }
            tvLogIn.setOnClickListener { toggleDetails(false) }
            tvBack.setOnClickListener { toggleDetails(registering = false, clickBack = true) }
            mbSubmit.setOnClickListener { onSubmitHandler() }
        }
    }
    private fun toggleDetails(registering: Boolean, clickBack: Boolean = false) {
        binding.run {
            isRegister = registering
            mbSignUp.isInvisible = !clickBack
            tvLogIn.isInvisible = !clickBack
            llDetails.isInvisible = clickBack
            spGender.isInvisible = !registering
            spGender.setSelection(0)
            npAge.isInvisible = !registering
            npAge.value = 1
            viewModel?.username?.postValue("")
            viewModel?.password?.postValue("")
        }
    }
    private fun onSubmitHandler() {
        viewModel.run {
            if(isRegister) {
                validateRegister(
                    binding.spGender.selectedItem.toString(),
                    binding.npAge.value
                )
            } else { validateLogin() }
        }
    }
    private fun checkLoginStatus() {
        if(viewModel.isLoggedIn()) {
            findNavController().navigate(
                LoginSignUpFragmentDirections.loginToContainer()
            )
        }
    }
}