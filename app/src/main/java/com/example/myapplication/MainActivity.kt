package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

import GameScreenInitial
import ProfileScreenInitial
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
    @Composable
    fun MainScreen() {
        val navController = rememberNavController ()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NavigationGraph (navController)
        }
    }
    @Composable
    fun NavigationGraph(navController: NavHostController) {
        val animDur = 500
        NavHost( navController = navController, startDestination = "ProfileScreenInitial"){
            composable(
                "GameScreenInitial/{numberOfColors}",
                arguments = listOf( navArgument( "numberOfColors" ) { type = NavType. IntType}),
                enterTransition = {
                    fadeIn()+
                            slideIntoContainer(
                                towards =  AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(animDur, easing=EaseOut)
                            )
                },
                exitTransition = {
                    fadeOut()+
                            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(animDur, easing=EaseIn))
                }

            ) { backStackEntry ->
                val numberOfColors = backStackEntry.arguments?.getInt("numberOfColors")!!
                GameScreenInitial(numberOfColors,
                    onGoBackButtonClicked ={
                            navController.popBackStack()
                    },
                    onGoToScreen3ButtonClicked = {score->
                        navController.navigate("ResultScreenInitial/$score")
                    }

                )
            }
            composable(
                "ProfileScreenInitial",
                enterTransition = {
                    fadeIn()+
                    slideIntoContainer(
                        towards =  AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(animDur, easing=EaseOut)
                    )
                },
                exitTransition = {
                    fadeOut()+
                            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(animDur, easing=EaseIn))
                }
            ) { backStackEntry ->
                ProfileScreenInitial(
                    onNavButtonClicked={numberOfColors->
                        navController.navigate("GameScreenInitial/$numberOfColors")
                    }
                )
            }
            composable(
                //na końcu trasy można dodawać argumenty opcjonalne
                "ResultScreenInitial/{score}",
                arguments = listOf(
                    navArgument("score") { type = NavType.IntType }
                ),
                enterTransition = {
                    fadeIn()+
                    slideIntoContainer(
                        towards =  AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(animDur, easing=EaseOut)
                    )
                },
                exitTransition = {
                    fadeOut()+
                            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(animDur, easing=EaseIn))
                }
            ) { backStackEntry ->
                val score = backStackEntry.arguments?.getInt("score")!!
                ResultScreen(
                    score=score,
                    onRestartGameClicked = {
                        navController.popBackStack()
                },
                    onLogoutClicked = {
                        navController.popBackStack("ProfileScreenInitial", inclusive = false)
                    }
                )
            }
        }
    }

}








//@Preview
//@Composable
//fun ProfileScreenInitialPreview() {
//    MyApplicationTheme {
//        ProfileScreenInitial()
//    }
//}