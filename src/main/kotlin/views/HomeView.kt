package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.base.TimeSelectorDialog
import components.home.ControlBar
import components.home.TimerCard
import utils.LogUtil

val log = LogUtil.create("HomeView")

@Composable
@Preview
fun HomeView() {
    log.info("App start")
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimerCard()
                ControlBar()
            }
        }
        TimeSelectorDialog()
    }
}

/**
 * 通过秒数计算分钟数和秒数
 */
fun calculateTime(time: Long): Pair<String, String> {
    val minutes = time / 60
    val seconds = time % 60
    return Pair(
        minutes.toString().padStart(2, '0'), seconds.toString().padStart(2, '0')
    )
}
