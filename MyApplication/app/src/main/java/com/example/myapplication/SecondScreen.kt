package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.io.File
import java.util.UUID


@Composable
fun SecondScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "Pinkie"
    var profilePic = allUsers.lastOrNull()?.picture.toString()

    var text by remember { mutableStateOf("") }
    var filename: String
    var path by remember { mutableStateOf("") }

    val context = LocalContext.current

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { newUri: Uri? ->
            profilePic = newUri.toString()
            filename = UUID.randomUUID().toString()

            newUri?.let {
                val inputStream = context.contentResolver.openInputStream(newUri)
                val outputFile = context.filesDir.resolve(filename)
                inputStream?.copyTo(outputFile.outputStream())
                path = outputFile.toString()
            }
        }
    )

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
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                painter = rememberAsyncImagePainter(profilePic),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Change name") }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                val user = User(name = text, picture = profilePic)
                viewModel.insert(user)
                Log.d("Saving user", "User saved to database")
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "SAVE")
        }

        Text(
            text = "Text from the database: $lastUserName",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

/*@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController())
}*/