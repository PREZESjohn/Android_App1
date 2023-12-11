import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.LinkedList

@Composable
fun GameScreenInitial(
    numberOfColors: Int,
    onGoBackButtonClicked: () -> Boolean,
    onGoToScreen3ButtonClicked: (Any) -> Unit
) {
    val colors = listOf(
        Color.Blue, Color.Yellow, Color.Cyan, Color.Black, Color.Gray, Color.Red,
        Color.Magenta,
        Color.Green,
        Color.DarkGray, Color.White)
    val availableColors= colors.take(numberOfColors)
    val backgroundColor= Color.White
    val choosenColors= remember { mutableStateListOf<Color>(backgroundColor, backgroundColor,backgroundColor,backgroundColor) }
    var correctColors = selectRandomColors(availableColors)

    var choosenColorsList= remember { mutableStateListOf<List<Color>>() }
    var infoColorsList: LinkedList<List<Color>> = LinkedList()
    var gamePlaying = remember { mutableStateOf(false) }
    var gameOver = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally){

        val colorListInitial= listOf(Color.White, Color.White, Color.White, Color.White)
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Your score: ${(infoColorsList.size) }",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(bottom = 48.dp)
                )
            }
            items(choosenColorsList.size) { rowNumber ->
                GameRow(choosenColors = choosenColorsList[rowNumber].toMutableList(), infoColors = infoColorsList[rowNumber],
                    click = false,
                    onSelectColorClick = {},
                    onCheckClick = {  })
            }
            item {
                if(!gameOver.value){
                    GameRow(choosenColors = choosenColors, infoColors = colorListInitial, click = gamePlaying.value,
                        onSelectColorClick = {
                                index->selectNextAvailableColor(availableColors,choosenColors,index)
                            gamePlaying.value=true             },
                        onCheckClick = {
                            val infoColor = checkColors(choosenColors, correctColors, backgroundColor)
                            infoColorsList.add(infoColor)
                            choosenColorsList.add(choosenColors.toList())
                            if(correctColors==choosenColors.toList()){
                                gameOver.value=true
                            }
                        })
                }else{
                    Button(
                        onClick = {
                            choosenColors.replaceAll { backgroundColor }
                            correctColors = selectRandomColors(availableColors)
                            
                            onGoToScreen3ButtonClicked(infoColorsList.size)

                            choosenColorsList.clear()
                            infoColorsList.clear()
                            gamePlaying.value=false
                            gameOver.value=false
                        },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("High score table")
                    }
                }

            }
        }

        Button(
            onClick = {onGoBackButtonClicked()},
            modifier = Modifier
                .padding(top = 20.dp),

        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun SelectableColorsRow(colorList: List<Color>, clickable:Boolean, onClick:(Int)->Unit){
    Row(horizontalArrangement =
    Arrangement.spacedBy(5.dp)) {
        for (index in colorList.indices){
            CircularButton(onClick ={onClick(index)} , color = colorList[index], clickable=clickable)
        }
    }
}

@Composable
fun CircularButton(onClick: ()->Unit, color: Color = Color.White, clickable: Boolean) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .size(50.dp)
            .background(color = MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = color,
            contentColor = MaterialTheme.colorScheme.onBackground),



        ) {}

}
@Composable
fun SmallCircle(color: Color){
    Box(modifier = Modifier
        .clip(CircleShape)
        .size(20.dp)
        .background(color = color)
        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)

    )
}

@Composable
fun FeedbackCircles(colorList: List<Color>){
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colorList[0])
            SmallCircle(color = colorList[1])
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colorList[2])
            SmallCircle(color = colorList[3])
        }
    }
}

@Composable
fun GameRow(choosenColors: MutableList<Color>, infoColors: List<Color>, click: Boolean,
            onSelectColorClick: (Int)->Unit, onCheckClick:()->Unit){

    Row (
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(vertical = 5.dp)){
        SelectableColorsRow(choosenColors, clickable= click,
            onClick = { index -> onSelectColorClick(index) })
        IconButton(onClick = { onCheckClick() },
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            colors = IconButtonDefaults.filledIconButtonColors(),
            enabled = click) {
            Icon(Icons.Filled.Check, contentDescription ="Check"  )
        }
        FeedbackCircles(infoColors)
    }

}

fun selectNextAvailableColor(availableColors: List<Color>, choosenColors: MutableList<Color>, buttonNumber: Int){

    val selectedColorsSet = choosenColors.toSet().toMutableSet()
    val selectedColorForButton = choosenColors[buttonNumber]

    selectedColorsSet.remove(selectedColorForButton)

    val availableColorsWithoutSelected = availableColors.filter { !selectedColorsSet.contains(it) }
    val currentIndex = availableColorsWithoutSelected.indexOf(selectedColorForButton)
    val nextIndex = (currentIndex + 1) % availableColorsWithoutSelected.size
    choosenColors[buttonNumber] = availableColorsWithoutSelected[nextIndex]

}
fun selectRandomColors(availableColors: List<Color>): List<Color>{
    return availableColors.shuffled().distinct().take(4)
}
fun checkColors(choosenColors: MutableList<Color>, correctColors:List<Color>, backgroundColor: Color): List<Color>{
    var returnList = mutableListOf<Color>(backgroundColor,backgroundColor,backgroundColor, backgroundColor)
    for(index1 in choosenColors.indices) {
        if(correctColors.contains(choosenColors[index1])){
            val index2 = correctColors.indexOf(choosenColors[index1])
            if(index1==index2){
                returnList[index1]= Color.Red
            }else{
                returnList[index1]= Color.Yellow
            }
        }else{
            returnList[index1]=backgroundColor
        }
    }
    return returnList
//    return TODO("Provide the return value")
}

@Preview
@Composable
fun GameScreenInitialPeview(){
    MyApplicationTheme {
        GameScreenInitial(
            numberOfColors = 6,
            onGoBackButtonClicked = {true},
            onGoToScreen3ButtonClicked = {true}
        )
    }
}