package com.hackfest.KereHore.di

import android.content.Context
import androidx.room.Room
import com.hackfest.KereHore.database.HF_Database
import com.hackfest.KereHore.database.HF_Database2
import com.hackfest.KereHore.database.HF_DatabaseDao
import com.hackfest.KereHore.database.HF_DatabaseDao2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

//@DisableInstallInCheck
@InstallIn(SingletonComponent:: class)
@Module
object HF_AppModule {
    @Singleton
    @Provides
    fun HF_providePocketDao(HF_Database: HF_Database): HF_DatabaseDao
            = HF_Database.HF_DatabaseDao()

    @Singleton
    @Provides
    fun HF_provideAppDatabase(@ApplicationContext context: Context): HF_Database
            = Room.databaseBuilder(
        context,
        HF_Database::class.java,
        "pocket_db")
        .fallbackToDestructiveMigration()
        .build()
}

@InstallIn(SingletonComponent::class)
@Module
object HF_AppModule2 {
    @Singleton
    @Provides
    fun HF_provideTotalBalanceDao(HF_Database2: HF_Database2): HF_DatabaseDao2
        = HF_Database2.HF_DatabaseDao2()

    @Singleton
    @Provides
    fun provideAppDatabase2(@ApplicationContext context: Context): HF_Database2
        = Room.databaseBuilder(
            context,
            HF_Database2::class.java,
            "totalBalance_db")
            .fallbackToDestructiveMigration()
            .build()
}