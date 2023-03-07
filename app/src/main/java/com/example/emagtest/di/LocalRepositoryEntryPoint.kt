package com.example.emagtest.di

import com.example.emagtest.room.LocalRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocalRepositoryEntryPoint {
    fun getLocalRepository(): LocalRepository
}