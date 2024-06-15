package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.model.UsuarioViewModel
import br.com.fiap.westockmobile.ui.theme.Poppins

@Composable
fun MenuScreen(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        if (showDialog) {
            ConfirmationDialog(
                onConfirm = {
                    showDialog = false
                    navController.navigate("login")
                },
                onDismiss = { showDialog = false }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(if (showDialog) Color.Gray.copy(alpha = 0.5f) else Color.Transparent),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.titlemenublue),
                    contentDescription = "Menu Title",
                    modifier = Modifier.align(Alignment.Start).size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                MenuButton(navController, text = "Perfil", destination = "perfil")
                Spacer(modifier = Modifier.height(16.dp))
                MenuButton(navController, text = "Produtos", destination = "produtos")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDialog = true }
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.exiticon),
                        contentDescription = "Exit Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sair",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Poppins,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavigationIcon(painterResource(id = R.drawable.perfilicon), "Profile", Modifier.size(36.dp),"perfil")  // Reduzido 10%
                    BottomNavigationIcon(painterResource(id = R.drawable.homeicon), "Home", Modifier.size(36.dp),"mainScreen")  // Reduzido 10%
                    BottomNavigationIcon(painterResource(id = R.drawable.cloudicon), "Cloud", Modifier.size(36.dp),"produtos")  // Reduzido 10%
                }
            }
        }

        // WhatsApp Button
        FloatingActionButton(
            onClick = { navController.navigate("whatsScreen") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .offset(y = -90.dp),
            containerColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.whatsappicon),
                contentDescription = "WhatsApp",
                tint = Color(0xFF25D366),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun MenuButton(navController: NavHostController, text: String, destination: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(destination) }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Poppins,
            color = Color.Black
        )
        Icon(
            painter = painterResource(id = R.drawable.goicon),
            contentDescription = "Arrow Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun BottomNavigationIcon(icon: Painter, contentDescription: String, modifier: Modifier = Modifier, destination: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .clickable { destination }
    )
}

@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Realmente deseja sair?",
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(48.dp)
            ) {
                Text("Sair", color = Color.White, fontFamily = Poppins)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399)),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(48.dp)
            ) {
                Text("Voltar", color = Color.White, fontFamily = Poppins)
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}
