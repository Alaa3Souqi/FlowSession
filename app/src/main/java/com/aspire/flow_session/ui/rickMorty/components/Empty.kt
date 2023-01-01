package com.aspire.flow_session.ui.rickMorty.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspire.flow_session.R

@Composable
fun Empty() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Nothing in Favorite", fontSize = 22.sp, color = Color(0xFF707070)
        )
    }
}