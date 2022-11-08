package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateFormat
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    dateTime: DateTime,
    dateTimeFormatter: DateTimeFormatter
    ) : BaseViewModel() {

    private val _currentDate = MutableStateFlow("")
    val currentDate: StateFlow<String> = _currentDate.asStateFlow()

    private val _avatarUrlString = MutableStateFlow("")
    val avatarUrlString: StateFlow<String> = _avatarUrlString.asStateFlow()

    init {
        _currentDate.value = dateTimeFormatter.getFormattedString(
            dateTime.today(),
            DateFormat.WeekDayMonthDay
        ).uppercase()
    }

    fun loadProfile() {
        showLoading()
        viewModelScope.launch {
            getProfileUseCase()
                .catch { e ->
                    hideLoading()
                    showError(e.message)
                }
                .collect {
                    hideLoading()
                    _avatarUrlString.value = it.avatarUrl
                }
        }
    }
}
