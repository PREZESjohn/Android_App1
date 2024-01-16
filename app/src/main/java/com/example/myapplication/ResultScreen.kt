package com.example.myapplication

import android.app.LauncherActivity
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.models.PlayerWithScore
import com.example.myapplication.providers.AppViewModelProvider
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodels.ProfileViewModel
import com.example.myapplication.viewmodels.ResultViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun ResultScreen(score: Int, onRestartGameClicked: () -> Unit, onLogoutClicked: () -> Unit,
                 //viewModel: ResultViewModel = viewModel(factory = AppViewModelProvider.Factory)
                 viewModel: ResultViewModel = hiltViewModel<ResultViewModel>()
) {
    var playersFlow=viewModel.loadPlayersScores()
    var playersScores by remember {
        mutableStateOf(emptyList<PlayerWithScore>())
    }
    LaunchedEffect(playersFlow){
        playersFlow.collect{newPlayers ->
            playersScores=newPlayers
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Results",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        Text(
            text = "Recent score: ${(score) }",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ItemList(playersScores = playersScores)
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {onRestartGameClicked()},
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                Text(text = "Restart game")
            }
            Button(
                onClick = {onLogoutClicked()},
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                Text(text = "Logout")
            }
        }
    }
}
@Composable
fun ItemRow(playerScore: PlayerWithScore) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Text(text = playerScore.playerName, style = MaterialTheme.typography.displayMedium)
            Text(text = playerScore.scoreValue.toString(), style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun ItemList(playersScores: List<PlayerWithScore>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().height(350.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 24.dp)
    ){
        items(playersScores) { item ->
            ItemRow(playerScore = item)
            Divider(color = Color.Gray, thickness = 2.dp)
        }
    }
}
@Preview
@Composable
fun ResultScreenInitialPeview(){
    MyApplicationTheme {
        ResultScreen(
            score = 6,
            onRestartGameClicked = {true},
            onLogoutClicked = {true}
        )
    }
}