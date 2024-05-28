package com.example.musicplayermobileapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val showToast: MutableLiveData<String> = MutableLiveData()
    fun validateRegister(gender: String, age: Int) {
        if(username.value != "" && password.value != "") {
            try {
//                userRepo.addUser(User(
//                    username = username.value!!,
//                    password = password.value!!.hashCode(),
//                ))
            } catch (e: Exception) { showToast.postValue(e.message) }
        } else {
            showToast.postValue("Please enter appropriate values for username and password!")
        }
    }
    fun addUser(user: User) { viewModelScope.launch(Dispatchers.IO) { userRepo.addUser(user) } }
}