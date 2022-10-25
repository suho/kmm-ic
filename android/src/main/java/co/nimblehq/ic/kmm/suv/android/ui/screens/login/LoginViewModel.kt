package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val logInUseCase: LogInUseCase
): ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val isLogInSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    fun logIn() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            logInUseCase(email = uiState.value.email, password = uiState.value.password)
                .catch { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.message)
                    }
                }
                .collect {
                    _uiState.update {
                        it.copy(isLoading = false, isLogInSuccess = true)
                    }
                }
        }
    }
}


