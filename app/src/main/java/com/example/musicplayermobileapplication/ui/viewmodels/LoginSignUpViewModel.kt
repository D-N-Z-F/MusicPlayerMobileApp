package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val authService: AuthService
): ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val showToast: MutableLiveData<String> = MutableLiveData()
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish
    fun validateRegister(gender: String, age: Int) {
        if(username.value != "" && password.value != "") {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val hashedPassword = password.value!!.hashCode()
                    val selectedGender = when(gender) {
                        "Male" -> Genders.MALE
                        "Female" -> Genders.FEMALE
                        "Non-Binary" -> Genders.NON_BINARY
                        else -> Genders.UNDISCLOSED
                    }
                    val user = userRepo.validateUser(username.value!!, hashedPassword)
                    if(user == null) {
                        userRepo.addUser(User(
                            username = username.value!!,
                            password = hashedPassword,
                            gender = selectedGender,
                            age = age
                        ))
                        val newUser = userRepo.validateUser(username.value!!, hashedPassword)
                        if(newUser != null) { login(newUser.id!!, username.value!!) }
                    } else { showToast.postValue("User already exists!") }
                }
            } catch (e: Exception) { showToast.postValue(e.message) }
        } else {
            showToast.postValue("Please enter appropriate values for username and password!")
        }
    }
    fun validateLogin() {
        if(username.value != "" && password.value != "") {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val hashedPassword = password.value!!.hashCode()
                    val user = userRepo.validateUser(username.value!!, hashedPassword)
                    if(user != null) { login(user.id!!, username.value!!) }
                    else { showToast.postValue("User doesn't exist!") }
                }
            } catch (e: Exception) { showToast.postValue(e.message) }
        } else {
            showToast.postValue("Please enter appropriate values for username and password!")
        }
    }
    private fun login(id: Int, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            authService.login(id, username)
            showToast.postValue("Success!")
            _finish.emit(Unit)
        }
    }
    fun isLoggedIn(): Boolean = authService.isLoggedIn()
}