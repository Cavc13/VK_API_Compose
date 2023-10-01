package com.snusnu.vkapicompose.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.snusnu.vkapicompose.data.repository.NewsFeedRepository
import com.snusnu.vkapicompose.domain.FeedPost
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
): AndroidViewModel(application) {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)
    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = repository.getWallComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(feedPost, comments)
        }
    }
}