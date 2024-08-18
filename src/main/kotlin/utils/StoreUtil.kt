package utils

import com.alibaba.fastjson2.JSON
import store.AppState
import store.AppStore
import java.io.File

class StoreUtil {
    companion object {

        private val configPath = "${getCurrentDirectory()}/config.json"

        private fun getCurrentDirectory(): String {
            val path = File(AppState::class.java.protectionDomain.codeSource.location.toURI().path).parent
            return path
        }

        fun saveConfig() {
            try {
                val json = JSON.toJSONString(AppStore.state)
                File(configPath).writeText(json)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun loadConfig(): AppState {
            try {
                val json = File(configPath).readText()
                return JSON.parseObject(json, AppState::class.java)
            } catch (e: Exception) {
                return AppState()
            }
        }
    }
}
