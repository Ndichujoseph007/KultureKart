package com.ndichu.kulturekart.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.ui.components.SectionCard

@Composable
fun AboutScreen(modifier: Modifier = Modifier,
                navController: NavController
) {
    val gold = Color(0xFFFFD700)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SectionCard(title = "Want to Buy or Sell", titleColor = gold) {


            // Who We Are
            SectionCard(title = "Who We Are", titleColor = gold) {
                Text(
                    "KultureKart is a digital marketplace born from a deep respect for heritage and craftsmanship. " +
                            "We connect passionate creators of cultural artifacts with people who value tradition, artistry, and identity."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Why We Started
            SectionCard(title = "Why We Started", titleColor = gold) {
                Text(
                    "Our journey began with a simple question: how can we keep culture alive in a digital age? " +
                            "Seeing many artisans struggle to access buyers, we created KultureKart to empower them and share their work with the world."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // What We Offer
            SectionCard(title = "What We Offer", titleColor = gold) {
                Text(
                    "Whether you’re a collector, a curious explorer, or a cultural entrepreneur, KultureKart offers tools to help you discover, trade, " +
                            "and appreciate authentic cultural products—from jewelry and masks to textiles and musical instruments."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Our Values
            SectionCard(title = "Our Values", titleColor = gold) {
                Text(
                    "We believe in fair trade, storytelling through art, and empowering communities by keeping traditions alive through commerce."
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social Media Section
            Text(
                text = "Connect With Us",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = gold,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* TODO: Open Instagram */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = "Instagram",
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Open X */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = "X",
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Open WhatsApp */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Open Facebook */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Facebook",

                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { /* TODO: Open LinkedIn */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_linkeddin),
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

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
}
