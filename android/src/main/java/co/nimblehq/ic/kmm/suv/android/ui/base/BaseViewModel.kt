package co.nimblehq.ic.kmm.suv.android.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("PropertyName")
abstract class BaseViewModel: ViewModel() {

    private var loadingCount: Int = 0
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    fun dismissError() {
        _errorMessage.value = null
    }

    protected fun showLoading() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        loadingCount++
    }

    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

    protected fun showError(message: String?) {
        _errorMessage.value = message
    }
}
