package com.jairrab.domain

import com.jairrab.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {

    var observer: DisposableObserver<T>? = null
    private val disposables = CompositeDisposable()


    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        this.observer = observer

        val observable = buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)

        addDisposable(observable.subscribeWith(observer))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}