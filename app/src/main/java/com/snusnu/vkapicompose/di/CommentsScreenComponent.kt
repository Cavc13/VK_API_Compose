package com.snusnu.vkapicompose.di

import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface  Factory{

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}