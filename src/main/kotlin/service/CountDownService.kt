package service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import store.AppState
import store.AppStore
import utils.TimerType

@Composable
fun CountDownService() {

    val state = AppStore.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.startCountDown) {
        if (state.startCountDown) {
            while (state.leftTime > 0) {
                delay(1000L)
                state.leftTime--
            }
            scope.launch {
                state.snackbarHostState.showSnackbar(message = "倒计时结束")
            }
            state.trayState?.sendNotification(state.currentTimerType.getNotification())
            state.showHintWindow()
            // 检查当前所处状态
            setTimerType(state)
        }
    }
}

private fun setTimerType(state: AppState) {
    when (state.currentTimerType) {
        TimerType.WORK -> {
            state.shortCycleCount++
            // 如果是第四个番茄钟，进行长休息
            if (state.shortCycleCount % 4 == 0) {
                state.setTimerType(TimerType.LONG_BREAK)
            } else {
                state.setTimerType(TimerType.SHORT_BREAK)
            }
        }

        TimerType.SHORT_BREAK, TimerType.LONG_BREAK -> {
            state.setTimerType(TimerType.WORK)
        }
    }
}
