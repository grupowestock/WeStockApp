package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.westockmobile.R


@Composable
fun TitleScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.title),
                contentDescription = "WeStock Logo",
                modifier = Modifier.size(180.dp)
            )
            Image(
                painter = painterResource(R.drawable.eclipse),
                contentDescription = "Eclipse Logo",
                modifier = Modifier.size(80.dp)
            )
        }
    }
}
