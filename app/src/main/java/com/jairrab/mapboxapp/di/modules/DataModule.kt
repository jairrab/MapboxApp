package com.jairrab.mapboxapp.di.modules

import com.jairrab.data.DataRepository
import com.jairrab.domain.repository.DomainRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: DataRepository): DomainRepository
}