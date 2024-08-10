package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.base.TimeSelectorDialog
import components.home.ControlBar
import components.home.TimerCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import store.AppState


@Composable
fun HomeView(state: AppState, changeView: (String) -> Unit) {

    LaunchedEffect(state.startCountDown) {
        if (state.startCountDown) {
            while (state.leftTime > 0) {
                delay(1000L)
                state.leftTime--
            }
            state.startCountDown = false
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimerCard(state)
            ControlBar(state, changeView)
        }

        TimeSelectorDialog(state)
    }

    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                state.snackbarHostState.showSnackbar("Snackbar")
            }
        }
    ) {
        Text("Show Snackbar")
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeView(AppState()) {}
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
