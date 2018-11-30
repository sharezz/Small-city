package com.sharezzorama.smallcity.base.mvp

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseMvpPresenter<View : BaseMvpView> : MvpPresenter<View>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}