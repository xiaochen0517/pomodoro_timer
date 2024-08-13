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
    val trayState = rememberTrayState()
    val state = AppStore.state
    state.trayState = trayState
    Tray(
        state = state.trayState!!,
        onAction = {
            state.mainWindowVisible = true
        },
        icon = icon,
        menu = {
            Item("Show") {
                state.mainWindowVisible = true
            }
            Item("Exit") {
                exitApplication()
            }
        }
    )

    Window(
        onCloseRequest = {
            // close windows but keep the app running
            println("Window closed")
            state.mainWindowVisible = false
        },
        visible = state.mainWindowVisible,
        icon = icon,
        title = "番茄时钟（Pomodoro Timer）",
        resizable = false,
    ) {
        App()
    }
}
