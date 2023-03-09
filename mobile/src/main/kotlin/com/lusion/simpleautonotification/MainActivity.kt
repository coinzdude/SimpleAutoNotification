@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package com.lusion.simpleautonotification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lusion.simpleautonotification.LusionUtility.triggerNotification

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            SGTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    MainLayout()
                }
            }
        }
    }

    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
//        viewModel.inputs.onTurnOnNotificationsClicked(granted)
    }

    public override fun onResume() {
        super.onResume()

        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)


        // attach the receiver so we can do UI updates based on events sent from utility classes.
            triggerNotification(
                applicationContext,
            )
            return
        }
    }

    @Composable
    fun MainLayout() {
        Row(verticalAlignment = Alignment.Bottom)
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            )
            {
                Column() {
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainLayoutPreview() {
        MainLayout()
    }

