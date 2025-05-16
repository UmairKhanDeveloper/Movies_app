package com.example.movies_app.presentation.ui.screen.login_signup

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.navgation.Screen

@Composable
fun Login_Signup(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFF1F1D2B)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.live_tv),
            contentDescription = null,
            modifier = Modifier
                .height(88.dp)
                .width(88.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "CINEMAX",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 28.sp
        )
        Text(
            text = buildAnnotatedString {
                append("Enter your registered\n")
                append("Phone Number to Sign Up")
            },
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF92929D),
            fontSize = 14.sp,textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                      navController.navigate(Screen.SignUpScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00CFE8),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .height(56.dp)
                .width(300.dp)
        ) {
            Text(text = "Sign Up", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AlreadyHaveAccountText({ navController.navigate(Screen.LoginScreen.route) })
        Spacer(modifier = Modifier.height(20.dp))
        SocialSignUpSection(onAppleClick = {}, onFacebookClick = {}, onGoogleClick = {})
    }
}

@Composable
fun AlreadyHaveAccountText(onLoginClick: () -> Unit) {
    val loginText = "Login"
    val fullText = "I already have an account? $loginText"

    val annotatedText = buildAnnotatedString {
        append("I already have an account? ")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF00CFE8),
                fontWeight = FontWeight.Bold
            )
        ) {
            append(loginText)
        }
        addStringAnnotation(
            tag = "LOGIN",
            annotation = "login",
            start = fullText.indexOf(loginText),
            end = fullText.length
        )
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations("LOGIN", offset, offset)
                .firstOrNull()?.let {
                    onLoginClick()
                }
        },
        style = TextStyle(
            color = Color(0xFF8E8E93),
            fontSize = 14.sp
        )
    )
}


@Composable
fun SocialSignUpSection(
    onGoogleClick: () -> Unit,
    onAppleClick: () -> Unit,
    onFacebookClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(10.dp)
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            Text(
                text = "  Or Sign up with  ",
                color = Color(0xFF8E8E93),
                fontSize = 14.sp
            )
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            SocialButton(R.drawable.ic_google, Color.White, onClick = onGoogleClick)
            SocialButton(R.drawable.ic_apple, Color(0xFF1C1C1E), onClick = onAppleClick)
            SocialButton(R.drawable.ic_facebook, Color(0xFF4267B2), onClick = onFacebookClick)
        }
    }
}

@Composable
fun SocialButton(@DrawableRes imageRes: Int, backgroundColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(69.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
