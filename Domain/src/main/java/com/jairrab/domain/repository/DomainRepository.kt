package com.jairrab.domain.repository

import com.jairrab.domain.model.MapInformation
import io.reactivex.Observable

interface DomainRepository {

    fun getMapPoints(): Observable<MapInformation>
}