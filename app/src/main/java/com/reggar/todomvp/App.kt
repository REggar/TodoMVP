package com.reggar.todomvp

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class App : Application(), HasActivityInjector {
    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        injectDependencies()
        setupLogger()
    }

    protected open fun injectDependencies() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector() = activityDispatchingAndroidInjector
}