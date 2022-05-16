package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItemDatabase
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.other.Constants.BASE_URL
import com.androiddevs.shoppinglisttestingyt.other.Constants.DATABASE_NAME
import com.androiddevs.shoppinglisttestingyt.repositories.DefaultShoppingRepository
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideShoppingRepository(defaultShoppingRepository: DefaultShoppingRepository) : ShoppingRepository

    companion object {
        @Provides
        @Singleton
        fun provideShoppingItemDatabase(@ApplicationContext context: Context): ShoppingItemDatabase {
            return Room
                .databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME)
                .build()
        }

        @Provides
        @Singleton
        fun provideShoppingDao(shoppingItemDatabase: ShoppingItemDatabase): ShoppingDao {
            return shoppingItemDatabase.shoppingDao()
        }

        @Provides
        @Singleton
        fun providePixabayApi(): PixabayAPI {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(PixabayAPI::class.java)
        }

        @Provides
        @Singleton
        fun provideGlideInstance(
            @ApplicationContext context: Context
        ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )
    }
}