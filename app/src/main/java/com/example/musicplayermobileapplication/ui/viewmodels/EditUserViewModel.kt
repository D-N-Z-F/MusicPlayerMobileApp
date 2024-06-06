package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.PublicUserDetails
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val authService: AuthService
): ViewModel() {
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish
    val showToast: MutableLiveData<String> = MutableLiveData()
    private var originalUserData: PublicUserDetails? = null
    private fun triggerFinish() { viewModelScope.launch { _finish.emit(Unit) } }
    private fun getUserId() = authService.getUserId()
    fun getUserById(): Flow<PublicUserDetails?> = userRepo.getUserById(getUserId())
    fun setupOriginal(user: PublicUserDetails) { originalUserData = user }
    fun validateUpdateDetails(
        username: String,
        age: Int,
        gender: Genders,
        bio: String,
        password: String,
        password2: String
    ) {
        originalUserData?.let {
            if(username == "" || password == "" || password2 == "") {
                showToast.postValue("Please enter appropriate values!")
            } else if(username == it.username && age == it.age
                && gender == it.gender && bio == it.bio) {
                showToast.postValue("Nothing to be changed!")
            } else if(password != password2) {
                showToast.postValue("Passwords don't match!")
            } else {
                updateUser(
                    User(
                        id = it.id,
                        username = username,
                        age = age,
                        gender = gender,
                        bio = bio,
                        password = password.hashCode(),
                        imagePath = it.imagePath,
                        status = it.status,
                        joinedAt = it.joinedAt
                    )
                )
            }
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepo.updateUser(user)
                showToast.postValue("Updated Successfully!")
            } catch (e: Exception) { showToast.postValue(e.message) }
            triggerFinish()
        }
    }
}