package com.example.movies_app.presentation.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.navgation.Screen
import com.example.movies_app.presentation.ui.screen.splash.PreferencesHelper

@Composable
fun Onboarding1(navController: NavController) {
    val context= LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFF1F1D2B))
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_first_mage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(30.dp))
                .size(width = 327.dp, height = 341.dp)
                .background(color = Color(0XFF1F1D2B)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "The biggest international\n and local film streaming",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Semper in cursus magna et eu\n varius nunc adipiscing. Elementum\n justo, laoreet id sem semper\n parturient. ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0XFF92929D),textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OnboardingIndicators(currentPage = 0)
                    Image(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 80.dp, height = 80.dp)
                            .clickable {
                                PreferencesHelper.setOnboardingCompleted(context, true)
                                navController.navigate(Screen.Onboarding2.route) {
                                    popUpTo(Screen.Onboarding1.route) { inclusive = true }
                                }

                            }
                    )
                }


            }

        }

    }
}
@Composable
fun OnboardingIndicators(currentPage: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in 0..2) {
            if (i == currentPage) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF00FFFF))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2D9CDB))
                )
            }
        }
    }
}

