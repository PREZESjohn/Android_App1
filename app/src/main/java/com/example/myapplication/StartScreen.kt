import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R

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
fun ProfileScreenInitial(onNavButtonClicked: (Int)->Unit={}) {

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
            ProfileImageWithPicker(profileImageUri = profileImageUri.value, selectImageOnClick = {
                imagePicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            })
            IconButton(
                onClick = { //lambda obsługująca kliknięcie
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Filled.AccountBox, contentDescription ="Profile photo"  )
            }
        }

        OutlinedTextFieldWithError(
            label = "Name",
            value = name,
            onValueChange ={name.value=it},
            isError ={ TextUtils.isEmpty(it)},
            errorMessage = "Name cannot be empty"
        )
        OutlinedTextFieldWithError(
            label = "Email",
            value = email,
            onValueChange ={email.value=it},
            isError ={!Patterns.EMAIL_ADDRESS.matcher(it).matches()},
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
            onClick = {onNavButtonClicked(5)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        ) {
            Text(text = "Next")
        }
    }
}