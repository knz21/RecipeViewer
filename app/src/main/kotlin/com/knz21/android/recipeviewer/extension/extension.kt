package com.knz21.android.recipeviewer.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.applySchedulers(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
  compositeDisposable.add(this)
}
