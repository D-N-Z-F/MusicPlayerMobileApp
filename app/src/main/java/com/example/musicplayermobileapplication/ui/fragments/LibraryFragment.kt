package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentLibraryBinding
import com.example.musicplayermobileapplication.ui.adapter.FavouriteAdapter
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel: SharedViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Similar to the Home page, we collect two things, the user playlists and the user
        // favourites, then we allow the user to click on each item individually, or to add
        // a new playlist.
        setupAdapter()
        viewModel.run {
            lifecycleScope.launch {
                getAllUserPlaylists().collect {
                    binding.rvPlaylists.isGone = it.isEmpty()
                    binding.tvEmptyPlaylists.isGone = it.isNotEmpty()
                    playlistAdapter.setupPlaylists(it)
                }
            }
            lifecycleScope.launch {
                getAllUserFavourites().collect {
                    it?.let {
                        val songs = it.favourites
                        binding.rvFavourites.isGone = songs.isEmpty()
                        binding.tvEmptyFavourites.isGone = songs.isNotEmpty()
                        favouriteAdapter.setupSongs(songs)
                    }
                }
            }
        }
        binding.fabAddPlaylist.setOnClickListener {
            findNavController().navigate(
                ContainerFragmentDirections.containerToAddEditPlaylist(0, "add")
            )
        }
    }
    private fun setupAdapter() {
        playlistAdapter = PlaylistAdapter(emptyList())
        playlistAdapter.listener = object: PlaylistAdapter.Listener {
            override fun onClick(playlist: Playlist) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewPlaylist(playlist.id!!)
                )
            }
        }
        favouriteAdapter = FavouriteAdapter(emptyList(), 2)
        favouriteAdapter.listener = object: FavouriteAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewSong(song.id!!, -1)
                )
            }
        }
        binding.run {
            rvPlaylists.adapter = playlistAdapter
            rvPlaylists.layoutManager = LinearLayoutManager(requireContext())
            rvFavourites.adapter = favouriteAdapter
            rvFavourites.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}