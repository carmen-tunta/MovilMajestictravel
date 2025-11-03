package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.BandejaRepository
import com.ucb.data.GithubRepository
import com.ucb.data.MovieRepository
import com.ucb.data.PushNotificationRepository
import com.ucb.data.UserRepository
import com.ucb.data.bandeja.IBandejaRemoteDataSource
import com.ucb.data.git.IGitRemoteDataSource
import com.ucb.data.git.ILocalDataSource
import com.ucb.data.movie.IMovieRemoteDataSource
import com.ucb.data.push.IPushDataSource
import com.ucb.data.user.IUserRemoteDataSource
import com.ucb.framework.bandeja.BandejaRemoteDataSource
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.framework.github.GithubLocalDataSource
import com.ucb.framework.github.GithubRemoteDataSource
import com.ucb.framework.movie.MovieRemoteDataSource
import com.ucb.framework.service.RetrofitBuilder
import com.ucb.ucbtest.R
import com.ucb.usecases.FindGitAlias
import com.ucb.usecases.GetPopularMovies
import com.ucb.usecases.SaveGitalias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ucb.framework.push.FirebaseNotificationDataSource
import com.ucb.framework.user.UserRemoteDataSource
import com.ucb.usecases.ObtainToken
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
    fun gitRemoteDataSource(retrofiService: RetrofitBuilder): IGitRemoteDataSource {
        return GithubRemoteDataSource(retrofiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): ILocalDataSource {
        return GithubLocalDataSource(context)
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
    fun gitRepository(remoteDataSource: IGitRemoteDataSource, localDataSource: ILocalDataSource): GithubRepository {
        return GithubRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveGitAlias(repository: GithubRepository): SaveGitalias {
        return SaveGitalias(repository)
    }

    @Provides
    @Singleton
    fun provideGitUseCases(githubRepository: GithubRepository): FindGitAlias {
        return FindGitAlias(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGetPopularMovies(movieRepository: MovieRepository, @ApplicationContext context: Context): GetPopularMovies {
        val token = context.getString(R.string.token)
        return GetPopularMovies(movieRepository, token)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dataSource: IMovieRemoteDataSource) : MovieRepository {
        return MovieRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(retrofit: RetrofitBuilder ): IMovieRemoteDataSource {
        return MovieRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource( @ApplicationContext context: Context ): LoginDataSource {
        return LoginDataSource(context = context)
    }

    @Provides
    @Singleton
    fun provideObtainToken( pushNotificationRepository: PushNotificationRepository): ObtainToken {
        return ObtainToken(pushNotificationRepository)
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