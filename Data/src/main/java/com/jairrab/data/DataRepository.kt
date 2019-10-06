package com.jairrab.data

import com.jairrab.data.mapper.MapPointMapper
import com.jairrab.data.model.MapPointData
import com.jairrab.data.store.DataStore
import com.jairrab.data.util.TimeUtils
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.Source
import com.jairrab.domain.repository.DomainRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataStore: DataStore,
    private val timeUtils: TimeUtils,
    private val mapper: MapPointMapper
) : DomainRepository {

    override fun getMapPoints(): Observable<MapInformation> {
        return dataStore.getRemoteData(true).getMapPoints()
            .map { list ->
                MapInformation(
                    mapPoints = list.map { mapper.mapToDomain(it) },
                    source = Source.REMOTE,
                    timeStamp = timeUtils.currentTime
                )
            }
            .map { information ->
                val list = information.mapPoints.map { mapper.mapToData(it) }

                val updateLastLocationTimeStamp = dataStore.getRemoteData(false)
                    .updateLastLocationTimeStamp(information.timeStamp)

                dataStore.getRemoteData(false)
                    .saveMapPoints(list)
                    .andThen(updateLastLocationTimeStamp)
                    .andThen(Observable.just(information))
            }
            .onErrorResumeNext { t: Throwable ->
                Observable.zip(
                    dataStore.getRemoteData(false).getLastLocationTimeStamp(),
                    dataStore.getRemoteData(false).getMapPoints(),
                    BiFunction<Long, List<MapPointData>, Pair<Long, List<MapPointData>>> { t1, t2 ->
                        Pair(t1, t2)
                    })
                    .map { pair ->
                        Observable.just(
                            MapInformation(
                                mapPoints = pair.second.map { mapper.mapToDomain(it) },
                                source = Source.CACHE,
                                timeStamp = pair.first
                            )
                        )
                    }
            }
            .flatMap { it }
    }
}