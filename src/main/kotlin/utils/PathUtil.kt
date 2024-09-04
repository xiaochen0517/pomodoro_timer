package utils

import store.AppState
import java.io.File

class PathUtil {

    companion object {

        private val currentPath = getCurrentDirectory()

        val configPath = "$currentPath/config.json"

        val logPath = "$currentPath/logs/"

        private fun getCurrentDirectory(): String {
            val path = File(AppState::class.java.protectionDomain.codeSource.location.toURI().path).parent
            return path
        }
    }
}
