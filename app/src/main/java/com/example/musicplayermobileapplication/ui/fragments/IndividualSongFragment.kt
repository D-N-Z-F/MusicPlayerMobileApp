package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentIndividualSongBinding
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.SelectedSongViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualSongFragment : Fragment() {
    private lateinit var binding: FragmentIndividualSongBinding
    private val selectedSongViewModel: SelectedSongViewModel by viewModels()

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

        selectedSongViewModel.song.observe(viewLifecycleOwner) { binding.song = it }
    }
}