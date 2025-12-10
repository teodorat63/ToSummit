package com.example.mobileapp.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    // ------------------------------------
    // REGISTER USER
    // ------------------------------------
    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        photoUri: Uri?
    ): Result<FirebaseUser> {
        return try {
            // 1. CREATE USER IN FIREBASE AUTH
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("User not created"))
            val uid = user.uid

            // 2. UPLOAD PROFILE PHOTO TO STORAGE
            val photoUrl = photoUri?.let { uploadProfilePhoto(uid, it) }

            // 3. SAVE USER INFO TO FIRESTORE
            val userData = hashMapOf(
                "email" to email,
                "firstName" to firstName,
                "lastName" to lastName,
                "phone" to phone,
                "photoUrl" to (photoUrl ?: "")
            )

            firestore.collection("users").document(uid).set(userData).await()

            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ------------------------------------
    // LOGIN USER
    // ------------------------------------
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

    // ------------------------------------
    // IMAGE UPLOAD
    // ------------------------------------
    private suspend fun uploadProfilePhoto(uid: String, uri: Uri): String {
        val ref = storage.reference.child("profilePhotos/$uid.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    // ------------------------------------
    // LOGOUT
    // ------------------------------------
    fun logout() = firebaseAuth.signOut()

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
}
