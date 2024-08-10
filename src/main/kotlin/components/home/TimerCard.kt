package components.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
                .padding(paddingValues = PaddingValues(48.dp, 12.dp, 48.dp, 12.dp)),
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
        }
    }

}

@Preview
@Composable
fun TimerCardPreview() {
    TimerCard(AppState())
}
