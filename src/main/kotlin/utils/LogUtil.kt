package utils

import java.io.File
import java.io.Serializable

class LogUtil(val name: String = "") : Serializable {

    companion object {

        fun create(name: String): LogUtil {
            // create log file
            createLogFile()
            return LogUtil(name)
        }

        private fun createLogFile(): File {
            // check path exists
            val logPath = File(PathUtil.logPath)
            if (!logPath.exists()) {
                logPath.mkdirs()
            }
            // create log file
            val logFile = File(PathUtil.logPath + "log-${DateUtil.getNowSimpleDate()}.log")
            // check if log file exists
            if (!logFile.exists()) {
                logFile.createNewFile()
            }
            return logFile
        }

    }

    private fun print(logType: LogType, message: String) {
        val logTypeStr = padStart(logType.name, 5)
        val logName = padStart(this.name, 10)
        val printLog = "${getCurrentTime()} $logTypeStr ${getThreadInfoStr()} $logName - $message"
        println(printLog)
        try {
            // write log to file in append mode
            File(PathUtil.logPath + "log-${DateUtil.getNowSimpleDate()}.log").appendText("$printLog\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getThreadInfoStr(): String {
        val currentThread = Thread.currentThread()
        val threadId = padStart(currentThread.threadId().toString(), 5)
        val threadName = padStart(currentThread.name, 20)
        val threadGroup = padStart(currentThread.threadGroup.name, 5)
        return "$threadId --- [$threadName] $threadGroup:"
    }

    fun info(message: String) {
        print(LogType.INFO, message)
    }

    fun debug(message: String) {
        print(LogType.DEBUG, message)
    }

    fun warn(message: String) {
        print(LogType.WARN, message)
    }

    fun error(message: String) {
        print(LogType.ERROR, message)
    }

}

private fun getCurrentTime(): String {
    val date = java.util.Date()
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(date)
}

private fun padStart(str: String, length: Int): String {
    if (str.length >= length) {
        return str.substring(0, length)
    }
    return str.padStart(length, ' ')
}
