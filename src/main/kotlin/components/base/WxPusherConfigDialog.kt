package components.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import store.AppStore
import utils.WxPusherUtil


@Composable
fun WxPusherConfigDialog() {
    val state = AppStore.state

    val scope = rememberCoroutineScope()

    var currentWxPusherUID by remember { mutableStateOf("") }
    currentWxPusherUID = state.wxPusherUID

    AnimatedVisibility(
        visible = state.wxPusherDialogVisible,
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
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "微信推送配置",
                        // bold style
                        style = androidx.compose.ui.text.TextStyle(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    )
                    // Dialog content
                    TextField(
                        value = currentWxPusherUID,
                        onValueChange = {
                            currentWxPusherUID = it
                        },
                        label = { Text("WxPusher UID") },
                        modifier = Modifier.width(400.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(16.dp, 0.dp, 16.dp, 4.dp)),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    TextButton(
                        onClick = {
                            if (currentWxPusherUID.isEmpty()) {
                                scope.launch {
                                    state.snackbarHostState.showSnackbar(message = "请填写UID后再发送测试")
                                }
                                return@TextButton
                            }
                            // 使用IO线程发送测试消息，避免阻塞UI线程
                            scope.launch(Dispatchers.IO) {
                                WxPusherUtil.sendNotification("测试", "这是一条测试消息", uid = currentWxPusherUID)
                            }
                        },
                        contentPadding = PaddingValues(24.dp, 2.dp),
                    ) {
                        Text("发送测试")
                    }
                    TextButton(
                        onClick = {
                            currentWxPusherUID = ""
                            state.wxPusherDialogVisible = false
                        },
                        contentPadding = PaddingValues(24.dp, 2.dp),
                    ) {
                        Text("取消")
                    }
                    TextButton(
                        onClick = {
                            if (currentWxPusherUID.isNotEmpty()) {
                                state.saveWxPusherUID(currentWxPusherUID)
                                state.wxPusherDialogVisible = false
                            } else {
                                scope.launch {
                                    state.snackbarHostState.showSnackbar(message = "请填写UID")
                                }
                            }
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
