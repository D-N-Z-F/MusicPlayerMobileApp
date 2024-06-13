package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicplayermobileapplication.core.modals.Modals
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentSearchBinding
import com.example.musicplayermobileapplication.ui.adapter.SongAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var songAdapter: SongAdapter
    private val viewModel: SharedViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    private lateinit var modal: Modals
    private var songList: List<Song> = emptyList()
    private var givenQuery: String? = null
    private var selectedGenre: Genres? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is the Search page, where users can filter or search for their specific songs
        // or genres. We need to fetch all songs, and then based on query input or filter
        // selected we then show the desired results, while also allowing the user to click.
        setupAdapter()
        lifecycleScope.launch {
            viewModel.getAllSongs().collect {
                binding.rvSearch.isGone = it.isEmpty()
                binding.llEmptySongs.isGone = it.isNotEmpty()
                songList = it
                songAdapter.setupSongs(it)
            }
        }
        binding.run {
            modal = Modals(requireContext())
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                override fun onQueryTextChange(query: String?): Boolean {
                    givenQuery = query
                    performSearch()
                    return true
                }
            })
            ivFilter.setOnClickListener {
                modal.showFilterDialog(selectedGenre) { genre ->
                    selectedGenre = genre
                    performSearch()
                }
            }
        }
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.searchView.setQuery(null, false)
        givenQuery = null
        selectedGenre = null
    }
    private fun performSearch() {
        binding.run {
            val filteredWords = viewModel.searchWord(givenQuery, selectedGenre, songList)
            songAdapter.setupSongs(filteredWords)
            rvSearch.isGone = filteredWords.isEmpty()
            llEmptySongs.isGone = filteredWords.isNotEmpty()
        }
    }
    private fun setupAdapter() {
        songAdapter = SongAdapter(emptyList(), 1)
        songAdapter.listener = object: SongAdapter.Listener {
            override fun onClick(song: Song) {
                findNavController().navigate(
                    ContainerFragmentDirections.containerToViewSong(song.id!!, -1)
                )
            }
        }
        binding.run {
            rvSearch.adapter = songAdapter
            rvSearch.layoutManager= GridLayoutManager(requireContext(),2)
        }
    }
}