package com.jairrab.domain.usercases

import com.jairrab.domain.ObservableUseCase
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.repository.DomainRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMapLocations @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val domainRepository: DomainRepository
) : ObservableUseCase<MapInformation, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<MapInformation> {
        return domainRepository.getMapPoints()
    }
}