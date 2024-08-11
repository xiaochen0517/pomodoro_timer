package utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Notification

enum class TimerType {

    WORK,

    SHORT_BREAK,

    LONG_BREAK;

    fun getIcon(): ImageVector {
        return when (this) {
            WORK -> Icons.Default.Work
            SHORT_BREAK -> Icons.Default.FreeBreakfast
            LONG_BREAK -> Icons.Default.Bed
        }
    }

    fun getNotification(): Notification {
        return when (this) {
            WORK -> Notification("工作时间结束", "休息一下吧")
            SHORT_BREAK -> Notification("短休息结束", "准备好了吗")
            LONG_BREAK -> Notification("长休息结束", "继续加油")
        }
    }

    fun getName(): String {
        return when (this) {
            WORK -> "工作时间"
            SHORT_BREAK -> "短休息"
            LONG_BREAK -> "长休息"
        }
    }

}
