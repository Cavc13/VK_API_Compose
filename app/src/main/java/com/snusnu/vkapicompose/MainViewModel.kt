package com.snusnu.vkapicompose

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snusnu.vkapicompose.domain.FeedPostModel
import com.snusnu.vkapicompose.domain.StatisticItem
import com.snusnu.vkapicompose.ui.theme.screens.home_screen.HomeScreenState

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPostModel>().apply {
        repeat(10) {
            add(
                FeedPostModel(
                    id = it
                )
            )
        }
    }

    private val initialState = HomeScreenState.Posts(initList)
    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun increaseCount(feedPostModel: FeedPostModel, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            modifiedList.replaceAll { feedPost ->
                if (feedPost == feedPostModel) {
                    feedPostModel.copy(
                        statistics = feedPost.statistics.toMutableList().apply {
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
                    feedPost
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(modifiedList)
    }

    fun deleteFeedPost(feedPostModel: FeedPostModel) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(feedPostModel)
        _screenState.value = HomeScreenState.Posts(modifiedList)
    }
}