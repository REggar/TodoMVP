package com.reggar.todomvp.common.rx

import io.reactivex.Observable
import org.junit.Test

class RxUtils {
    @Test
    fun takeSecondObservable_returnsSecondObservable() {
        /* Given */
        val value1 = "one"
        val value2 = "two"
        val observable1 = Observable.just(value1)
        val observable2 = Observable.just(value2)

        /* When */
        val observable = Observable.combineLatest(observable1, observable2, takeSecondObservable()).test()

        /* Then */
        observable.assertValue(value2)
        observable.assertComplete()
    }
}