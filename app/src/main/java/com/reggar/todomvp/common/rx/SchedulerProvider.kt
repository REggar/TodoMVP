package com.reggar.todomvp.common.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {
    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun computation() = Schedulers.computation()

    fun io() = Schedulers.io()
}