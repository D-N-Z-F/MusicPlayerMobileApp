package com.example.musicplayermobileapplication.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.modals.Modals
import com.example.musicplayermobileapplication.core.utils.format
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.FragmentViewSongBinding
import com.example.musicplayermobileapplication.ui.viewmodels.ViewSongViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ViewSongFragment : Fragment() {
    private lateinit var binding: FragmentViewSongBinding
    private val viewModel: ViewSongViewModel by viewModels()
    private val args: ViewSongFragmentArgs by navArgs()
    private lateinit var modal: Modals

    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object: Runnable {
        override fun run() {
            mediaPlayer.let {
                if(it.isPlaying) {
                    binding.durationStart.text = it.currentPosition.format()
                    binding.progressBar.progress =
                        (it.currentPosition.toDouble() / it.duration * 100).toInt()
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }
    private lateinit var btnPlayPause: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modal = Modals(requireContext())
        btnPlayPause = binding.btnPlayPause
        viewModel.run {
            lifecycleScope.launch {
                getSongById(args.id).collect {
                    it?.let {
                        setSong(it)
                        setupDetails(it)
                        val audioFile = File(it.filePath)
                        if(audioFile.exists()) { setupAudioPlayer(Uri.fromFile(audioFile)) }
                    }
                }
            }
            lifecycleScope.launch {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                getAllUserFavourites().collect {
                    it?.let { setFavourite(it) }
                    setLikedStatus()
                }
            }
            binding.ivPlaylist.setOnClickListener {
                lifecycleScope.launch {
                    getAllUserPlaylists().collect {
                        Log.d("debugging", it.toString())
                        modal.showPlaylistsDialog(it) { playlist ->
                            viewModel.addRemoveFromPlaylist(playlist)
                        }
                    }
                }
            }
        }
    }
    private fun setupAudioPlayer(audioUri: Uri) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(requireContext(), audioUri)
            setOnPreparedListener { setupPlayback() }
            setOnCompletionListener { resetAudio() }
            prepareAsync()
        }
    }
    private fun setupPlayback() {
        binding.run {
            durationEnd.text = mediaPlayer.duration.format()
            (btnPlayPause).setOnClickListener {
                if(!mediaPlayer.isPlaying) { playAudio() }
                else if(mediaPlayer.isPlaying) { pauseAudio() }
            }
            btnPlayPause.setOnLongClickListener { resetAudio(); true }
        }
    }
    private fun setLikedStatus() {
        val color = ContextCompat.getColor(
            requireContext(),
            if(viewModel.isFavourited()) R.color.red else R.color.white
        )
        binding.ivFav.setColorFilter(color)
    }
    private fun setupDetails(song: Song) {
        binding.run {
            setLikedStatus()
            tvTitle.text = song.title
            tvArtist.text = song.artist
            val image = File(song.imagePath)
            if(image.exists()) {
                Glide.with(requireContext())
                    .load(image)
                    .into(ivImage)
            }
            ivFav.setOnClickListener { viewModel.addRemoveFavourite(song) }
        }
    }
    private fun playAudio() {
        mediaPlayer.start()
        handler.post(runnable)
        btnPlayPause.setImageResource(R.drawable.ic_pause)
    }
    private fun pauseAudio() {
        mediaPlayer.pause()
        handler.removeCallbacks(runnable)
        btnPlayPause.setImageResource(R.drawable.ic_play)
    }
    private fun resetAudio() {
        if(mediaPlayer.isPlaying) { mediaPlayer.pause() }
        mediaPlayer.seekTo(0)
        handler.removeCallbacks(runnable)
        btnPlayPause.setImageResource(R.drawable.ic_play)
        binding.run {
            durationStart.text = 0.format()
            progressBar.progress = 0
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        mediaPlayer.release()
    }
}