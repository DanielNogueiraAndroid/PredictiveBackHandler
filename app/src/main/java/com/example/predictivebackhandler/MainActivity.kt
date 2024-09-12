package com.example.predictivebackhandler

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.predictivebackhandler.ui.theme.PredictiveBackHandlerTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MainActivity : ComponentActivity() {
    private val list = listOf(1, 2, 3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictiveBackHandlerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    LaunchedEffect("start") {
                        MainScope().launch {
                            autoStart(navController)
                        }
                    }

                    NavHost(navController, list.first().toString()) {
                        list.forEach { item ->
                            composable(item.toString()) {
                                Log.d(this@MainActivity::class.simpleName, "composable $item")

                                val click = {
                                    Thread.sleep(1000) // simulate work during back handler
                                    navController.navigateUp()
                                }

                                BackHandler {
                                    click.invoke()
                                }
                                Greeting(
                                    name = item.toString(),
                                    modifier = Modifier.padding(innerPadding),
                                    navigate = { _ ->
                                        val next = item + 1
                                        if (list.size >= next) {
                                            navController.navigate(next.toString())
                                        }
                                       else {
                                            backMany()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun autoStart(navController: NavHostController) {
        delay(1000)
        list.forEach { item ->
            delay(200)
            if (item > 1) {
                navController.navigate(item.toString())
                Log.d(this@MainActivity::class.simpleName, "auto navigate $item")
            }
        }
        delay(1000)
        backMany()
        delay(1000)
    }

    private fun backMany() {
        // sendCancelIfRunning: isInProgress=falsecallback=androidx.activity.OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1@6260b6b
        MainScope().launch {
            list.forEach { item ->
                //delay(2) // IndexOutOfBoundsException
                //delay(4) // IndexOutOfBoundsException
                //delay(5) // IndexOutOfBoundsException
                //delay(10) // IndexOutOfBoundsException
                delay(15) // TODO Check this consider debounce time ??? to allow sendCancelIfRunning?
                backFlow().collect{
                    back(item)
                }
            }
        }
    }

    private fun backFlow():Flow<Int>  = flowOf(1).flowOn(Dispatchers.IO)

    private fun back(i: Int) {
        Log.w(this@MainActivity::class.simpleName, "onBackPressed $i")
        this@MainActivity.onBackPressed()
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier, navigate: (String) -> Unit) {
        Column {
            Text(
                text = "Compose $name!",
                modifier = modifier
            )
            Button(onClick = { ->
                navigate.invoke(name)
            }, content = {
                Text(
                    text = "navigate to next item",
                    modifier = modifier
                )
            }, modifier = modifier
            )
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PredictiveBackHandlerTheme {
            Greeting("Android", navigate = { _ -> })
        }
    }
}