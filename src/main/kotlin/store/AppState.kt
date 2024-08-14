package store

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.TrayState
import com.alibaba.fastjson2.JSON
import utils.CountDownType
import utils.TimerType
import java.io.File

class AppState {
    var leftTime by mutableStateOf(2L)
    var startCountDown by mutableStateOf(false)
    var countDownType by mutableStateOf(CountDownType.MEDIUM)
    var timerInfo by mutableStateOf(countDownType.getTimerInfo())
    var currentTimerType by mutableStateOf(TimerType.WORK)
    var shortCycleCount by mutableStateOf(0)
    var longCycleCount by mutableStateOf(0)


    var mainWindowVisible by mutableStateOf(true)
    var trayState: TrayState? = null
    val snackbarHostState = SnackbarHostState()
    var currentView by mutableStateOf("home")
    var timerTypeDialogVisible by mutableStateOf(false)
    var hintWindowVisible by mutableStateOf(false)

    val appName = "番茄时钟（Pomodoro Timer）"

    fun setState(newState: AppState) {
        this.leftTime = newState.leftTime
        this.startCountDown = newState.startCountDown
        this.countDownType = newState.countDownType
        this.timerInfo = newState.timerInfo
    }

    fun setCountDownFlag(flag: Boolean) {
        startCountDown = flag
    }

    fun setTimerType(timerType: TimerType) {
        this.startCountDown = false
        this.currentTimerType = timerType
        this.leftTime = getLeftTimeByTimerType(timerType)
        if (timerType == TimerType.LONG_BREAK) {
            this.longCycleCount++
        }
    }

    private fun getLeftTimeByTimerType(timerType: TimerType): Long {
        return when (timerType) {
            TimerType.WORK -> (timerInfo.work * 60).toLong()
            TimerType.SHORT_BREAK -> (timerInfo.shortBreak * 60).toLong()
            TimerType.LONG_BREAK -> (timerInfo.longBreak * 60).toLong()
        }
    }

    fun resetCountDown(countDownType: CountDownType) {
        this.countDownType = countDownType
        this.timerInfo = countDownType.getTimerInfo()
        this.currentTimerType = TimerType.WORK
        this.shortCycleCount = 0
        this.longCycleCount = 0
        this.startCountDown = false
        this.leftTime = (timerInfo.work * 60).toLong()
    }

    fun showHintWindow() {
        this.mainWindowVisible = true
        this.hintWindowVisible = true
    }

    fun closeHintWindow() {
        this.hintWindowVisible = false
    }

}

object AppStore {
    val state by lazy {
        createAppState()
    }

    // TODO 配置保存和读取
    fun createAppState(): AppState {
        if (File(configPath).exists()) {
            return loadConfig(configPath)
        }
        return AppState()
    }
}

private val configPath = "${getCurrentDirectory()}/config.json"

fun getCurrentDirectory(): String {
    val path = File(AppState::class.java.protectionDomain.codeSource.location.toURI().path).parent
    println(path)
    return path
}

fun saveConfig(state: AppState, filePath: String) {
    try {
        val json = JSON.toJSONString(state)
        File(filePath).writeText(json)
    } catch (e: Exception) {
        println(e)
        e.printStackTrace()
    }
}

fun loadConfig(filePath: String): AppState {
    try {
        val json = File(filePath).readText()
        return JSON.parseObject(json, AppState::class.java)
    } catch (e: Exception) {
        return AppState()
    }
}
