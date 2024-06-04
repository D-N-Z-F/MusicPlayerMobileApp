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
import com.example.musicplayermobileapplication.ui.adapter.SongsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouritesAdapter: SongsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        favouritesAdapter = SongsAdapter(emptyList())

        favouritesAdapter.listener = object : SongsAdapter.Listener {
            override fun onClick(songs: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(songs.id!!)
                )
            }
        }
        binding.rvFavourites.adapter = favouritesAdapter
        binding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
    }

}