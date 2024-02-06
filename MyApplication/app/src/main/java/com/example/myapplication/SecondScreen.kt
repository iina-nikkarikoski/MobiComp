package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter



@Composable
fun SecondScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    var text by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf<Uri?>(null) }
    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "DefaultUserName"

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri = it }
    )
    /*val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            // Store the image URI in the app's data directory
            StoreImageUri(context, uri)
        }
    )*/

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Magenta)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.Black, CircleShape)
                    .clickable(onClick = {
                        photoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }),
                painter = if(uri == null){
                     painterResource(R.drawable.pinkie)
                } else {
                    rememberAsyncImagePainter(uri)
                },
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                /*onValueChange = { newText ->
                    // Update the text value as it changes
                    text = newText

                    // Automatically save to the database
                    val user = User(name = newText, picture = uri.toString())
                    viewModel.insert(user)
                },*/
                label = { Text("Change name")}
            )
        }
    }


    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = {
                val user = User(name = text, picture = uri.toString())
                viewModel.insert(user)
                Log.d("Saving user", "User saved to database")

                /*// Fetch the stored image URI
                val storedImageUri = fetchStoredImageUri()

                // Create a User object with the name and stored image URI
                val user = User(name = text, picture = storedImageUri?.toString())

                // Insert the user into the database
                viewModel.insert(user)*/
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "SAVE")
        }

        //Conversation(navController = navController, messages = SampleData.conversationSample, lastUserName)

        Text(
            text = "Text from the database: $lastUserName",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Function to store the image URI
/*@Composable
private fun StoreImageUri(context: Context, uri: Uri?) {
    uri?.let {
        val fileName = "stored_image.txt"
        // Write the image URI to a file in the app's internal storage
        LocalContext.current.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
            output.write(uri.toString().toByteArray())
        }
    }
}

// Function to fetch the stored image URI
@Composable
private fun fetchStoredImageUri(): Uri? {
    val fileName = "stored_image.txt"
    var imageUri: Uri? = null

    // Read the image URI from the file in the app's internal storage
    LocalContext.current.openFileInput(fileName).use { input ->
        val bytes = ByteArray(input.available())
        input.read(bytes)
        val uriString = String(bytes)
        imageUri = Uri.parse(uriString)
    }

    return imageUri
}*/

/*@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController())
}*/