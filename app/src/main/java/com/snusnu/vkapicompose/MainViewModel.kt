package com.snusnu.vkapicompose

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snusnu.vkapicompose.domain.FeedPostModel
import com.snusnu.vkapicompose.domain.StatisticItem

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

    private val _feedPostModels = MutableLiveData<List<FeedPostModel>>(initList)
    val feedPostModels: LiveData<List<FeedPostModel>> = _feedPostModels

    fun increaseCount(feedPostModel: FeedPostModel, item: StatisticItem) {
        val modifiedList = feedPostModels.value?.toMutableList() ?: throw IllegalAccessException()
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
        _feedPostModels.value = modifiedList
    }

    fun deleteFeedPost(feedPostModel: FeedPostModel) {
        val modifiedList = _feedPostModels.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPostModel)
        _feedPostModels.value = modifiedList
    }
}