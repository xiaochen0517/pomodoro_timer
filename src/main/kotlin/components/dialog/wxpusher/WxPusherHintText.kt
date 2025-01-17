package components.dialog.wxpusher

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import store.AppStore
import java.awt.Desktop
import java.net.URI

@Preview
@Composable
fun WxPusherHintText() {

    val state = AppStore.state
    val scope = rememberCoroutineScope()
    val url = "https://wxpusher.zjiecode.com/wxuser/?type=1&id=82894#/follow"

    Row {
        Text(
            text = "请点击",
            style = TextStyle(
                fontSize = 14.sp,
            ),
        )
        ClickableText(
            text = buildAnnotatedString {
                append(" 此链接 ")
            },
            // cursor on hover
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            // text color and underline, hover effect
            style = TextStyle(
                color = Color.Blue,
                fontSize = 14.sp,
            ),
            onClick = {
                // Open the browser
                if (Desktop.isDesktopSupported() && Desktop.getDesktop()
                        .isSupported(Desktop.Action.BROWSE)
                ) {
                    Desktop.getDesktop().browse(URI(url))
                } else {
                    scope.launch {
                        state.snackbarHostState.showSnackbar(message = "无法打开浏览器")
                    }
                }
            }
        )
        Text(
            text = "关注公众号并获取UID",
            style = TextStyle(
                fontSize = 14.sp,
            ),
        )
    }
}
