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
import com.example.musicplayermobileapplication.databinding.FragmentHomeBinding
import com.example.musicplayermobileapplication.ui.adapter.FavouriteAdapter
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var songAdapter: SongAdapter
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel: SharedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Firstly, we set up the adapters, and then we collect 3 things, songs, user playlists,
        // and user favourites.
        setupAdapters()
        viewModel.run {
            lifecycleScope.launch {
                getAllSongs().collect {
                    binding.rvPopularSongs.isGone = it.isEmpty()
                    binding.tvEmptySongs.isGone = it.isNotEmpty()
                    songAdapter.setupSongs(it)
                }
            }
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
    }
    private fun setupAdapters() {
        songAdapter = SongAdapter(emptyList(), 1)
        songAdapter.listener = object: SongAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewSong(song.id!!, -1)
                )
            }
        }
        playlistAdapter = PlaylistAdapter(emptyList())
        playlistAdapter.listener = object: PlaylistAdapter.Listener {
            override fun onClick(playlist: Playlist) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewPlaylist(playlist.id!!)
                )
            }
        }
        favouriteAdapter = FavouriteAdapter(emptyList(), 1)
        favouriteAdapter.listener = object: FavouriteAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewSong(song.id!!, -1)
                )
            }
        }
        binding.run {
            rvPopularSongs.adapter = songAdapter
            rvPopularSongs.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            rvPlaylists.adapter = playlistAdapter
            rvPlaylists.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            rvFavourites.adapter = favouriteAdapter
            rvFavourites.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
        }
    }
}