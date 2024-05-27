package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.databinding.FragmentContainerBinding
import com.example.musicplayermobileapplication.databinding.LayoutTabItemBinding
import com.example.musicplayermobileapplication.ui.adapter.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpTabs.adapter = TabAdapter(
            this@ContainerFragment,
            listOf(
                HomeFragment(), SearchFragment(), LibraryFragment(), ProfileFragment()
            )
        )

        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_search,
            R.drawable.ic_playlist, R.drawable.ic_profile)

        val tabTexts = listOf("Home", "Search", "Playlist", "Profile")

        TabLayoutMediator(binding.tlTabs, binding.vpTabs) { tab, position ->
            val tabBinding = LayoutTabItemBinding.inflate(LayoutInflater.from(requireContext()))
            tabBinding.tabIcon.setImageResource(tabIcons[position])
            tabBinding.tabText.text = tabTexts[position]
            tab.customView = tabBinding.root
        }.attach()
    }
}