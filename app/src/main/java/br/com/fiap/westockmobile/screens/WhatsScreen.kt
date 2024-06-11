package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway

@Composable
fun WhatsScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.cabecalhowatts), // Substitua pelo ID da sua imagem de cabeçalho
                contentDescription = "Header Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)  // Ajuste a altura conforme necessário
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "WeStock",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Raleway,
                color = Color(0xFF003399)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle continue to chat click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(
                    text = "Continuar para o chat",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
