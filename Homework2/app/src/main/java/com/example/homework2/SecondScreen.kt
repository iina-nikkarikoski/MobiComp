package com.example.homework2

import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage


@Composable
fun SecondScreen(navController: NavHostController) {

    var text by remember { mutableStateOf("PINKIE") }
    var uri by remember { mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri = it }
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
        Column(modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.Black, CircleShape)
                    .clickable(onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }),
                painter = painterResource(R.drawable.pinkie),
                contentDescription = null,
            )

            AsyncImage(model = uri, contentDescription = null)

            /*Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(start = 75.dp),
                text = "PINKIE",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )*/

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Change name")}
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {
            Text(text = "SAVE")
        }
    }
}

@Composable
fun PhotoPicker() {
    var uri by remember { mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri = it }
    )

    Column {
        Button(
            onClick = {
                photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "open gallery")
        }

        AsyncImage(model = uri, contentDescription = null)
    }
}

@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController())
}