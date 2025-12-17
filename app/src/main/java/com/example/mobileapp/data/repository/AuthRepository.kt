package com.example.mobileapp.data.repository

import android.net.Uri
import com.cloudinary.Cloudinary
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
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        photoUri: Uri?
    ): Result<FirebaseUser> {
        return try {
            // 1. Create user in Firebase Auth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("User not created"))
            val uid = user.uid

            // 2. Upload Profile Picture to Cloudinary
            var photoUrl: String? = null
            if (photoUri != null) {
                photoUrl = cloudinaryDataSource.uploadAvatar(photoUri, uid)
            }

            // 3. Save user info to Firestore
            val userData = hashMapOf(
                "email" to email,
                "firstName" to firstName,
                "lastName" to lastName,
                "phone" to phone,
                "photoUrl" to photoUrl
            )

            firestore.collection("users").document(uid).set(userData).await()

            Result.success(user)

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
