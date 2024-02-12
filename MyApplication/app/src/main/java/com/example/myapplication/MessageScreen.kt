package com.example.myapplication

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter


data class Message(val author: String, val body: String)

/*val profilePicture = if (message.author.isNotEmpty()) {
    // Use a custom profile picture if the author property is not empty
    Uri.parse()
} else {
    // Use a default profile picture if the author property is empty
    Uri.parse("android.resource://com.example.myapplication/${R.drawable.pinkie}")
}*/

@Composable
fun Settings(title: String, onSettingsClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Magenta)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun MessageScreen(msg: Message, latestUserName: String?,  key: String, viewModel: UserViewModel) {

    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    var profilePic = allUsers.lastOrNull()?.picture.toString()
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, Color.Black, CircleShape),
            //painter = painterResource(R.drawable.pinkie),
            //painter = rememberAsyncImagePainter(profilePicture),
            painter = rememberAsyncImagePainter(profilePic),
            /*if(profilePic == null){
                painterResource(R.drawable.pinkie)
            } else {

            },*/
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "",
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = latestUserName ?: msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Composable
fun Conversation(navController: NavController, messages: List<Message>, viewModel: UserViewModel) {
    val allUsers by viewModel.allUsers.observeAsState(emptyList())
    val lastUserName = allUsers.lastOrNull()?.name ?: "Pinkie"
    Column {
        Settings(title = "Conversation", onSettingsClick = {navController.navigate(route = Screen.SecondScreen.route)})
        LazyColumn {
            items(messages) { message ->
                val uniqueKey = "${message.author}_${message.hashCode()}"
                /*if (lastUserName?.isEmpty() == true) {
                    MessageScreen(message, "Pinkie", uniqueKey)
                }*/
                MessageScreen(message, lastUserName, uniqueKey, viewModel)
            }
        }
        /*LazyColumn {
            items(messages) { message ->
                // Concatenate the author's name with a unique identifier to create a distinct key
                val uniqueKey = "${message.author}_${message.hashCode()}"
                MessageScreen(message, uniqueKey)
            }
        }*/
    }
}

/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleData {
    // Sample conversation data

    val conversationSample = listOf(
        Message(
            "Pinkie",
            "Test...Test...Test..."
        ),
        Message(
            "Pinkie",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        Message(
            "Pinkie",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        Message(
            "Pinkie",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Pinkie",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        Message(
            "Pinkie",
            "It's available from API 21+ :)"
        ),
        Message(
            "Pinkie",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Pinkie",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Pinkie",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Pinkie",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Pinkie",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Pinkie",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Pinkie",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}