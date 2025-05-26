package com.example.movies_app.presentation.ui.screen.login_signup.Sign_Up

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.navgation.Screen
import com.example.movies_app.firebase.AuthRepositoryImpl
import com.example.movies_app.firebase.AuthUser
import com.example.movies_app.firebase.AuthViewModel
import com.example.movies_app.firebase.ResultState
import com.example.movies_app.realtime_database.RealTimeDbRepository
import com.example.movies_app.realtime_database.RealTimeUser
import com.example.movies_app.realtime_database.RealTimeViewModel
import com.example.movies_app.presentation.ui.screen.splash.PreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val authRepo = AuthRepositoryImpl(firebaseAuth, context)
    val authViewModel = AuthViewModel(authRepo)

    val databaseReference = FirebaseDatabase.getInstance().reference.child("your_node")
    val dbRepository = remember { RealTimeDbRepository(databaseReference, context) }
    val dbViewModel = remember { RealTimeViewModel(dbRepository) }

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var checked by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Sign Up",
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
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .background(Color(0xFF1F1D2B))
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Let’s get started",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "The latest movies and series are here",
                fontSize = 12.sp,
                color = Color(0xFFEBEBEF)
            )

            Spacer(modifier = Modifier.height(48.dp))

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
            Spacer(modifier = Modifier.height(12.dp))

            TermsAndPrivacyCheckbox(
                checked = checked,
                onCheckedChange = { checked = it },
                onTermsClick = {  },
                onPrivacyClick = {  }
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (!checked) {
                        errorMessage = "You must agree to Terms and Privacy Policy"
                        return@Button
                    }

                    isLoading = true
                    errorMessage = ""

                    scope.launch {
                        authViewModel.createUser(AuthUser(email, password, ""))
                            .collectLatest { result ->
                                when (result) {
                                    is ResultState.Error -> {
                                        errorMessage = "Sign up failed: ${result.error.message}"
                                        isLoading = false
                                    }

                                    ResultState.Loading -> Unit
                                    is ResultState.Success -> {
                                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                                        if (firebaseUser != null) {
                                            dbViewModel.insert(
                                                RealTimeUser.RealTimeItems(
                                                    userName = username,
                                                    email = email,
                                                    password = password
                                                )
                                            ).collectLatest { dbResult ->
                                                when (dbResult) {
                                                    is ResultState.Error -> {
                                                        errorMessage = "Error saving user data: ${dbResult.error.message}"
                                                        isLoading = false
                                                    }

                                                    ResultState.Loading -> Unit
                                                    is ResultState.Success -> {
                                                        FirebaseAuth.getInstance().signOut()
                                                        isLoading = false
                                                        navController.navigate(Screen.LoginScreen.route) {
                                                            popUpTo(Screen.HomeScreen.route) { inclusive = true }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D1FF)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }

    if (isLoading) {
        LoadingDialog()
    }
}

@Composable
fun LoadingDialog(message: String = "Creating account...") {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2A2C36)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = Color(0xFF00D1FF),
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
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
    Column(modifier = Modifier.fillMaxWidth()) {
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
                Text(text = "Enter your password", color = Color.Gray)
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
    Column(modifier = Modifier.fillMaxWidth()) {
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
                Text(text = "Enter your email", color = Color.Gray)
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
    Column(modifier = Modifier.fillMaxWidth()) {
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
                Text(text = "Enter your name", color = Color.Gray)
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



