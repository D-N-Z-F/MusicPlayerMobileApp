package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicplayermobileapplication.databinding.FragmentIndividualSongBinding
import com.example.musicplayermobileapplication.ui.viewmodels.SongsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualSongFragment : Fragment() {
    private lateinit var binding: FragmentIndividualSongBinding
    private val viewModel: SongsViewModel by viewModels()

    private var selectedSongId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentIndividualSongBinding.inflate(layoutInflater, container, false)

        selectedSongId = requireArguments().getInt("id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.song.observe(viewLifecycleOwner) { binding.song = it }
    }
}