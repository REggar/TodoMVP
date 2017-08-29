package com.reggar.todomvp

import com.reggar.todomvp.common.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import org.threeten.bp.Clock
import javax.inject.Singleton

/**
 * The Dagger module of the application, containing all application-wide singletons.
 */
@Module
class ApplicationModule {
    @Provides @Singleton
    internal fun provideSchedulerProvider() = SchedulerProvider()

    @Provides @Singleton
    internal fun provideClock() = Clock.systemDefaultZone()
}
