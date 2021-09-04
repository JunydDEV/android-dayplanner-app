package com.android.dayplanner.app.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.room.Room
import androidx.test.espresso.IdlingResource
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.TaskDatabase
import com.android.dayplanner.app.ui.resourceidling.SimpleIdlingResource


class MainActivity : AppCompatActivity() {

    private lateinit var db: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)

        setSplashExitAnimation(splashScreen)
    }

    private fun setSplashExitAnimation(splashScreen: SplashScreen) {
       // SimpleIdlingResource.setIdleState(false)
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            configureObjectAnimator(splashScreenView) { slideUpAnimation ->
                with(slideUpAnimation) {
                    interpolator = AnticipateInterpolator()
                    duration = 3000L
                    doOnEnd {
                        splashScreenView.remove()
                      //  SimpleIdlingResource.setIdleState(true)
                    }
                    start()
                }
            }
        }
    }

    private fun configureObjectAnimator(
        splashScreenView: SplashScreenViewProvider,
        onComplete: (ObjectAnimator) -> Unit
    ) {
        val objectAnimator = ObjectAnimator.ofFloat(
            splashScreenView.view,
            View.TRANSLATION_Y,
            0f,
            -splashScreenView.view.height.toFloat()
        )
        onComplete.invoke(objectAnimator)
    }

    fun getDatabaseInstance(): TaskDatabase {
        if(!this::db.isInitialized) {
            db = Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java, "tasks-database"
            ).allowMainThreadQueries().build()
        }
        return db
    }
}