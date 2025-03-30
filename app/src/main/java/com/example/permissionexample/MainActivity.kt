package com.example.permissionexample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permissionexample.ui.theme.PermissionExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Button(onClick = {
                            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, context)
                        }) {
                            Text("Request Permission")
                        }
                    }
                }
            }
        }
    }

    private fun askForPermission(permission: String, context: Context) {
        //requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        when {
            ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                Log.d("PERMISSION STUFF", "Permission already granted")
                getAndUseLocation()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, permission
            ) -> {

                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                //showInContextUI(...)
                Log.d("PERMISSION STUFF", "Permission rationale")
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    // https://developer.android.com/training/permissions/requesting#kotlin
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("PERMISSION STUFF", "Permission granted")
                // Permission is granted. Continue the action or workflow in your
                // app.
                getAndUseLocation()
            } else {
                Log.d("PERMISSION STUFF", "Permission not granted")
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }
}

fun getAndUseLocation() {
    // TODO get and use location
}