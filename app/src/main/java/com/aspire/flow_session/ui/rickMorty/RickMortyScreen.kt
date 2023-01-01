package com.aspire.flow_session.ui.rickMorty

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aspire.flow_session.ui.rickMorty.components.CharacterView
import com.aspire.flow_session.ui.rickMorty.components.Empty
import com.aspire.flow_session.ui.rickMorty.components.Error
import com.aspire.flow_session.ui.rickMorty.components.Loading
import com.aspire.flow_session.R

@ExperimentalFoundationApi
@Composable
fun RickMortyScreen(navController: NavHostController, viewModel: RickMortyViewModel) {

    Column {
        Image(
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .size(46.dp)
                .clickable { navController.navigate("favorite") }
                .align(Alignment.End),
            painter = painterResource(id = R.drawable.folder),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(12.dp))
        Divider()

        when (val state = viewModel.characters.collectAsState().value) {

            is RickMortyViewModel.ScreenState.Success -> {

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.items) { item ->
                        CharacterView(item.name, item.image, item.isSaved) {
                            viewModel.onEvent(RickMortyEvents.FavoriteClick(item))
                        }
                    }
                }
            }

            RickMortyViewModel.ScreenState.Empty -> {
                Empty()
            }

            is RickMortyViewModel.ScreenState.Error -> {
                Error {
                    viewModel.onEvent(RickMortyEvents.TryAgainCharacter)
                }
            }

            RickMortyViewModel.ScreenState.Loading -> {
                Loading()
            }
        }
    }
}