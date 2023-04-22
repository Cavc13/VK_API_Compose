package com.snusnu.vkapicompose.ui.theme.screens.instagram_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InstagramViewModel: ViewModel() {
    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> = _isFollowing

    fun changeFollowingStatus() {
        val wasFollowing = _isFollowing.value ?: false
        _isFollowing.value = !wasFollowing
    }
}