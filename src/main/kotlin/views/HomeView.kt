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
import java.awt.Cursor


@Composable
@Preview
fun HomeView() {
    var startButtonText by remember { mutableStateOf("开始计时") }
    var startCountDown by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(1500L) }

    LaunchedEffect(startCountDown) {
        if (startCountDown) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft -= 1
            }
            startCountDown = false
        }
    }

    MaterialTheme {
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
                            val timeInfo = calculateTime(timeLeft)
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
                            if (startCountDown) {
                                startButtonText = "继续计时"
                                startCountDown = false
                            } else {
                                startButtonText = "暂停计时"
                                startCountDown = true
                            }
                        },
                        modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                    ) {
                        Text(startButtonText)
                    }

                    Button(
                        onClick = {
                            println("点击了设置按钮")
                        },
                        modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR))),
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "设置")
                    }
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
