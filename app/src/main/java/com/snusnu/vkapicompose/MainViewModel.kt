package com.snusnu.vkapicompose

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.domain.StatisticItem

class MainViewModel: ViewModel() {
    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun increaseCount(item: StatisticItem) {
        val oldStatistics = feedPost.value?.statistics ?: throw IllegalAccessException()
        val newStatistics = oldStatistics.toMutableList().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                replaceAll { oldItem ->
                    if (oldItem.type == item.type) {
                        oldItem.copy(count = oldItem.count + 1)
                    } else {
                        oldItem
                    }
                }
            }
        }
        _feedPost.value = feedPost.value?.copy(statistics = newStatistics)
    }
}