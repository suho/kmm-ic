package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val logInUseCase: LogInUseCase
): BaseViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun logIn() {
        showLoading()
        viewModelScope.launch {
            logInUseCase(email = email.value, password = password.value)
                .catch { e ->
                    hideLoading()
                    showError(e.message)
                }
                .collect {
                    hideLoading()
                    _isLoginSuccess.emit(true)
                }
        }
    }
}
