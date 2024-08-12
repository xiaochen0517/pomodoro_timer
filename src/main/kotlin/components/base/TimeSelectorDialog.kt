package components.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import store.AppStore
import utils.CountDownType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectorDialog() {
    val state = AppStore.state
    var currentCountDownType by remember { mutableStateOf(state.countDownType) }

    AnimatedVisibility(
        visible = state.showTimeSelectorDialog,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0f, 0f, 0f, 0.2f)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.End,
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(16.dp, 16.dp, 16.dp, 0.dp)),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            "选择番茄时钟配置", style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            "（分钟/短休息/长休息）", style = TextStyle(
                                color = Color.Gray
                            )
                        )
                    }
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    ) {
                        SegmentedButton(
                            selected = currentCountDownType == CountDownType.SHORT,
                            onClick = {
                                currentCountDownType = CountDownType.SHORT
                            },
                            shape = RoundedCornerShape(topStart = 48.dp, bottomStart = 48.dp)
                        ) {
                            Text("15/3/15")
                        }
                        SegmentedButton(
                            selected = currentCountDownType == CountDownType.MEDIUM,
                            onClick = {
                                currentCountDownType = CountDownType.MEDIUM
                            },
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text("25/5/20")
                        }
                        SegmentedButton(
                            selected = currentCountDownType == CountDownType.LONG,
                            onClick = {
                                currentCountDownType = CountDownType.LONG
                            },
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text("35/7/25")
                        }
                        SegmentedButton(
                            selected = currentCountDownType == CountDownType.EXTRA_LONG,
                            onClick = {
                                currentCountDownType = CountDownType.EXTRA_LONG
                            },
                            shape = RoundedCornerShape(topEnd = 48.dp, bottomEnd = 48.dp)
                        ) {
                            Text("50/10/30")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(16.dp, 0.dp, 16.dp, 4.dp)),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {

                    TextButton(
                        onClick = {
                            state.showTimeSelectorDialog = false
                        },
                        contentPadding = PaddingValues(24.dp, 2.dp),
                    ) {
                        Text("取消")
                    }
                    TextButton(
                        onClick = {
                            state.showTimeSelectorDialog = false
                            state.resetCountDown(currentCountDownType)
                        },
                        contentPadding = PaddingValues(24.dp, 2.dp),
                    ) {
                        Text("确定")
                    }
                }
            }
        }
    }
}
