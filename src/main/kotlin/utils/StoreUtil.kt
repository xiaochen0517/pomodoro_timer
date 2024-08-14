package utils

import com.alibaba.fastjson2.JSON
import store.AppState
import store.AppStore
import java.io.File
import java.util.logging.Logger

class StoreUtil {
    companion object {

        private val log = Logger.getLogger(StoreUtil::class.java.name)

        private val configPath = "${getCurrentDirectory()}/config.json"

        private fun getCurrentDirectory(): String {
            val path = File(AppState::class.java.protectionDomain.codeSource.location.toURI().path).parent
            println(path)
            return path
        }

        fun saveConfig() {
            try {
                val json = JSON.toJSONString(AppStore.state)
                File(configPath).writeText(json)
                log.info("write config file: $json")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun loadConfig(): AppState {
            try {
                val json = File(configPath).readText()
                log.info("read config file: $json")
                return JSON.parseObject(json, AppState::class.java)
            } catch (e: Exception) {
                return AppState()
            }
        }
    }
}
