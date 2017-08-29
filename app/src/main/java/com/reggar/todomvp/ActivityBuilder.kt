package com.reggar.todomvp

import com.reggar.todomvp.feature.main.MainActivity
import com.reggar.todomvp.feature.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun bindMainActivity(): MainActivity
}