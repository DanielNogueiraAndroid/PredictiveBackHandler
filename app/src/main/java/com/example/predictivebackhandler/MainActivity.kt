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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.predictivebackhandler.ui.theme.PredictiveBackHandlerTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictiveBackHandlerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    val list = listOf(1, 2, 3)
                    NavHost(navController, list.first().toString()) {
                        list.forEach { item ->
                            composable(item.toString()) {
                                Log.d("composable", "$item")

                                val onBack = {
                                    navController.navigateUp()
                                    Thread.sleep(1000) // simulate work during back handler
                                }

                                val next = item + 1
                                val click = if (list.size >= next) {
                                    { navController.navigate(next.toString()) }
                                } else {
                                    { backMany() }
                                }
                                val clickName = if (list.size >= next) {
                                    "navigate to next item"
                                } else {
                                     "backMany"
                                }

                                BackHandler {
                                    onBack.invoke()
                                }
                                Greeting(
                                    name = item.toString(),
                                    modifier = Modifier.padding(innerPadding),
                                    clickName= clickName,
                                    navigate = { _ ->
                                        click.invoke()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun backMany() {
        MainScope().launch {
            backFlow().collect {
                back()
            }
            backFlow().collect {
                back()
            }
            backFlow().collect {
                back()
            }
        }
    }

    private fun backFlow(): Flow<Int> = flowOf(1).flowOn(Dispatchers.IO)

    private fun back() {
        this@MainActivity.onBackPressed()
    }

    @Composable
    fun Greeting(
        name: String,
        modifier: Modifier = Modifier,
        navigate: (String) -> Unit,
        clickName: String
    ) {
        Column {
            Text(
                text = "Compose $name!",
                modifier = modifier
            )
            Button(onClick = { ->
                navigate.invoke(name)
            }, content = {
                Text(
                    text = clickName ,
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
            Greeting("Android", navigate = { _ -> }, clickName = "click name")
        }
    }
}