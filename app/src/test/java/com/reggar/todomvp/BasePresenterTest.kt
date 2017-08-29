package com.reggar.todomvp

import android.support.annotation.CallSuper
import com.reggar.todomvp.feature.base.MvpContract

abstract class BasePresenterTest<P : MvpContract.Presenter<V>, V : MvpContract.View> : BaseTest() {
    protected lateinit var presenter: P
    protected lateinit var view: V

    @CallSuper
    override fun setUp() {
        super.setUp()
        view = createView()
        presenter = createPresenter()
        beforeAttachView()
        presenter.attachView(view)
    }

    protected abstract fun createView(): V

    protected abstract fun createPresenter(): P

    protected open fun beforeAttachView() = Unit
}