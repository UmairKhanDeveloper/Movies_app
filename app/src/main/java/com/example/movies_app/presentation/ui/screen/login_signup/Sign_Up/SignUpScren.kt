package com.example.movies_app.presentation.ui.screen.login_signup

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.navgation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            },
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(32.dp)
                        .background(Color(0xFF252836)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1F1D2B))
        )
    }) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(Color(0xFF1F1D2B))
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Let’s get started",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "The latest movies and series are here",
                fontSize = 12.sp,
                color = Color(0xFFEBEBEF),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(70.dp))
            StyledNameTextField2(username = username, onNameChange = { username = it })
            Spacer(modifier = Modifier.height(20.dp))
            StyledEmailTextField2(email = email, onEmailChange = { email = it })
            Spacer(modifier = Modifier.height(20.dp))

            StyledPasswordTextField2(
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(8.dp))


            var checked by remember { mutableStateOf(false) }

            TermsAndPrivacyCheckbox(
                checked = checked,
                onCheckedChange = { checked = it },
                onTermsClick = { },
                onPrivacyClick = { }
            )



            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D1FF)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Login", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledPasswordTextField2(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Password",
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        TextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = {
                Text(text = "enter your password", color = Color.Gray)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility",
                        tint = Color.LightGray
                    )
                }
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF1F1D2B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(BorderStroke(1.dp, Color(0XFF92929D)), shape = RoundedCornerShape(50))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledEmailTextField2(
    email: String,
    onEmailChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Email Address",
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        TextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = {
                Text(text = "enter your email", color = Color.Gray)
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF1F1D2B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(BorderStroke(1.dp, Color(0XFF92929D)), shape = RoundedCornerShape(50))
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledNameTextField2(
    username: String,
    onNameChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Username",
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        TextField(
            value = username,
            onValueChange = onNameChange,
            placeholder = {
                Text(text = "enter your name", color = Color.Gray)
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF1F1D2B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(BorderStroke(1.dp, Color(0XFF92929D)), shape = RoundedCornerShape(50))
        )

    }
}

@Composable
fun TermsAndPrivacyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.White,
                checkedColor = Color(0xFF00D1FF)
            )
        )

        Spacer(modifier = Modifier.width(4.dp))

        val annotatedString = buildAnnotatedString {
            append("I agree to the ")

            pushStringAnnotation(tag = "TERMS", annotation = "terms")
            withStyle(style = SpanStyle(color = Color.Cyan)) {
                append("Terms and Services")
            }
            pop()

            append(" and ")

            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
            withStyle(style = SpanStyle(color = Color.Cyan)) {
                append("Privacy Policy")
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onTermsClick()
                    }
                annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onPrivacyClick()
                    }
            },
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
    }
}