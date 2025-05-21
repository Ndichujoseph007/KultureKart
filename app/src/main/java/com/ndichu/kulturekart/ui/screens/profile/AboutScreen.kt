package com.ndichu.kulturekart.ui.screens.profile

import android.R.color.transparent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.ui.components.SectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(modifier: Modifier = Modifier,
                navController: NavController
) {
    val gold = Color(0xFFFFD700)
    val background = painterResource(id = R.drawable.background)
    val GoldDark = Color(0xFFB8860B) // Dark goldenrod hex color


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = background,
                contentScale = ContentScale.Crop
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = "About Us",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
       
        // Who We Are
            SectionCard(title = "Who We Are", titleColor = GoldDark) {
                Text(
                    "KultureKart is a digital marketplace born from a deep respect for heritage and craftsmanship. " +
                            "We connect passionate creators of cultural artifacts with people who value tradition, artistry, and identity."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Why We Started
            SectionCard(title = "Why We Started", titleColor = GoldDark) {
                Text(
                    "Our journey began with a simple question: how can we keep culture alive in a digital age? " +
                            "Seeing many artisans struggle to access buyers, we created KultureKart to empower them and share their work with the world."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // What We Offer
            SectionCard(title = "What We Offer", titleColor = GoldDark) {
                Text(
                    "Whether you’re a collector, a curious explorer, or a cultural entrepreneur, KultureKart offers tools to help you discover, trade, " +
                            "and appreciate authentic cultural products—from jewelry and masks to textiles and musical instruments."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Our Values
            SectionCard(title = "Our Values", titleColor = GoldDark) {
                Text(
                    "We believe in fair trade, storytelling through art, and empowering communities by keeping traditions alive through commerce."
                )
            }

            Spacer(modifier = Modifier.height(24.dp))



            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "© 2025 KultureKart. All rights reserved.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Contact: support@kulturekart.app",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    val navController = rememberNavController()
    AboutScreen(navController = navController)
}