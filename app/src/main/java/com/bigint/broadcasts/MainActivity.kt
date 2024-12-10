package com.bigint.broadcasts

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bigint.broadcasts.receivers.InternetChangeReceiver
import com.bigint.broadcasts.ui.theme.BroadcastsAndroidTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BroadcastsAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Broadcasts receivers app",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val notificationPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(Unit) {
        if (!notificationPermissionState.status.isGranted) {
            notificationPermissionState.launchPermissionRequest()
        }
    }
    // Main Container for Greeting and Internet Status
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Greeting Text with enhanced styling
        Text(
            text = "$name!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Info Section with subtle background and rounded corners
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Internet Status",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                InternetStatusScreen() // Displays the status of the internet connection
            }
        }
    }
}

@Composable
fun InternetStatusScreen() {
    val context = LocalContext.current
    val isInternetAvailable = remember { mutableStateOf(true) }

    // Define the callback for Internet status changes
    val onInternetStatusChanged: (Boolean) -> Unit = { isAvailable ->
        isInternetAvailable.value = isAvailable
    }

    // Register the receiver dynamically
    DisposableEffect(Unit) {
        val receiver = InternetChangeReceiver(onInternetStatusChanged)
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)

        // Register the receiver
        context.registerReceiver(receiver, intentFilter)

        // Cleanup: Unregister the receiver when the composable leaves the composition
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    // UI: Display the Internet status
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isInternetAvailable.value) "Internet Available" else "Internet Unavailable",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadcastsAndroidTheme {
        Greeting("Android")
    }
}