package com.android.dayplanner.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dayplanner.app.ui.resourceidling.SimpleIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    fun doInitialization(onInitializationComplete: (Boolean) -> Unit) {
        SimpleIdlingResource.setIdleState(false)
        viewModelScope.launch {
            delay(3000)
            onInitializationComplete.invoke(true)

            SimpleIdlingResource.setIdleState(true)
        }
    }
}