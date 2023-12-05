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
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.LinkedList

import GameScreenInitial
import ProfileScreenInitial
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


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
        NavHost( navController = navController, startDestination = "ProfileScreenInitial"){
            composable(
                //parametr podaje się w ścieżce/trasie
                "GameScreenInitial/{numberOfColors}",
                //na liście określamy typy poszczególnych argumentów
                arguments = listOf( navArgument( "numberOfColors" ) { type = NavType. IntType})
            ) { backStackEntry ->
                //tutaj odczytujemy wartość parametru przekazanego ze Screen1
                val numberOfColors =
                    backStackEntry.arguments?.getInt("numberOfColors")!!
                //dodajemy Screen2 i przekazujemy do niego wartość parametru
                GameScreenInitial(numberOfColors,
                    //obsługa zdarzeń związanych z nawigacją została „wyciągnięta”
                    //tutaj (ze względu na dostęp do kontrolera nawigacji)
                    //powrót ze Screen2 do Screen1
                    onGoBackButtonClicked =
                    { replyForScreen1 ->
                        //ustawienie „wyniku” wykonania Screen2 (wynik pobierze
                        //Screen1)
                        navController.previousBackStackEntry?.savedStateHandle?.set("reply",replyForScreen1)
                        //powrót tam skąd przyszliśmy
                        navController.popBackStack()
                    },

                //przejście ze Screen2 do Screen3
                    onGoToScreen3ButtonClicked = {
//nawigacja do Screen3 i przekazanie parametrów (Screen3 ma
                        //dwa obowiązkowe i jeden opcjonalny argument)

                        //przekazanie konkretnej wartości parametru opcjonalnego
                        //navController.navigate("screen3/1str/2?optionalArgument=3")
                        //użycie wartości domyślnej parametru opcjonalnego
                        navController.navigate("screen3/1str/2")
                    }

                )
            }
            composable(
                "ProfileScreenInitial",
            ) { backStackEntry ->
                //odbieranie wartości zwróconej przez Screen2
                val replyFromScreen2 =
                    backStackEntry. savedStateHandle
                        .get<String>( "reply" ) ?: "???"
                //dodanie Screen1 i przekazanie parametrów
                ProfileScreenInitial(
                    //nawigacja do Screen2 z przekazaniem parametru
                    onNavButtonClicked={numberOfColors->
                        navController.navigate("GameScreenInitial/$numberOfColors")
                    }

                )
            }
            composable(
                //na końcu trasy można dodawać argumenty opcjonalne
                "screen3/{argument1}/{argument2}?optionalArgument={optionalArgument}",
                arguments = listOf(
                    navArgument("argument1") { type = NavType.StringType },
                    navArgument("argument2") { type = NavType.IntType },
                    //argument opcjonalny musi być nullowalny lub mieć
                    //wartość domyślną
                    navArgument( "optionalArgument" ) {
                        type = NavType. IntType ; defaultValue = - 1 })
            ) { backStackEntry ->
                val argument1FromScreen2 =
                    backStackEntry.arguments?.getString("argument1")!!
                val argument2FromScreen2 =
                    backStackEntry.arguments?.getInt("argument2")!!
                //odczytanie argumentu opcjonalnego
                val optiomanArgumentFromScreen2 =
                    backStackEntry. arguments ?.getInt( "optionalArgument")
                ResultScreen(onButtonClicked = {
                    //powrót tam skąd przyszliśmy
                    navController.navigateUp()
                })
            }
        }
    }

}






@Preview
@Composable
fun GameScreenInitialPeview(){
    MyApplicationTheme {
    }
}

//@Preview
//@Composable
//fun ProfileScreenInitialPreview() {
//    MyApplicationTheme {
//        ProfileScreenInitial()
//    }
//}