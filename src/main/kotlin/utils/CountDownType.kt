package utils

import model.TimerInfo

enum class CountDownType {

    SHORT,

    MEDIUM,

    LONG,

    EXTRA_LONG;

    fun getTimerInfo(): TimerInfo {
        return when (this) {
            SHORT -> TimerInfo(15, 3, 15)
            MEDIUM -> TimerInfo(25, 5, 20)
            LONG -> TimerInfo(35, 7, 25)
            EXTRA_LONG -> TimerInfo(50, 10, 30)
        }
    }
}
