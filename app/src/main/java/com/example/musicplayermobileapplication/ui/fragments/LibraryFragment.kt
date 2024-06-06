package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.databinding.FragmentUserLibraryBinding
import com.example.musicplayermobileapplication.ui.adapter.LibraryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentUserLibraryBinding
    private lateinit var libraryAdapter: LibraryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        libraryAdapter = LibraryAdapter(emptyList())

        libraryAdapter.listener = object : LibraryAdapter.Listener {
            override fun onClick(playlist: Playlist) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToLibrary(playlist.id!!)
                )
            }
        }
        binding.rvLibrary.adapter = libraryAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
    }
}