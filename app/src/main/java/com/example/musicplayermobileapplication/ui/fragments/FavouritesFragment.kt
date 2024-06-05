package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentFavouritesBinding
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouritesAdapter: SongAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        favouritesAdapter = SongAdapter(emptyList())

        favouritesAdapter.listener = object : SongAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(song.id!!)
                )
            }
        }
        binding.rvFavourites.adapter = favouritesAdapter
        binding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
    }

}