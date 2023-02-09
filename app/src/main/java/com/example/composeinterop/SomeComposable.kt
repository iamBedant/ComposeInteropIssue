package com.example.composeinterop

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SomeComposable (
    modifier: Modifier = Modifier
) {
    LazyColumn {
        item {
            Button(onClick = {  }) {
                Text(
                    text = "Click Me",
                )
            }

            Spacer(modifier = modifier.height(20.dp))
        }
        item {
            Text(
                text = "This is a sample text for demo purpose",
            )
        }
        item {
            Text(
                text = "This is a sample text for demo purpose",
            )
        }
    }
}