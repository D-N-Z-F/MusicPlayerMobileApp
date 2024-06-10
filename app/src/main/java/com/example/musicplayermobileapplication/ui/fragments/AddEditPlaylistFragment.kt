package com.example.musicplayermobileapplication.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.musicplayermobileapplication.core.modals.Modals
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
    private lateinit var modal: Modals
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
                finish.collect {
                    when(it) {
                        0.toByte() -> findNavController().popBackStack()
                        1.toByte() -> findNavController().navigate(
                            AddEditPlaylistFragmentDirections.addEditPlaylistToContainer()
                        )
                        else -> throw IllegalArgumentException("Illegal Argument Found!")
                    }
                }
            }
            if(args.type == "edit" && args.id != 0) {
                lifecycleScope.launch {
                    getPlaylistById(args.id).collect {
                        it?.let {
                            setupDetails(it)
                            setupOriginal(it)
                        }
                    }
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
        modal = Modals(requireContext())
        binding.run {
            tvAddEdit.text = getString(R.string.add_edit_playlist, args.type.capitalize())
            mbAddEdit.text = getString(if(args.type == "add") R.string.add else R.string.update)
        }
    }
    private fun setupDetails(playlist: Playlist) {
        binding.run {
            etTitle.setText(playlist.title)
            etDesc.setText(playlist.desc)
            mbDelete.visibility = View.VISIBLE
            mbDelete.setOnClickListener {
                modal.showConfirmationDialog(
                    "Are You Sure?",
                    " Do you want to delete this playlist?"
                ) { viewModel.deletePlaylist() }
            }
        }
    }
}