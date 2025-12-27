package com.example.mobileapp.data.repository

import com.example.mobileapp.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository

) {

    private val usersRef = firestore.collection("users")

    fun getLeaderboard(): Flow<List<User>> = callbackFlow {
        val listener = usersRef
            .orderBy("points", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val users = snapshot?.documents?.mapNotNull {
                    it.toObject(User::class.java)
                        ?.copy(uid = it.id)
                }.orEmpty()

                trySend(users)
            }

        awaitClose { listener.remove() }
    }

    suspend fun addPointsToCurrentUser(pointsToAdd: Long): Boolean {
        val currentUser = authRepository.currentUser ?: return false
        return try {
            usersRef.document(currentUser.uid)
                .update("points", FieldValue.increment(pointsToAdd))
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
