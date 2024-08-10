package components.base

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import store.AppState
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectorDialog(state: AppState) {

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    BasicAlertDialog(
        modifier = Modifier.wrapContentSize(),
        onDismissRequest = { state.showTimeSelectorDialog = false },
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
            ) {
                TimeInput(
                    state = timePickerState,
                )
            }
            Row(
                modifier = Modifier
                    .padding(paddingValues = PaddingValues(4.dp, 0.dp, 4.dp, 4.dp)),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {

                TextButton(
                    onClick = {
                        state.showTimeSelectorDialog = false
                    },
                    contentPadding = PaddingValues(24.dp, 2.dp),
                ) {
                    Text("取消")
                }
                TextButton(
                    onClick = {
                        state.showTimeSelectorDialog = false
                        state.leftTime = (timePickerState.hour * 60 + timePickerState.minute).toLong()
                    },
                    contentPadding = PaddingValues(24.dp, 2.dp),
                ) {
                    Text("确定")
                }
            }
        }
    }
}

@Preview
@Composable
fun TimeSelectorDialogPreview() {
    TimeSelectorDialog(AppState())
}
