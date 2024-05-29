package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayermobileapplication.databinding.FragmentIndividualSongBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualSongFragment : Fragment() {
    private lateinit var binding: FragmentIndividualSongBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentIndividualSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}