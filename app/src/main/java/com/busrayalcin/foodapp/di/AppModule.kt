package com.busrayalcin.foodapp.di

import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import com.busrayalcin.foodapp.retrofit.ApiUtils
import com.busrayalcin.foodapp.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodDaoRepo(fdao : FoodDao) : FoodDaoRepo {
        return FoodDaoRepo(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodDao{
        return ApiUtils.getFoodDao()
    }
}