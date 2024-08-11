package service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Notification
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import store.AppState
import utils.TimerType

@Composable
fun CountDownService(state: AppState) {

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
            // 检查当前所处状态
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
    }
}

fun getNotificationByTimerType(timerType: TimerType): Notification {
    return when (timerType) {
        TimerType.WORK -> Notification("工作时间结束", "休息一下吧")
        TimerType.SHORT_BREAK -> Notification("短休息结束", "准备好了吗")
        TimerType.LONG_BREAK -> Notification("长休息结束", "继续加油")
    }
}
