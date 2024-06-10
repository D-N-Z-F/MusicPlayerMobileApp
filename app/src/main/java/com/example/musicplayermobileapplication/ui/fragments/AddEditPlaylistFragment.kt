package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.capitalize
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.databinding.FragmentAddEditPlaylistBinding
import com.example.musicplayermobileapplication.ui.viewmodels.AddEditPlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class AddEditPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddEditPlaylistBinding
    private val viewModel: AddEditPlaylistViewModel by viewModels()
    private val args: AddEditPlaylistFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDetails()
        viewModel.run {
            lifecycleScope.launch {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { findNavController().popBackStack() }
            }
            if(args.type == "edit" && args.id != 0) {
                lifecycleScope.launch {
                    getPlaylistById().collect { it?.let { setupOriginal(it) } }
                }
            }
        }
        binding.run {
            mbAddEdit.setOnClickListener {
                viewModel.validatePlaylist(
                    args.type, etTitle.text.toString(), etDesc.text.toString()
                )
            }
        }
    }
    private fun setupDetails() {
        binding.run {
            tvAddEdit.text = getString(R.string.add_edit_playlist, args.type.capitalize())
            mbAddEdit.text = getString(if(args.type == "add") R.string.add else R.string.update)
        }
    }
}