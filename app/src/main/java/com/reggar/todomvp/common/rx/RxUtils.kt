package com.reggar.todomvp.common.rx

import io.reactivex.functions.BiFunction

fun <U> takeSecondObservable() = BiFunction<Any, U, U> { _, second -> second }