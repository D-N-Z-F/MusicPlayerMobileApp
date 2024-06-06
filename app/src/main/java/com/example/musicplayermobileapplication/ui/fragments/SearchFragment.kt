package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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
    private var songList: List<Song> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        lifecycleScope.launch {
            viewModel.getAllSongs().collect {
                songList = it
                songAdapter.setupSongs(it)
            }
        }
        binding.run {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false
                override fun onQueryTextChange(query: String?): Boolean {
                    val filteredWords = viewModel.searchWord(query, songList)
                    songAdapter.setupSongs(filteredWords)
                    return true
                }
            })
        }
    }
    private fun setupAdapter() {
        songAdapter = SongAdapter(emptyList())
        songAdapter.listener = object: SongAdapter.Listener {
            override fun onClick(song: Song) { Log.d("debugging", song.id!!.toString()) }
        }
        binding.run {
            rvSearch.adapter = songAdapter
            rvSearch.layoutManager= GridLayoutManager(requireContext(),2)
        }
    }
}