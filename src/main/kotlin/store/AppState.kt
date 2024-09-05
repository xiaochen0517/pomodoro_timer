package store

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.TrayState
import com.google.gson.annotations.Expose
import utils.CountDownType
import utils.StoreUtil
import utils.TimerType
import java.io.Serializable

data class SerializableAppState(
    @Expose
    var countDownType: CountDownType? = CountDownType.MEDIUM,
    @Expose
    var currentTimerType: TimerType? = TimerType.WORK,
    @Expose
    var shortCycleCount: Int? = 0,
    @Expose
    var longCycleCount: Int? = 0,
    @Expose
    var leftTime: Long? = 2L,
    @Expose
    var wxPusherUID: String? = ""
)

class AppState : Serializable {

    val appName = "番茄时钟（Pomodoro Timer）"

    var countDownType by mutableStateOf(CountDownType.MEDIUM)
    var currentTimerType by mutableStateOf(TimerType.WORK)
    var shortCycleCount by mutableStateOf(0)
    var longCycleCount by mutableStateOf(0)

    var leftTime by mutableStateOf(2L)
    var startCountDown by mutableStateOf(false)

    var wxPusherUID by mutableStateOf("")

    var hintWindowVisible by mutableStateOf(false)
    var mainWindowVisible by mutableStateOf(true)
    var currentView by mutableStateOf("home")
    var timerTypeDialogVisible by mutableStateOf(false)
    var wxPusherDialogVisible by mutableStateOf(false)

    var trayState: TrayState? = null

    val snackbarHostState = SnackbarHostState()
    private var timerInfo = countDownType.getTimerInfo()

    private fun getLeftTimeByTimerType(timerType: TimerType): Long {
        return when (timerType) {
            TimerType.WORK -> (this.timerInfo.work * 60).toLong()
            TimerType.SHORT_BREAK -> (this.timerInfo.shortBreak * 60).toLong()
            TimerType.LONG_BREAK -> (this.timerInfo.longBreak * 60).toLong()
        }
    }

    fun showHintWindow() {
        this.mainWindowVisible = true
        this.hintWindowVisible = true
    }

    /**
     * set function
     */
    fun setState(newState: SerializableAppState) {
        this.countDownType = newState.countDownType ?: CountDownType.MEDIUM
        this.currentTimerType = newState.currentTimerType ?: TimerType.WORK
        this.shortCycleCount = newState.shortCycleCount ?: 0
        this.longCycleCount = newState.longCycleCount ?: 0
        this.timerInfo = this.countDownType.getTimerInfo()
        this.leftTime = newState.leftTime ?: (this.timerInfo.work * 60).toLong()
        this.wxPusherUID = newState.wxPusherUID ?: ""
    }

    fun setTimerType(timerType: TimerType) {
        this.startCountDown = false
        this.currentTimerType = timerType
        this.leftTime = getLeftTimeByTimerType(timerType)
        if (timerType == TimerType.LONG_BREAK) {
            this.longCycleCount++
        }
        StoreUtil.saveConfig()
    }

    fun resetCountDown(countDownType: CountDownType) {
        this.countDownType = countDownType
        this.timerInfo = countDownType.getTimerInfo()
        this.currentTimerType = TimerType.WORK
        this.shortCycleCount = 0
        this.longCycleCount = 0
        this.startCountDown = false
        this.leftTime = (timerInfo.work * 60).toLong()
        StoreUtil.saveConfig()
    }

    fun getSerializableAppState(): SerializableAppState {
        return SerializableAppState(
            countDownType = this.countDownType,
            currentTimerType = this.currentTimerType,
            shortCycleCount = this.shortCycleCount,
            longCycleCount = this.longCycleCount,
            leftTime = this.leftTime,
            wxPusherUID = this.wxPusherUID
        )
    }

    fun saveWxPusherUID(wxPusherUID: String) {
        this.wxPusherUID = wxPusherUID
        StoreUtil.saveConfig()
    }

}

object AppStore {
    val state by lazy {
        createAppState()
    }

    private fun createAppState(): AppState {
        val state = AppState()
        val readState = StoreUtil.loadConfig()
        state.setState(readState)
        return state
    }
}
