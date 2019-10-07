package com.jairrab.domain.usercases

import com.jairrab.domain.ObservableUseCase
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.domain.model.LocationFeature
import com.jairrab.domain.repository.DomainRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetLocationQuery @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val domainRepository: DomainRepository
) : ObservableUseCase<LocationFeature, Pair<Double, Double>?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Pair<Double, Double>?): Observable<LocationFeature> {
        return domainRepository.getCurrentLocation(
            params?.first?:0.0,
            params?.second?:0.0
        )
    }
}