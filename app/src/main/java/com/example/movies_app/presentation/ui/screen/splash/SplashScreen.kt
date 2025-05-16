package com.example.movies_app.presentation.ui.screen.splash



import android.content.Context
import android.content.SharedPreferences
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.movies_app.R
import androidx.compose.ui.res.painterResource
import com.example.movies_app.presentation.ui.navgation.Screen

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scale = remember { Animatable(0f) }

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

        if (PreferencesHelper.isOnboardingCompleted(context)) {
            navController.navigate(Screen.Login_Signup.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Onboarding1.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        }
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
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .height(400.dp)
                .width(400.dp)
        )
    }
}


object PreferencesHelper {
    private const val PREF_NAME = "app_prefs"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    fun setOnboardingCompleted(context: Context, isCompleted: Boolean) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, isCompleted).apply()
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }
}