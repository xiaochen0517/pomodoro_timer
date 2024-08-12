package components.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import store.AppStore

@Composable
@Preview
fun ControlBar() {
    val state = AppStore.state
    val startButtonText = if (state.startCountDown) "暂停" else "开始"
    val scope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = {
                state.setCountDownFlag(!state.startCountDown)
                scope.launch {
                    state.snackbarHostState.showSnackbar(message = "${startButtonText}倒计时")
                }
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
        ) {
            if (state.startCountDown) {
                Icon(Icons.Filled.Stop, contentDescription = "暂停")
            } else {
                Icon(Icons.Default.PlayArrow, contentDescription = "开始")
            }
            Text(startButtonText)
        }

//        Button(
//            onClick = {
//                changeView("settings")
//            },
//            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
//        ) {
//            Icon(Icons.Default.Settings, contentDescription = "设置")
//        }

        Button(
            onClick = { state.showTimeSelectorDialog = true },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
        ) {
            Icon(Icons.Default.Timer, contentDescription = "时间配置")
        }
    }
}
