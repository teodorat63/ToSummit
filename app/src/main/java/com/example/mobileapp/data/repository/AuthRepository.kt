package com.example.mobileapp.data.repository

import android.net.Uri
import com.cloudinary.Cloudinary
import com.example.mobileapp.data.model.User
import com.example.mobileapp.data.remote.cloudinary.CloudinaryDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val cloudinaryDataSource: CloudinaryDataSource
) {

    suspend fun register(
        user: User,
        password: String,
        photoUri: Uri?
    ): Result<FirebaseUser> {
        return try {
            // 1. Create user in Firebase Auth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("User not created"))
            val uid = firebaseUser.uid

            // 2. Upload Profile Picture to Cloudinary
            val photoUrl = photoUri?.let { cloudinaryDataSource.uploadAvatar(it, uid) }

            // 3. Create full user object with uid and photoUrl
            val newUser = user.copy(uid = uid, photoUrl = photoUrl)

            // 4. Save to Firestore
            firestore.collection("users")
                .document(uid)
                .set(newUser)
                .await()

            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(Exception("Error adding user to Firestore: $e"))
        }
    }


    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() = firebaseAuth.signOut()

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
}
