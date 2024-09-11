package com.example.predictivebackhandler

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictiveBackHandlerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                   val list =  listOf(1,2,3)
                    NavHost(navController, list.first().toString()) {
                        list.forEach { item ->
                            composable(item.toString()) {
                                Log.d("composable", "$item")
                                BackHandler {
                                    Thread.sleep(1000) // simulate work during back handler
                                    navController.navigateUp() // action
                                }
                                Greeting(
                                    name = item.toString(),
                                    modifier = Modifier.padding(innerPadding),
                                    navigate = { _ ->
                                        val next  = item+1
                                        if (list.size>= next){
                                            navController.navigate(next.toString())
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

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier, navigate: (String) -> Unit) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = { ->
            navigate.invoke(name)
        }, content = {
            Text(
                text = "Hello $name!",
                modifier = modifier
            )
        })
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PredictiveBackHandlerTheme {
            Greeting("Android", navigate = { _ -> })
        }
    }
}