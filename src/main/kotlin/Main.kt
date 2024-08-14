import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.*
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
            state.trayState?.sendNotification(Notification(state.appName, "已最小化到系统托盘",))
        },
        visible = state.mainWindowVisible,
        icon = icon,
        title = state.appName,
        resizable = false,
    ) {
        App()
    }
}
