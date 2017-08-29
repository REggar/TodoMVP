package com.reggar.todomvp

import android.support.annotation.CallSuper
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.reggar.todomvp.common.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseTest {
    val schedulerProvider = mock<SchedulerProvider> {
        on { ui() } doReturn Schedulers.trampoline()
        on { computation() } doReturn Schedulers.trampoline()
        on { io() } doReturn Schedulers.trampoline()
    }

    @CallSuper
    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
}
