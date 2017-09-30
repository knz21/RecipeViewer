package com.knz21.android.recipeviewer.di

import android.content.Context
import com.github.gfx.android.orma.AccessThreadConstraint
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.knz21.android.recipeviewer.domain.model.OrmaDatabase
import com.knz21.android.recipeviewer.infra.api.RecipeService
import com.knz21.android.recipeviewer.presentation.RecipeApp
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: RecipeApp) {

  private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

  @Provides
  fun provideContext(): Context = applicationContext

  @Provides
  fun providesOkHttp(): OkHttpClient {
    val logger = HttpLoggingInterceptor { log -> Timber.tag("OkHttp").v(log) }
    logger.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
  }

  @Provides
  fun provideRetrofit(oktHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(oktHttpClient)
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Provides
  fun provideRecipeService(retrofit: Retrofit): RecipeService = retrofit.create(RecipeService::class.java)

  @Provides
  fun provideOrma(context: Context): OrmaHolder {
    val orma = OrmaDatabase.builder(context)
        .writeOnMainThread(AccessThreadConstraint.FATAL)
        .readOnMainThread(AccessThreadConstraint.FATAL)
        .trace(true)
        .build()
    return OrmaHolder(orma)
  }

  class OrmaHolder(val orma: OrmaDatabase)
}

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
  fun plus(m: RecipeModule): RecipeComponent
}