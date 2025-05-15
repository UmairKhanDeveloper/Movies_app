package com.example.movies_app.presentation.ui.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.navgation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 0.6f,
            animationSpec = tween(
                delayMillis = 700,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(2000)
        navController.navigate(Screen.Onboarding1.route)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFF1F1D2B)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "",
            modifier = Modifier
                .scale(scale.value)
                .align(Alignment.CenterHorizontally)
                .height(400.dp)
                .width(400.dp)
        )



    }

}