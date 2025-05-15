package com.example.movies_app.presentation.ui.navgation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding1
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding2
import com.example.movies_app.presentation.ui.screen.onboarding.Onboarding3
import com.example.movies_app.presentation.ui.screen.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination =Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route){ SplashScreen(navController)}
        composable(Screen.Onboarding1.route){ Onboarding1()}
        composable(Screen.Onboarding2.route){ Onboarding2() }
        composable(Screen.Onboarding3.route){ Onboarding3() }




    }

}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object SplashScreen : Screen("SplashScreen", "SplashScreen", icon = Icons.Default.Settings)
    object Onboarding : Screen("Onboarding", "Onboarding", icon = Icons.Default.Settings)
    object Onboarding1 : Screen("Onboarding1", "Onboarding1", icon = Icons.Default.Settings)
    object Onboarding2 : Screen("Onboarding2", "Onboarding2", icon = Icons.Default.Settings)
    object Onboarding3 : Screen("Onboarding3", "Onboarding3", icon = Icons.Default.Settings)

}

//@Composable
//fun BottomNavigation(navController: NavHostController) {
//    val items = listOf(
//        Screen.HomeScreen,
//        Screen.ProgressScreen,
//        Screen.DownloadScreen
//    )
//
//    NavigationBar {
//        val navStack by navController.currentBackStackEntryAsState()
//        val current = navStack?.destination?.route
//
//        items.forEach {
//            val isSelected = current == it.route
//            val contentColor = if (isSelected) Color(0XFFff8379) else Color.Gray
//
//            NavigationBarItem(
//                selected = isSelected,
//                onClick = {
//                    navController.navigate(it.route) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                    }
//                },
//                icon = {
//                    Image(
//                        painter = painterResource(id = it.icon),
//                        contentDescription = "",
//                        modifier = Modifier.size(25.dp),
//                        colorFilter = ColorFilter.tint(contentColor)
//                    )
//                },
//                label = {
//                    if (isSelected) {
//                        Text(text = it.title, color = contentColor)
//                    }
//                }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White)
//            )
//        }
//    }
//
//
//}
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun NavEntry() {
//    val navController = rememberNavController()
//    Scaffold(bottomBar = {
//        BottomNavigation(navController = navController)
//    }) {
//        Navigation(navController = navController )
//    }
//
//}