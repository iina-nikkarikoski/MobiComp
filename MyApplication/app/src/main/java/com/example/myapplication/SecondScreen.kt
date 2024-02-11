package com.example.myapplication

import android.R
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.io.File




@Composable
fun SecondScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    var text by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf<Uri?>(null) }
    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "Pinkie"
    var savedUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri = it }
    )
    val context = LocalContext.current

    /*val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            uri = saveImageToStorage(context, selectedUri)
        }
    )*/
    /*val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                val copiedFile = imageToStorage(context, context.contentResolver, uri)
                if (copiedFile != null) {
                    Log.d("PhotoPicker", "Image copied to internal storage: ${copiedFile.absolutePath}")
                    state.description.value = copiedFile.absolutePath
                    selectedImage = copiedFile
                }

                state.description.value?.let {filePath ->
                    val profilePic = File(filePath)
                    Image(
                        painter = rememberImagePainter(profilePic),
                        contentDescription = "Profile picture",

                    )
                }
            }
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
                painter = rememberAsyncImagePainter(uri),
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
                //savedUri = saveImageToStorage(context, uri)
                val user = User(name = text, picture = uri.toString())
                viewModel.insert(user)
                //uri = savedUri
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
/*fun saveImageToStorage(context: Context, imageUri: Uri?): Uri? {
    if (imageUri == null) {
        return null
    }

    // Create a unique filename for the image
    val filename = "user_image_${System.currentTimeMillis()}.jpg"

    try {
        // Open an output stream to the app's files directory
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
            // Get the input stream from the image URI
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                // Copy the image data from the input stream to the output stream
                inputStream.copyTo(outputStream)
            }
        }

        // Return the saved image URI using FileProvider
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", File(context.filesDir, filename))
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}*/

/*fun loadImageFromStorage(context: Context, imageUri: Uri?): Painter {
    return rememberDrawablePainter(imageUri)
}*/

/*fun saveImageToStorage(context: Context, imageUri: Uri?): Uri? {
    if (imageUri == null) {
        return null
    }

    // Create a unique filename for the image
    val filename = "user_image_${System.currentTimeMillis()}.jpg"

    // Open an output stream to the app's files directory
    val outputStream: OutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)

    // Get the input stream from the image URI
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)

    // Copy the image data from the input stream to the output stream
    inputStream?.copyTo(outputStream)

    // Close the streams
    inputStream?.close()
    outputStream.close()

    Log.d("Saving image", "image saved to database")

    // Return the saved image URI
    return Uri.fromFile(File(context.filesDir, filename))
}*/

/*@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController())
}*/