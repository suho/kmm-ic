package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateFormat
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import kotlinx.coroutines.flow.*
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
                    showError(e.message)
                }
                .onCompletion { hideLoading() }
                .collect {
                    _avatarUrlString.value = it.avatarUrl
                }
        }
    }
}
