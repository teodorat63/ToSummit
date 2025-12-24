package com.example.mobileapp.screens.leaderboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.model.User
import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository

) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())

    val users: StateFlow<List<User>> = _users.asStateFlow()

    val currentUserId: String?
        get() = authRepository.currentUser?.uid

    init {
        fetchLeaderboard()
    }

    private fun fetchLeaderboard() {
        viewModelScope.launch {
            repository.getLeaderboard()
                .catch { e ->
                    Log.e("Leaderboard", e.message.orEmpty())
                }
                .collect { users ->
                    _users.value = users
                }
        }
    }
}
