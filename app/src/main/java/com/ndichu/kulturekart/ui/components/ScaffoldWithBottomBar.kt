package com.ndichu.kulturekart.ui.components


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun ScaffoldWithBottomBar(
    navController: NavController,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(

    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
