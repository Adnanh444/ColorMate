package com.adnan.colormate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adnan.colormate.R

@Composable
fun HomeScreen() {
    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        CameraScannerScreen(onClose = { showCamera = false })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Color Mate", style = MaterialTheme.typography.headlineLarge)
            Text(text = stringResource(R.string.made_by), style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showCamera = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.scan_color))
            }
        }
    }
}
