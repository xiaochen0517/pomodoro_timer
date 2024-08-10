package components.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import store.AppState
import java.awt.Cursor

@Composable
fun ControlBar(state: AppState, changeView: (String) -> Unit) {

    val startButtonText = if (state.startCountDown) "暂停" else "开始"

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = {
                state.setCountDownFlag(!state.startCountDown)
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
        ) {
            if (state.startCountDown) {
                Icon(Icons.Filled.Stop, contentDescription = "暂停")
            } else {
                Icon(Icons.Default.PlayArrow, contentDescription = "开始")
            }
            Text(startButtonText)
        }

        Button(
            onClick = {
                println("点击设置按钮")
                changeView("settings")
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR))),
        ) {
            Icon(Icons.Default.Settings, contentDescription = "设置")
        }

        Button(
            onClick = {
                println("点击时间配置按钮")
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR))),
        ) {
            Icon(Icons.Default.Timer, contentDescription = "时间配置")
        }
    }
}

@Preview
@Composable
fun ControlBarPreview() {
    ControlBar(AppState()) {}
}
