import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.*
import store.AppStore
import utils.LogUtil
import java.awt.*

//val log = LogUtil.create("Main")

fun main() = application {

    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        Dialog(Frame(), e.message ?: "Error").apply {
            layout = FlowLayout()
            val label = Label(e.message)
            add(label)
            val button = Button("OK").apply {
                addActionListener { dispose() }
            }
            add(button)
            setSize(300,300)
            isVisible = true
        }
    }

    throw Error("Test error")

//    log.info("App start")

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
            state.mainWindowVisible = false
            state.trayState?.sendNotification(Notification(state.appName, "已最小化到系统托盘"))
        },
        visible = state.mainWindowVisible,
        icon = icon,
        title = state.appName,
        resizable = false,
    ) {
        App()
    }
}
