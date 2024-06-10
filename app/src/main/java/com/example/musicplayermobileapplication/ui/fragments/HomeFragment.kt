package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.core.utils.setDataVisibility
import com.example.musicplayermobileapplication.core.utils.setPlaceholderVisibility
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
        setupAdapters()
        viewModel.run {
//            addEssentials() // Temporary
            lifecycleScope.launch {
                getAllSongs().collect {
                    binding.rvPopularSongs.visibility = setDataVisibility(it)
                    binding.tvEmptySongs.visibility = setPlaceholderVisibility(it)
                    songAdapter.setupSongs(it)
                }
            }
            lifecycleScope.launch {
                getAllUserPlaylists().collect {
                    binding.rvPlaylists.visibility = setDataVisibility(it)
                    binding.tvEmptyPlaylists.visibility = setPlaceholderVisibility(it)
                    playlistAdapter.setupPlaylists(it)
                }
            }
            lifecycleScope.launch {
                getAllUserFavourites().collect {
                    it?.let {
                        val songs = it.favourites
                        binding.rvFavourites.visibility = setDataVisibility(songs)
                        binding.tvEmptyFavourites.visibility = setPlaceholderVisibility(songs)
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
                    ContainerFragmentDirections.containerToViewSong(song.id!!)
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
                    ContainerFragmentDirections.containerToViewSong(song.id!!)
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