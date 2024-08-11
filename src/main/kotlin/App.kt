import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.*
import service.CountDownService
import store.AppStore
import views.HomeView
import views.SettingsView

@Composable
@Preview
fun App() {

    val state = AppStore.state

    var currentView by remember { mutableStateOf("home") }

    MaterialTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = state.snackbarHostState) }
        ) {
            CountDownService(state)
            Crossfade(targetState = currentView) { viewInfo ->
                when (viewInfo) {
                    "home" -> HomeView(
                        state = state,
                        changeView = { newView -> currentView = newView }
                    )

                    "settings" -> SettingsView(
                        state = state,
                        changeView = { newView -> currentView = newView }
                    )
                }
            }
        }
    }

}
