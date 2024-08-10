package store

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alibaba.fastjson2.JSON
import java.io.File

class AppState {
    var leftTime by mutableStateOf(10L)
    var startCountDown by mutableStateOf(false)

    val snackbarHostState = SnackbarHostState()
    var showTimeSelectorDialog by mutableStateOf(false)

    fun setCountDownFlag(flag: Boolean) {
        startCountDown = flag
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
