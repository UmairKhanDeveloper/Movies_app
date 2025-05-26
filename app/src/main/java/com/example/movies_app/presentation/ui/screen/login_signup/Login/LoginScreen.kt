package com.example.movies_app.presentation.ui.screen.login_signup.Login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val authRepo = AuthRepositoryImpl(firebaseAuth, context)
    val authViewModel = AuthViewModel(authRepo)

    val databaseReference = FirebaseDatabase.getInstance().reference.child("your_node")
    val repository = remember { RealTimeDbRepository(databaseReference, context) }
    val viewModel = remember { RealTimeViewModel(repository) }
    val state = viewModel.res.value

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loginSuccess by remember { mutableStateOf(false) }


    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var forgotEmail by remember { mutableStateOf("") }
    var forgotPasswordMessage by remember { mutableStateOf<String?>(null) }
    var isSendingResetEmail by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (PreferencesHelper.isUserLoggedIn(context)) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    if (loginSuccess) {
        LaunchedEffect(Unit) {
            PreferencesHelper.setUserLoggedIn(context, true)
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text("Login", fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 16.sp)
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
            state.item.forEach {
                Text(
                    text = "Hi, ${it.items.userName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Welcome back! Please enter \nyour details.",
                fontSize = 12.sp,
                color = Color(0xFFEBEBEF),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(70.dp))

            StyledEmailTextField(email = email, onEmailChange = { email = it })
            Spacer(modifier = Modifier.height(20.dp))

            StyledPasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF00D1FF),
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        forgotEmail = email
                        forgotPasswordMessage = null
                        showForgotPasswordDialog = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    errorMessage = ""
                    isLoading = true

                    scope.launch {
                        authViewModel.loginUser(AuthUser(email = email, password = password, ""))
                            .collectLatest { result ->
                                when (result) {
                                    is ResultState.Loading -> isLoading = true
                                    is ResultState.Error -> {
                                        errorMessage = "Login failed: ${result.error.message}"
                                        isLoading = false
                                    }
                                    is ResultState.Success -> {
                                        PreferencesHelper.setUserLoggedIn(context, true)
                                        isLoading = false
                                        loginSuccess = true
                                    }
                                }
                            }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D1FF)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", color = Color.White, fontSize = 16.sp)
                }
            }
        }


        if (showForgotPasswordDialog) {
            AlertDialog(
                onDismissRequest = { showForgotPasswordDialog = false },
                title = { Text("Reset Password", color = Color.White) },
                text = {
                    Column {
                        Text("Enter your email to receive password reset instructions.",color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = forgotEmail,
                            onValueChange = { forgotEmail = it },
                            placeholder = { Text("Email Address") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )
                        forgotPasswordMessage?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it,
                                color = if (it.contains("sent")) Color.Green else Color.Red,
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (forgotEmail.isBlank()) {
                                forgotPasswordMessage = "Please enter your email."
                                return@TextButton
                            }
                            isSendingResetEmail = true
                            firebaseAuth.sendPasswordResetEmail(forgotEmail)
                                .addOnCompleteListener { task ->
                                    isSendingResetEmail = false
                                    if (task.isSuccessful) {
                                        forgotPasswordMessage = "Reset email sent! Please check your inbox."
                                    } else {
                                        forgotPasswordMessage = task.exception?.localizedMessage ?: "Failed to send reset email."
                                    }
                                }
                        },
                        enabled = !isSendingResetEmail
                    ) {
                        if (isSendingResetEmail) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text("Send")
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showForgotPasswordDialog = false }) {
                        Text("Cancel")
                    }
                },containerColor =Color(0XFF171725)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledPasswordTextField(
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
fun StyledEmailTextField(
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

