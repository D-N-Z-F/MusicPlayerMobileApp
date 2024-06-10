package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentViewPlaylistBinding
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.ViewPlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ViewPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentViewPlaylistBinding
    private lateinit var songAdapter: SongAdapter
    private val viewModel: ViewPlaylistViewModel by viewModels()
    private val args: ViewPlaylistFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPlaylistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.run {
            lifecycleScope.launch {
                getPlaylistById(args.id).collect {
                    it?.let {
                        setupDetails(it)
                        songAdapter.setupSongs(it.songs)
                    }
                }
            }
        }
    }
    private fun setupDetails(playlist: Playlist) {
        binding.run {
            tvTitle.text = playlist.title
            tvDesc.text = playlist.desc
            val noOfSongs = "${playlist.songs.size} songs"
            tvNoOfSongs.text= noOfSongs
            val image = File(playlist.imagePath)
            if(image.exists()) {
                Glide.with(requireContext())
                    .load(image)
                    .into(ivImage)
            }
            ivSettings.setOnClickListener {
                findNavController().navigate(
                    ViewPlaylistFragmentDirections
                        .viewPlaylistToAddEditPlaylist(playlist.id!!, "edit")
                )
            }
        }
    }
    private fun setupAdapter() {
        songAdapter = SongAdapter(emptyList(), 2)
        songAdapter.listener = object : SongAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ViewPlaylistFragmentDirections.viewPlaylistToViewSong(song.id!!)
                )
            }
        }
        binding.rvFavourites.adapter = songAdapter
        binding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
    }
}