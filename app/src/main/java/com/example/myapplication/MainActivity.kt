package com.example.myapplication


import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues


import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.LinkedList


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ProfileScreenInitial();
                    GameScreenInitial();
                }
            }
        }
    }
}
@Composable
fun GameScreenInitial() {
    val availableColors= listOf(Color.Blue, Color.Yellow, Color.Cyan, Color.Black, Color.Gray, Color.Red,Color.Magenta,Color.Green,
        Color.DarkGray, Color.White)
    val backgroundColor=Color.White
    val choosenColors= remember { mutableStateListOf<Color>(backgroundColor, backgroundColor,backgroundColor,backgroundColor) }
    val correctColors = selectRandomColors(availableColors)

    var choosenColorsList= remember {mutableStateListOf<List<Color>>()}
    var infoColorsList: LinkedList<List<Color>> = LinkedList()
    var gamePlaying = remember {mutableStateOf(false)}
    var gameOver = remember {mutableStateOf(false)}
    val rounds = remember {mutableStateOf(0)}

    Column{

        val colorListInitial= listOf(Color.White,Color.White,Color.White,Color.White)
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Your score: ${(choosenColorsList.size)+1 }", // jak dodam tu wyswietlenie jakiej kolwiek zmiennej remember to od nowa inicjuje zmienne i sie psuje wyswieltanie gamerow
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
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(10.dp)
                            .background(Color.Cyan)

                    ) {
                        Text("Start over")
                    }
                }

            }
       }
    }
}
@Composable
fun SelectableColorsRow(colorList: List<Color>,clickable:Boolean, onClick:(Int)->Unit){
    Row(horizontalArrangement =
            Arrangement.spacedBy(5.dp)) {
        for (index in colorList.indices){
            CircularButton(onClick ={onClick(index)} , color = colorList[index], clickable=clickable)
        }
    }
}

@Composable
fun CircularButton(onClick: ()->Unit,color: Color = Color.White, clickable: Boolean) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .size(50.dp)
            .background(color = MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = color,
            contentColor =MaterialTheme.colorScheme.onBackground),



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

    val selectedColorsSet = choosenColors.toSet()
    val selectedColorForButton = choosenColors[buttonNumber]
    val availableColorsWithoutSelected = availableColors.filter { !selectedColorsSet.contains(it) }
    val currentIndex = availableColors.indexOf(selectedColorForButton)
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
                returnList[index1]=Color.Red
            }else{
                returnList[index1]=Color.Yellow
            }
        }else{
            returnList[index1]=backgroundColor
        }
    }
    return returnList
//    return TODO("Provide the return value")
}



@Composable
private fun ProfileImageWithPicker(profileImageUri: Uri?, selectImageOnClick: () -> Unit) {

    if(profileImageUri==null){
        Image(
                    //wymaga dodania ikony w katalogu /res/drawable
                    //(prawy przycisk | New | Vector asset)
                painter = painterResource(
                    id = R.drawable.ic_baseline_question_mark_24),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,)
    }else{
        AsyncImage(
            model = profileImageUri,
            contentDescription = "Profile image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }

//    IconButton(
//                onClick = selectImageOnClick,
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape),
//            ) {
//                //Icon(Icons.Rounded.AccountBox , contentDescription ="Profile photo" )
//
//            }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OutlinedTextFieldWithError(
    modifier: Modifier = Modifier.fillMaxWidth(),
    label: String,
    value: MutableState<String>,
    onValueChange: (String) -> Unit,
    isError: (String) -> Boolean,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }


        OutlinedTextField(
            value = value.value,
            onValueChange = {
                onValueChange(it)
                isFocused = true
            },
            singleLine = true,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            isError = isError(value.value),
            modifier = modifier,
            supportingText = {
//                if (isError(value.value) && isFocused) {
//                    Text(text=errorMessage)
//                }
            },
            trailingIcon = {
                if (isError(value.value) && isFocused)
                    Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
            },
        )

            if (isError(value.value) && isFocused) {
                //Row(modifier=Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = errorMessage, modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxHeight())
                //}
            }else{
                //Row(modifier=Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "", modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxHeight())
                //}
            }
            //Text(text=errorMessage)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenInitial() {
    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val colorsNum = rememberSaveable{ mutableStateOf("") }
    val profileImageUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            if (selectedUri != null) {
                profileImageUri.value = selectedUri
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        Box {
//            Image(
//                    //wymaga dodania ikony w katalogu /res/drawable
//                    //(prawy przycisk | New | Vector asset)
//                painter = painterResource(
//                    id = R.drawable.ic_baseline_question_mark_24),
//                    contentDescription = "Profile photo",
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(CircleShape),
//                    contentScale = ContentScale.Crop,)
            ProfileImageWithPicker(profileImageUri = profileImageUri.value, selectImageOnClick = {
                imagePicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            })
            IconButton(
                onClick = { //lambda obsługująca kliknięcie
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Filled.AccountBox, contentDescription ="Profile photo"  )
            }
        }

//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = name.value,
//            onValueChange = { name.value = it },
//            label = { Text("Name") },
//            singleLine = true,
//            isError = false,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//            supportingText = { Text("Name can't be empty") }
//        )
        OutlinedTextFieldWithError(
            label = "Name",
            value = name,
            onValueChange ={name.value=it},
            isError ={TextUtils.isEmpty(it)},
            errorMessage = "Name cannot be empty"
        )
        OutlinedTextFieldWithError(
            label = "Email",
            value = email,
            onValueChange ={email.value=it},
            isError ={!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()},
            errorMessage = "Email is not correct"
        )
        OutlinedTextFieldWithError(
            label = "Colors",
            value = colorsNum,
            onValueChange ={colorsNum.value=it},
            isError =
            {!(it.toIntOrNull() in 5..10)
                     },
            keyboardType = KeyboardType.Decimal,
            errorMessage = "Number of colors must be a digit and be between 5 and 10"
        )
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            ) {
            Text(text = "Next")
        }
    }
}


@Preview
@Composable
fun GameScreenInitialPeview(){
    MyApplicationTheme {
        GameScreenInitial()
    }
}

//@Preview
//@Composable
//fun ProfileScreenInitialPreview() {
//    MyApplicationTheme {
//        ProfileScreenInitial()
//    }
//}