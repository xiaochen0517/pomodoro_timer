package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import store.AppState
import java.awt.Cursor
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon


fun sendWindowsNotification(title: String, message: String) {
    if (SystemTray.isSupported()) {
        val tray = SystemTray.getSystemTray()
        val trayIcon = TrayIcon(Toolkit.getDefaultToolkit().createImage(""), title)
        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = title

        tray.add(trayIcon)
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO)

        // Remove the tray icon after displaying the message.
        tray.remove(trayIcon)
    } else {
        println("System tray not supported!")
    }
}

@Composable
@Preview
fun HomeView(state: AppState, changeView: (String) -> Unit) {

    var startButtonText by remember { mutableStateOf("开始计时") }

    LaunchedEffect(state.startCountDown) {
        if (state.startCountDown) {
            while (state.leftTime > 0) {
                delay(1000L)
                state.leftTime--
            }
            state.startCountDown = false
            sendWindowsNotification("Pomodoro Timer", "计时结束")
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(48.dp, 12.dp, 48.dp, 12.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        val textStyle = TextStyle(
                            fontSize = TextUnit(48f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold
                        )
                        val timeInfo = calculateTime(state.leftTime)
                        Text(text = timeInfo.first, style = textStyle)
                        Text(text = " : ", style = textStyle)
                        Text(text = timeInfo.second, style = textStyle)
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = {
                        if (state.startCountDown) {
                            startButtonText = "继续计时"
                            state.startCountDown = false
                        } else {
                            startButtonText = "暂停计时"
                            state.startCountDown = true
                        }
                    },
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ) {
                    Text(startButtonText)
                }

                Button(
                    onClick = {
                        println("点击了设置按钮")
                        changeView("settings")
                    },
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR))),
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "设置")
                }
            }
        }
    }
}

/**
 * 通过秒数计算分钟数和秒数
 */
fun calculateTime(time: Long): Pair<String, String> {
    val minutes = time / 60
    val seconds = time % 60
    return Pair(
        minutes.toString().padStart(2, '0'),
        seconds.toString().padStart(2, '0')
    )
}
