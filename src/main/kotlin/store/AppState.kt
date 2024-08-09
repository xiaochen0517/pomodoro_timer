package store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppState {
    var leftTime by mutableStateOf(10L)
    var startCountDown by mutableStateOf(false)
}

object AppStore {
    var state = AppState()
}
