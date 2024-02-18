package com.example.myapplication

import android.Manifest
import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


@Composable
fun SecondScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "Pinkie"
    var profilePic = allUsers.lastOrNull()?.picture.toString()
    val notificationService = NotificationService(LocalContext.current)
    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var text by remember { mutableStateOf("") }
    var filename: String
    var path by remember { mutableStateOf("") }
    var isPopupVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val controller = remember {LifecycleCameraController(applicationContext)}

    val gyroscopeListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val rotationY = event.values[1]
            if (rotationY > 1) {
                notificationService.showRotationNotification(rotationY)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            gyroscopeListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(gyroscopeListener)
        }
    }

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    val hasCameraPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    val cameraResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                // Update profilePic with the taken picture
                //viewModel.updateProfilePic()
            } else {
                // Handle the case when the picture was not successfully taken
            }
        }
    )

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            hasNotificationPermission = isGranted
            if (isGranted) {
                notificationService.showBasicNotification()
            } else {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(hasNotificationPermission) {
        if (hasNotificationPermission) {
            notificationService.showBasicNotification()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            //photoPicker.launch(MediaStore.ACTION_IMAGE_CAPTURE)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

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
            .zIndex(1f)
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        // Popup content
        AnimatedVisibility(
            visible = isPopupVisible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .zIndex(0f)
                    .padding(10.dp, 40.dp),
                Alignment.TopCenter
            ) {
                Row {
                    Button(
                        onClick = {
                            photoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                            isPopupVisible = false
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("From Gallery")
                    }
                    Button(
                        onClick = {
                            if (!hasCameraPermission) {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            } else {
                                cameraResultLauncher.launch(null)
                            }
                            isPopupVisible = false
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text("From Camera")
                    }
                }

            }
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
                        isPopupVisible = true
                    }),
                painter = rememberAsyncImagePainter(profilePic),
                //painter = painterResource(R.drawable.pinkie),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Change name") }
            )

            Button(
                onClick = {
                    if (!hasNotificationPermission) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        notificationService.showBasicNotification()
                    }
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = "Enable notifications")
            }
        }
    }

    Box(
        modifier = Modifier
            .zIndex(2f)
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

        /*Text(
            text = "Text from the database: $lastUserName",
            modifier = Modifier.padding(top = 16.dp)
        )*/
    }
}

@Composable
fun getOutputMediaFileUri(context: Context): Uri {
    val mediaStorageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "YourAppDirectoryName")
    if (!mediaStorageDir.exists()) {
        mediaStorageDir.mkdirs()
    }
    val filename = "${UUID.randomUUID()}.jpg"
    val file = File(mediaStorageDir, filename)

    // Save the captured image to the file
    val outputStream = FileOutputStream(file)
   //val takenImage = getCameraImage(context) // You'll need to implement this function to get the taken image
    //takenImage?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    // Add the image to the gallery
    addImageToGallery(context, file)

    return Uri.fromFile(file)
}

private fun addImageToGallery(context: Context, file: File) {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        put(MediaStore.Images.Media.DATA, file.absolutePath)
    }

    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
}

@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController(), viewModel = viewModel())
}