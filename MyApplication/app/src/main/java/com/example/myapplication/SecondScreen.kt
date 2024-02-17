package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.util.UUID


@Composable
fun SecondScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "Pinkie"
    var profilePic = allUsers.lastOrNull()?.picture.toString()
    val notificationService = NotificationService(LocalContext.current)
    val sensorManager = LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var text by remember { mutableStateOf("") }
    var filename: String
    var path by remember { mutableStateOf("") }

    val context = LocalContext.current

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
        sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)

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
                //painter = rememberAsyncImagePainter(profilePic),
                painter = painterResource(R.drawable.pinkie),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Change name") }
            )

            Button(onClick = {
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

/*@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    SecondScreen(navController = rememberNavController(), viewModel = viewModel())
}*/