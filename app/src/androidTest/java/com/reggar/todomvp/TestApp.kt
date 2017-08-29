package com.reggar.todomvp

import com.reggar.todomvp.data.TestDataModule

class TestApp : App() {
    override fun injectDependencies() {
        DaggerApplicationComponent.builder()
                .application(this)
                .dataModule(TestDataModule())
                .build()
                .inject(this)
    }
}