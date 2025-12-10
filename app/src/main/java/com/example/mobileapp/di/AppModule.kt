package com.example.mobileapp.di

import android.content.Context
import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.data.repository.LocationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide FirebaseAuth instance
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // Provide FirebaseFirestore instance
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    // Provide FirebaseStorage instance
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    // Provide AuthRepository with all dependencies
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): AuthRepository {
        return AuthRepository(auth, firestore, storage)
    }

    // Provide LocationRepository
    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context
    ): LocationRepository = LocationRepository(context)
}
