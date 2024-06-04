package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentSearchBinding
import com.example.musicplayermobileapplication.ui.adapter.SongsAdapter
import com.example.musicplayermobileapplication.ui.viewmodels.SongsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SongsAdapter
    private val viewModel: SongsViewModel by viewModels()

    private var songList: List<Song> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getAllSongs().let {
                songList = it
                adapter.setSongs(it)
            }
        }

        binding.run {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filteredWords = searchWord(newText, songList)
                    if (filteredWords.isNotEmpty()) adapter.setSongs(filteredWords)
                    return true
                }
            })
        }
    }

    fun searchWord(query: String?, wordList: List<Song>): List<Song> {
        return if (query.isNullOrBlank()) {
            wordList
        } else {
            wordList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}