package com.example.musicplayermobileapplication.core.modals

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.musicplayermobileapplication.databinding.LayoutAlertViewBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Modals @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showConfirmationDialog(header: String, body: String, onConfirm: () -> Unit) {
        val dialogBox = AlertDialog.Builder(context).create()
        val alertView = LayoutAlertViewBinding.inflate(LayoutInflater.from(context))
        alertView.run {
            tvHeader.text = header
            tvBody.text = body
            btnPositive.setOnClickListener { onConfirm(); dialogBox.dismiss() }
            btnNegative.setOnClickListener { dialogBox.dismiss() }
        }
        dialogBox.run {
            setView(alertView.root)
            show()
        }
    }
}