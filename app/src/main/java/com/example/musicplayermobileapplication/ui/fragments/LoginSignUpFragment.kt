package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import com.example.musicplayermobileapplication.databinding.FragmentLoginSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSignUpFragment : Fragment() {
    private lateinit var binding: FragmentLoginSignupBinding
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

        binding.run {
            mbSignUp.setOnClickListener {
                npAge.minValue = 0
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
            npAge.isInvisible = !registering
        }
    }

    private fun onSubmitHandler() {

    }

    private fun validateData() {
        binding.run {
            if(etUsername.text != null && etPassword.text != null) {

            }
        }
    }
}