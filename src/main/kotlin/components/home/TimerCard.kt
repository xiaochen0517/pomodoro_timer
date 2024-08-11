package components.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import store.AppState
import views.calculateTime

@Composable
fun TimerCard(state: AppState) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = PaddingValues(24.dp, 12.dp, 24.dp, 8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                val textStyle = TextStyle(
                    fontSize = TextUnit(56f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                val timeInfo = calculateTime(state.leftTime)
                Text(text = timeInfo.first, style = textStyle)
                Text(text = " : ", style = textStyle)
                Text(text = timeInfo.second, style = textStyle)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AssistChip(
                    onClick = {
                        // DO NOTHING
                    },
                    label = {
                        Text(state.currentTimerType.getName())
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = state.currentTimerType.getIcon(),
                            contentDescription = state.currentTimerType.getName(),
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
                AssistChip(
                    onClick = {
                        // DO NOTHING
                    },
                    label = {
                        Text("短周期：${state.shortCycleCount}")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FreeBreakfast,
                            contentDescription = "短周期",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
                AssistChip(
                    onClick = {
                        // DO NOTHING
                    },
                    label = {
                        Text("长周期：${state.longCycleCount}")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Bed,
                            contentDescription = "长周期",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerCardPreview() {
    TimerCard(AppState())
}




