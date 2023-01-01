package com.aspire.flow_session.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aspire.flow_session.ui.rickMorty.FavoriteScreen
import com.aspire.flow_session.ui.rickMorty.RickMortyScreen
import com.aspire.flow_session.ui.rickMorty.RickMortyViewModel
import com.aspire.flow_session.ui.theme.RickMortyComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: RickMortyViewModel = hiltViewModel()

            RickMortyComposeTheme {
                NavHost(navController = navController, startDestination = "rick&morty") {

                    composable("rick&morty") {
                        RickMortyScreen(navController, viewModel)
                    }

                    composable("favorite") {
                        FavoriteScreen(viewModel)
                    }
                }
            }
        }
    }
}