package com.example.musicplayermobileapplication.ui.fragments

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.isDownloadsDocument
import com.example.musicplayermobileapplication.core.utils.isExternalStorageDocument
import com.example.musicplayermobileapplication.core.utils.isMediaDocument
import com.example.musicplayermobileapplication.databinding.FragmentLoginSignupBinding
import com.example.musicplayermobileapplication.ui.viewmodels.LoginSignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.IllegalFormatException

@AndroidEntryPoint
class LoginSignUpFragment : Fragment() {
    private lateinit var binding: FragmentLoginSignupBinding
    private val viewModel: LoginSignUpViewModel by viewModels()
    private var isRegister: Boolean = false
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLoginStatus()
        setupLauncher()
        lifecycleScope.launch {
            viewModel.run {
                showToast.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { checkLoginStatus() }
            }
        }
        binding.viewModel = viewModel
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            mbSignUp.setOnClickListener {
                npAge.minValue = 1
                npAge.maxValue = 150
                toggleDetails(true)
            }
            tvLogIn.setOnClickListener { toggleDetails(false) }
            tvBack.setOnClickListener { toggleDetails(registering = false, clickBack = true) }
            mbImage.setOnClickListener { selectImageHandler() }
            mbSubmit.setOnClickListener { onSubmitHandler() }
        }
    }
    private fun toggleDetails(registering: Boolean, clickBack: Boolean = false) {
        binding.run {
            isRegister = registering
            mbSignUp.isGone = !clickBack
            tvLogIn.isGone = !clickBack
            llDetails.isGone = clickBack
            mbImage.isGone = !registering
            spGender.isGone = !registering
            spGender.setSelection(0)
            npAge.isGone = !registering
            npAge.value = 1
            viewModel?.username?.postValue("")
            viewModel?.password?.postValue("")
        }
    }
    private fun onSubmitHandler() {
        viewModel.run {
            if(isRegister) {
                validateRegister(
                    binding.spGender.selectedItem.toString(),
                    binding.npAge.value
                )
            } else { validateLogin() }
        }
    }
    private fun selectImageHandler() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { setType("image/*") }
        resultLauncher.launch(intent)
    }
    private fun setupLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d("debugging", result.toString())
            if(result.resultCode == Activity.RESULT_OK && result.data != null) {
                Log.d("uri_debugging102", result.data.toString())
                Log.d("uri_debugging103", result.data?.data.toString())
                result.data!!.data?.let { uri ->
                    val imagePath = getFilePathFromUri(uri)
                    imagePath?.let {
                        viewModel.imagePath.value = it
                        viewModel.showToast.postValue("Image uploaded successfully.")
                    }
                }
            }
        }
    }
    private fun getFilePathFromUri(uri: Uri): String? {
        if(DocumentsContract.isDocumentUri(requireContext(), uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val splitId = docId.split(":")
            when {
                uri.isExternalStorageDocument() -> {
                    if ("primary".equals(splitId[0], ignoreCase = true)) {
                        return "${Environment.getExternalStorageDirectory()}/${splitId[1]}"
                    }
                }
                uri.isDownloadsDocument() -> {
                    if (docId.startsWith("raw:")) {
                        return docId.replaceFirst("raw:", "")
                    }
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(docId)
                    )
                    return getDataColumn(uri = contentUri)
                }
                uri.isMediaDocument() -> {
                    val contentUri: Uri? = when(splitId[0]) {
                        "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        else -> throw IllegalArgumentException("Illegal Argument Supplied!")
                    }
                    return getDataColumn(contentUri, "_id=?", arrayOf(splitId[1]))
                }
            }
        } else if("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(uri = uri)
        } else if("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }
    private fun getDataColumn(
        uri: Uri?, selection: String? = null, selectionArgs: Array<String>? = null
    ): String? {
        var cursor: Cursor? = null
        try {
            cursor = requireContext().contentResolver.query(
                uri!!, arrayOf("_data"), selection, selectionArgs, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow("_data")
                return cursor.getString(index)
            }
        } catch(e: Exception) { viewModel.showToast.postValue(e.message) }
        finally { cursor?.close() }
        return null
    }
    private fun checkLoginStatus() {
        if(
            viewModel.isLoggedIn()
            && findNavController().currentDestination?.id == R.id.loginFragment
            ) {
            findNavController().navigate(LoginSignUpFragmentDirections.loginToContainer())
        }
    }
}