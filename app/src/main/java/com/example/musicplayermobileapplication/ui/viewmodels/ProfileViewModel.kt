package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {
    val showToast: MutableLiveData<String> = MutableLiveData()
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            authService.logout()
            showToast.postValue("Logout Successful!")
            _finish.emit(Unit)
        }
    }
    fun isLoggedIn(): Boolean = authService.isLoggedIn()
}