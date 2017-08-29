package com.reggar.todomvp

import android.app.Application
import com.reggar.todomvp.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DataModule::class,
        ActivityBuilder::class
))
interface ApplicationComponent {
    fun inject(application: App)

    @Component.Builder interface Builder {
        @BindsInstance fun application(application: Application): Builder

        fun dataModule(dataModule: DataModule): Builder

        fun build(): ApplicationComponent
    }
}
