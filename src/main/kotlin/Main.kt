import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import store.AppStore

fun main() = application {

    val icon = painterResource("icon.png")
    var windowVisible by remember { mutableStateOf(true) }

    val trayState = rememberTrayState()
    Tray(
        state = trayState,
        onAction = {
            windowVisible = true
        },
        icon = icon,
        menu = {
            Item("Show") {
                windowVisible = true
            }
            Item("Exit") {
                exitApplication()
            }
        }
    )
    AppStore.state.trayState = trayState

    Window(
        onCloseRequest = {
            // close windows but keep the app running
            println("Window closed")
            windowVisible = false
        },
        visible = windowVisible,
        icon = icon,
        title = "番茄时钟（Pomodoro Timer）",
        resizable = false,
    ) {
        App()
    }
}
