package com.example.movies_app.presentation.ui.navgation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies_app.R
import com.example.movies_app.presentation.ui.screen.download.DownloadScreen
import com.example.movies_app.presentation.ui.screen.home.HomeScreen
import com.example.movies_app.presentation.ui.screen.login_signup.Createnewpassword.CreateNewPassword
import com.example.movies_app.presentation.ui.screen.login_signup.Login.LoginScreen
import com.example.movies_app.presentation.ui.screen.login_signup.Login_Signup.Login_Signup
import com.example.movies_app.presentation.ui.screen.login_signup.ResetPassword.ResetPassword
import com.example.movies_app.presentation.ui.screen.login_signup.Sign_Up.SignUpScreen
import com.example.movies_app.presentation.ui.screen.login_signup.Verification.Verification
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding1
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding2
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding3
import com.example.movies_app.presentation.ui.screen.profile.ProfileScreen
import com.example.movies_app.presentation.ui.screen.search.SearchScreen
import com.example.movies_app.presentation.ui.screen.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination =Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route){ SplashScreen(navController)}
        composable(Screen.Onboarding1.route){ Onboarding1(navController)}
        composable(Screen.Onboarding2.route){ Onboarding2(navController) }
        composable(Screen.Onboarding3.route){ Onboarding3(navController) }
        composable(Screen.Login_Signup.route){ Login_Signup(navController) }
        composable(Screen.LoginScreen.route){ LoginScreen(navController) }
        composable(Screen.SignUpScreen.route){ SignUpScreen(navController) }
        composable(Screen.ResetPassword.route){ ResetPassword(navController) }
        composable(Screen.Verification.route){ Verification(navController) }
        composable(Screen.CreateNewPassword.route){ CreateNewPassword(navController) }
        composable(Screen.HomeScreen.route){ HomeScreen(navController) }
        composable(Screen.SearchScreen.route){ SearchScreen(navController) }
        composable(Screen.DownloadScreen.route){ DownloadScreen(navController) }
        composable(Screen.ProfileScreen.route){ ProfileScreen(navController) }






    }

}

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int) {
    object SplashScreen : Screen("SplashScreen", "SplashScreen", R.drawable.ic_home)
    object Onboarding1 : Screen("Onboarding1", "Onboarding1", R.drawable.ic_splash)
    object Onboarding2 : Screen("Onboarding2", "Onboarding2", R.drawable.ic_splash)
    object Onboarding3 : Screen("Onboarding3", "Onboarding3", R.drawable.ic_splash)
    object Login_Signup : Screen("Login_Signup", "Login_Signup", R.drawable.ic_splash)
    object LoginScreen : Screen("LoginScreen", "LoginScreen", R.drawable.ic_splash)
    object SignUpScreen : Screen("SignUpScreen", "SignUpScreen", R.drawable.ic_splash)
    object ResetPassword : Screen("ResetPassword", "ResetPassword", R.drawable.ic_splash)
    object Verification : Screen("Verification", "Verification", R.drawable.ic_splash)
    object CreateNewPassword : Screen("CreateNewPassword", "CreateNewPassword", R.drawable.ic_splash)
    object HomeScreen : Screen("HomeScreen", "Home", R.drawable.ic_home)
    object SearchScreen : Screen("SearchScreen", "Search", R.drawable.ic_search)
    object DownloadScreen : Screen("DownloadScreen", "Download", R.drawable.ic_download)
    object ProfileScreen : Screen("ProfileScreen", "Profile", R.drawable.account)
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.SearchScreen,
        Screen.DownloadScreen,
        Screen.ProfileScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF1F1D2B),
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            val iconColor = if (isSelected) Color(0xFF00CFE8) else Color.Gray

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (isSelected) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.title,
                                tint = iconColor,
                            )
                            if (screen.title.isNotEmpty()) {
                                Text(
                                    text = screen.title,
                                    color = iconColor,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.title,
                            tint = iconColor,

                        )
                    }
                },
                label = {  },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF252836),
                    selectedIconColor = Color(0xFF00CFE8),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavEntry() {
    val navController = rememberNavController()
    var showBottomNav by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    showBottomNav = when {
        currentRoute == null -> true
        currentRoute.contains(Screen.SplashScreen.route) -> false
        currentRoute.contains(Screen.Onboarding1.route) -> false
        currentRoute.contains(Screen.Onboarding2.route) -> false
        currentRoute.contains(Screen.Onboarding3.route) -> false
        currentRoute.contains(Screen.Login_Signup.route) -> false
        currentRoute.contains(Screen.LoginScreen.route) -> false
        currentRoute.contains(Screen.SignUpScreen.route) -> false
        currentRoute.contains(Screen.ResetPassword.route) -> false
        currentRoute.contains(Screen.Verification.route) -> false
        currentRoute.contains(Screen.CreateNewPassword.route) -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Navigation(navController, )
    }
}