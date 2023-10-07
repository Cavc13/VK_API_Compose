package com.snusnu.vkapicompose.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.snusnu.vkapicompose.di.ApplicationComponent
import com.snusnu.vkapicompose.di.DaggerApplicationComponent

class VkApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this
        )
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as VkApplication).component
}