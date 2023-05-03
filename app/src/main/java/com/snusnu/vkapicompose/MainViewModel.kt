package com.snusnu.vkapicompose

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.domain.PostComment
import com.snusnu.vkapicompose.domain.StatisticItem
import com.snusnu.vkapicompose.ui.theme.screens.home_screen.HomeScreenState

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it
                )
            )
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val initialState = HomeScreenState.Posts(initList)
    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState
    private var savedState: HomeScreenState? = initialState

    fun closeComments() {
        _screenState.value = savedState
    }
    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }
    fun increaseCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            modifiedList.replaceAll { feedPostOld ->
                if (feedPost == feedPostOld) {
                    feedPostOld.copy(
                        statistics = feedPostOld.statistics.toMutableList().apply {
                            replaceAll { oldItem ->
                                if (oldItem == item) {
                                    oldItem.copy(count = oldItem.count + 1)
                                } else {
                                    oldItem
                                }
                            }
                        }
                    )
                } else {
                    feedPostOld
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(modifiedList)
    }

    fun deleteFeedPost(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(modifiedList)
    }
}