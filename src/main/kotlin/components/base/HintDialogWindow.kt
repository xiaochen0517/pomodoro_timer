package components.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import store.AppStore
import utils.TimerType

@Composable
fun HintDialogWindow() {
    val state = AppStore.state
    if (!state.hintWindowVisible) {
        return
    }
    DialogWindow(
        onCloseRequest = {
            state.hintWindowVisible = false
        },
        visible = state.hintWindowVisible,
        resizable = false,
        alwaysOnTop = true,
        title = "提示",
        state = DialogState(position = WindowPosition(Alignment.Center))
    ) {
        val hintText = if (state.currentTimerType == TimerType.WORK) {
            "休息时间结束"
        } else {
            "工作时间结束"
        }
        MaterialTheme {
            Scaffold {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "info",
                        tint = Color(0xFFD97706),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        hintText,
                        modifier = Modifier.padding(PaddingValues(0.dp, 0.dp, 0.dp, 16.dp)),
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Button(
                        onClick = {
                            state.hintWindowVisible = false
                        }
                    ) {
                        Text("确定")
                    }
                }
            }
        }
    }
}
