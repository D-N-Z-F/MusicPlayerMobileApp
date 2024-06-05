package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentIndividualPlaylistBinding
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentIndividualPlaylistBinding
    private lateinit var playlistAdapter: SongAdapter

    private var selectedPlaylistId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndividualPlaylistBinding.inflate(layoutInflater, container, false)

        selectedPlaylistId = requireArguments().getInt("id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        playlistAdapter = SongAdapter(emptyList())

        playlistAdapter.listener = object : SongAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(song.id!!)
                )
            }
        }
        binding.rvFavourites.adapter = playlistAdapter
        binding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
    }
}