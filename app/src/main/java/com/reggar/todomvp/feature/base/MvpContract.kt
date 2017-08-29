package com.reggar.todomvp.feature.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface MvpContract {
    interface View

    abstract class Presenter<T : View> {
        private val disposables: CompositeDisposable = CompositeDisposable()
        protected lateinit var view: T
            private set

        fun attachView(view: T) {
            this.view = view
            onAttachView()
        }

        fun detachView() {
            onDetachView()
            disposables.clear()
        }

        protected fun addDisposable(disposable: Disposable) {
            disposables.add(disposable)
        }

        protected fun removeDisposable(disposable: Disposable) {
            disposables.remove(disposable)
        }

        protected abstract fun onAttachView()

        protected open fun onDetachView() = Unit
    }
}