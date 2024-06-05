package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.data.model.Favourite
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentHomeBinding
import com.example.musicplayermobileapplication.ui.adapter.FavouriteAdapter
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var songAdapter: SongAdapter
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel: HomeViewModel by viewModels()
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
            lifecycleScope.launch {
                getAllSongs().collect {
                    binding.rvPopularSongs.isInvisible = it.isEmpty()
                    binding.tvEmptySongs.isInvisible = it.isNotEmpty()
                    songAdapter.setupSongs(it)
                }
            }
            lifecycleScope.launch {
                getAllUserPlaylists().collect {
                    binding.rvPlaylists.isInvisible = it.isEmpty()
                    binding.tvEmptyPlaylists.isInvisible = it.isNotEmpty()
                    playlistAdapter.setupPlaylists(it)
                }
            }
            lifecycleScope.launch {
                getAllUserFavourites().collect {
                    it?.let {
                        val songs = it.favourites
                        binding.rvFavourites.isInvisible = songs.isEmpty()
                        binding.tvEmptyFavourites.isInvisible = songs.isNotEmpty()
                        favouriteAdapter.setupSongs(songs)
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        songAdapter = SongAdapter(emptyList())
        songAdapter.listener = object : SongAdapter.Listener {
            override fun onClick(song: Song) {
                Log.d("debugging", song.id!!.toString())
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(song.id)
                )
            }

        }
        playlistAdapter = PlaylistAdapter(emptyList())
        playlistAdapter.listener = object : PlaylistAdapter.Listener {
            override fun onClick(playlist: Playlist) {
                Log.d("debugging", playlist.id!!.toString())
                findNavController().navigate(
                    ContainerFragmentDirections.containerToLibrary(playlist.id)
                )
            }
        }
        favouriteAdapter = FavouriteAdapter(emptyList())
        favouriteAdapter.listener = object : FavouriteAdapter.Listener {
            override fun onClick(song: Song) {
                Log.d("debugging", song.id!!.toString())
                findNavController().navigate(
                    ContainerFragmentDirections.containerToSong(song.id)
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