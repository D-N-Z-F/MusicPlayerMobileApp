package com.example.musicplayermobileapplication.core.modals

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.format
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.databinding.LayoutAlertViewBinding
import com.example.musicplayermobileapplication.databinding.LayoutFilterViewBinding
import com.example.musicplayermobileapplication.databinding.LayoutPlaylistsViewBinding
import com.example.musicplayermobileapplication.ui.adapter.PlaylistAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// This class acts as a base for all modals used throughout the app, such as the
// Confirmation Dialog, Filter Dialog, and the Playlists Dialog.
class Modals @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private lateinit var playlistAdapter: PlaylistAdapter
    fun showConfirmationDialog(
        header: String, body: String, onConfirm: () -> Unit
    ) {
        val dialogView = LayoutAlertViewBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).setView(dialogView.root).create()
        dialogView.run {
            tvHeader.text = header
            tvBody.text = body
            btnPositive.setOnClickListener { onConfirm(); dialog.dismiss() }
            btnNegative.setOnClickListener { dialog.dismiss() }
        }
        setupDialog(dialog)
    }
    fun showFilterDialog(
        selectedGenre: Genres? ,onConfirm: (Genres?) -> Unit
    ) {
        val dialogView = LayoutFilterViewBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).setView(dialogView.root).create()
        var newSelection: Genres? = null
        dialogView.run {
            setRadioValues(this, selectedGenre)
            rgGenres.setOnCheckedChangeListener { _, id ->
                newSelection = when(id) {
                    R.id.rbRnB -> Genres.RHYTHM_AND_BLUES
                    R.id.rbClassical -> Genres.CLASSICAL
                    R.id.rbJazz -> Genres.JAZZ
                    R.id.rbKorean -> Genres.KOREAN
                    R.id.rbChinese -> Genres.CHINESE
                    R.id.rbEnglish -> Genres.ENGLISH
                    R.id.rbPopular -> Genres.POPULAR
                    else -> null
                }
            }
            btnPositive.setOnClickListener { onConfirm(newSelection); dialog.dismiss() }
            btnNegative.setOnClickListener { dialog.dismiss() }
        }
        setupDialog(dialog)
    }
    fun showPlaylistsDialog(
        playlists: List<Playlist>, addRemoveFromPlaylist: (Playlist) -> Unit
    ) {
        val dialogView = LayoutPlaylistsViewBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).setView(dialogView.root).create()
        dialogView.run {
            playlistAdapter = PlaylistAdapter(playlists)
            playlistAdapter.listener = object: PlaylistAdapter.Listener {
                override fun onClick(playlist: Playlist) {
                    addRemoveFromPlaylist(playlist); dialog.dismiss()
                }
            }
            rvPlaylists.adapter = playlistAdapter
            rvPlaylists.layoutManager = LinearLayoutManager(context)
            rvPlaylists.isGone = playlists.isEmpty()
            tvEmptyPlaylists.isGone= playlists.isNotEmpty()
        }
        setupDialog(dialog)
    }
    private fun setupDialog(dialog: AlertDialog) {
        dialog.run {
            setOnShowListener {
                window?.setBackgroundDrawable(
                    ContextCompat.getDrawable(context, R.drawable.dialog_backdrop)
                )
            }
            show()
        }
    }
    private fun setRadioValues(binding: LayoutFilterViewBinding, selectedGenre: Genres?) {
        binding.run {
            var count = 1
            for(genre in Genres.entries) {
                val radioBtn = rgGenres.getChildAt(count++) as RadioButton
                radioBtn.text = genre.format()
                if(genre == selectedGenre) { radioBtn.isChecked = true }
            }
        }
    }
}