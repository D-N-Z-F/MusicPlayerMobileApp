package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.musicplayermobileapplication.databinding.FragmentHomeBinding
import com.example.musicplayermobileapplication.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter(emptyList())

        binding.vpPopularSongs.let { pager ->
            pager.adapter = homeAdapter
            pager.offscreenPageLimit - 3
            val pageTransformer = CompositePageTransformer()
            pageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.35f + 0.35f * r
                page.scaleX = 0.65f + 0.25f * r
            }

            pager.setPageTransformer(pageTransformer)

            lifecycleScope.launch {
                var position = 0
                while (true) {
                    pager.setCurrentItem(position, true)
                    position++
                    if (position > 5) position = 0
                    delay(2000)
                }
            }
        }
    }
}