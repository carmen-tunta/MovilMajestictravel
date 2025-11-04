package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.BandejaRepository
import com.ucb.data.PushNotificationRepository
import com.ucb.data.UserRepository
import com.ucb.data.bandeja.IBandejaRemoteDataSource
import com.ucb.data.push.IPushDataSource
import com.ucb.data.user.IUserRemoteDataSource
import com.ucb.framework.bandeja.BandejaRemoteDataSource
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.framework.service.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ucb.framework.push.FirebaseNotificationDataSource
import com.ucb.framework.user.UserRemoteDataSource
import com.ucb.usecases.bandeja.GetBandejaByAgent
import com.ucb.usecases.bandeja.TakeRequest
import com.ucb.usecases.user.Login

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(@ApplicationContext context: Context) : RetrofitBuilder {
        return RetrofitBuilder(context)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: RetrofitBuilder): IUserRemoteDataSource {
        return UserRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideUserRepository(iUserDS: IUserRemoteDataSource): UserRepository {
        return UserRepository(iUserDS)
    }

    @Provides
    @Singleton
    fun provideLogin(userRepository: UserRepository): Login {
        return Login(userRepository)
    }

    @Provides
    @Singleton
    fun provideBandejaRemoteDataSource(retrofit: RetrofitBuilder): IBandejaRemoteDataSource {
        return BandejaRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideBandejaRepository(iBandejaRDS: IBandejaRemoteDataSource): BandejaRepository {
        return BandejaRepository(iBandejaRDS)
    }

    @Provides
    @Singleton
    fun provideGetBandejaByAgent(bandejaRepo: BandejaRepository): GetBandejaByAgent {
        return GetBandejaByAgent(bandejaRepo)
    }

    @Provides
    @Singleton
    fun provideTakeRequest(bandejaRepo: BandejaRepository): TakeRequest {
        return TakeRequest(bandejaRepo)
    }


    @Provides
    @Singleton
    fun provideLoginDataSource( @ApplicationContext context: Context ): LoginDataSource {
        return LoginDataSource(context = context)
    }

    @Provides
    @Singleton
    fun providePushNotificationRepository( pushDataSource: IPushDataSource): PushNotificationRepository {
        return PushNotificationRepository(pushDataSource)
    }

    @Provides
    @Singleton
    fun provideIPushDataSource(): IPushDataSource {
        return FirebaseNotificationDataSource()
    }
}