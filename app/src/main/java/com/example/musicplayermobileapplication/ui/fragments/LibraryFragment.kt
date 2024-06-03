package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentLibraryBinding
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var libraryAdapter: PlaylistAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        libraryAdapter = PlaylistAdapter(emptyList())

        libraryAdapter.listener = object : PlaylistAdapter.Listener {
            override fun onClick(songs: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(songs.id!!)
                )
            }
        }
        binding.rvLibrary.adapter = libraryAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
    }
}