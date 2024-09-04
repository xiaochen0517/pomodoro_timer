package utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import store.AppStore
import store.SerializableAppState
import java.io.File

class StoreUtil {
    companion object {

        private val log = LogUtil.create("StoreUtil")
        private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        fun saveConfig() {
            try {
                val json = gson.toJson(AppStore.state.getSerializableAppState())
                log.debug("save config: $json")
                File(PathUtil.configPath).writeText(json)
            } catch (e: Exception) {
                e.printStackTrace()
                log.error("save config error: ${e.message}")
            }
        }

        fun loadConfig(): SerializableAppState {
            try {
                val json = File(PathUtil.configPath).readText()
                return gson.fromJson(json, SerializableAppState::class.java)
            } catch (e: Exception) {
                log.error("load config error: ${e.message}")
                return SerializableAppState()
            }
        }
    }
}
