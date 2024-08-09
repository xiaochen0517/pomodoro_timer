import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import views.HomeView

fun main() = application {

    val icon = painterResource("icon.png")

    Tray(
        icon = icon,
        menu = {
            Item("Exit") {
                exitApplication()
            }
        }
    )

    Window(
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = "Pomodoro Timer"
    ) {
        HomeView()
    }
}
