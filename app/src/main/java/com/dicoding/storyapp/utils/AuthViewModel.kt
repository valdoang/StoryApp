package com.dicoding.storyapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.model.LoginResult
import com.dicoding.storyapp.preference.LoginPreference
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: LoginPreference): ViewModel() {
    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun login(token: String) {
        viewModelScope.launch {
            pref.login(token)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}