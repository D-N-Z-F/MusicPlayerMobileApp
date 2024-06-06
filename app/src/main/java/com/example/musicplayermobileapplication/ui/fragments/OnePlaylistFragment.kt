package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentOnePlaylistBinding
import com.example.musicplayermobileapplication.ui.adapter.HorizontalItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnePlaylistFragment : Fragment() {
    private lateinit var binding: FragmentOnePlaylistBinding
    private lateinit var horizontalItemAdapter: HorizontalItemAdapter

    private var selectedPlaylistId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnePlaylistBinding.inflate(layoutInflater, container, false)

        selectedPlaylistId = requireArguments().getInt("id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        horizontalItemAdapter = HorizontalItemAdapter(emptyList())

        horizontalItemAdapter.listener = object : HorizontalItemAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(song.id!!)
                )
            }
        }
        binding.rvFavourites.adapter = horizontalItemAdapter
        binding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
    }
}