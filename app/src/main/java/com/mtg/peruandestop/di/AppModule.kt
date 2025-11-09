package com.mtg.peruandestop.di

import android.content.Context
import com.mtg.data.BandejaRepository
import com.mtg.data.PushNotificationRepository
import com.mtg.data.UserRepository
import com.mtg.data.bandeja.IBandejaRemoteDataSource
import com.mtg.data.push.IPushDataSource
import com.mtg.data.user.IUserRemoteDataSource
import com.mtg.framework.bandeja.BandejaRemoteDataSource
import com.mtg.framework.datastore.LoginDataSource
import com.mtg.framework.service.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.mtg.framework.push.FirebaseNotificationDataSource
import com.mtg.framework.user.UserRemoteDataSource
import com.mtg.usecases.bandeja.AtenderRequest
import com.mtg.usecases.bandeja.CotizandoRequest
import com.mtg.usecases.bandeja.GetBandejaByAgent
import com.mtg.usecases.bandeja.ReleaseRequest
import com.mtg.usecases.bandeja.RequestSinRespuesta
import com.mtg.usecases.bandeja.TakeRequest
import com.mtg.usecases.user.Login

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
    fun provideReleaseRequest(bandejaRepo: BandejaRepository): ReleaseRequest {
        return ReleaseRequest(bandejaRepo)
    }

    @Provides
    @Singleton
    fun provideCotizandoRequest(bandejaRepo: BandejaRepository): CotizandoRequest {
        return CotizandoRequest(bandejaRepo)
    }

    @Provides
    @Singleton
    fun provideRequestSinRespuesta(bandejaRepo: BandejaRepository): RequestSinRespuesta {
        return RequestSinRespuesta(bandejaRepo)
    }

    @Provides
    @Singleton
    fun provideAtenderRequest(bandejaRepo: BandejaRepository): AtenderRequest {
        return AtenderRequest(bandejaRepo)
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