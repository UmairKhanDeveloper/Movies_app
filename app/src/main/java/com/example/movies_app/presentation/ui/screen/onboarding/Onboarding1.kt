package com.example.movies_app.presentation.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies_app.R

@Composable
fun Onboarding1() {
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
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(30.dp))
                .size(width = 327.dp, height = 341.dp)
                .background(color = Color(0XFF1F1D2B)),
        ) {
            Column(modifier = Modifier
                .fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "The biggest international\n and local film streaming",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Semper in cursus magna et eu\n varius nunc adipiscing. Elementum\n justo, laoreet id sem semper\n parturient. ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

            }

        }

    }
}
