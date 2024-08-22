package utils

import java.io.Serializable

class LogUtil(val name: String = "") : Serializable {

    companion object {

        fun create(name: String): LogUtil {
            return LogUtil(name)
        }

    }

    private fun print(logType: LogType, message: String) {
        val logTypeStr = padStart(logType.name, 5)
        val logName = padStart(this.name, 10)
        println("${getCurrentTime()} $logTypeStr ${getThreadInfoStr()} $logName - $message")
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
