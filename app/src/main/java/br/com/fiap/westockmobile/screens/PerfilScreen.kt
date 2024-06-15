package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.westockmobile.R
import br.com.fiap.westockmobile.model.UsuarioViewModel
import br.com.fiap.westockmobile.model.UsuarioViewModelFactory
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway

@Composable
fun PerfilScreen(navController: NavHostController, usuarioRepository: UsuarioRepository) {
    val usuarioViewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModelFactory(usuarioRepository))
    val usuario by usuarioViewModel.usuario.observeAsState()

    var nome by remember { mutableStateOf(usuario?.nome ?: "") }
    var sobrenome by remember { mutableStateOf(usuario?.sobrenome ?: "") }
    var celular by remember { mutableStateOf(usuario?.celular ?: "") }
    var email by remember { mutableStateOf(usuario?.email ?: "") }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text("Perfil", color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = Raleway)
                Spacer(modifier = Modifier.height(40.dp))
                ProfileField(label = "Nome", value = nome, onValueChange = { nome = it })
                Spacer(modifier = Modifier.height(16.dp))
                ProfileField(label = "Sobrenome", value = sobrenome, onValueChange = { sobrenome = it })
                Spacer(modifier = Modifier.height(16.dp))
                ProfileField(label = "Celular", value = celular, onValueChange = { celular = it })
                Spacer(modifier = Modifier.height(16.dp))
                ProfileField(label = "E-mail", value = email, onValueChange = { email = it })
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)  // Reduz a largura em 30%
                        .height(40.dp),
                    onClick = {
                        val updatedUsuario = usuario?.copy(
                            nome = nome,
                            sobrenome = sobrenome,
                            celular = celular,
                            email = email
                        )
                        if (updatedUsuario != null) {
                            usuarioViewModel.updateUsuario(updatedUsuario)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
                ) {
                    Text("Salvar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)  // Reduz a largura em 30%
                        .height(40.dp),
                    onClick = { navController.navigate("menuScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Voltar", color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = Poppins)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavigationIcon(navController, painterResource(id = R.drawable.perfilicon), "Profile", "perfil")
                    BottomNavigationIcon(navController, painterResource(id = R.drawable.homeicon), "Home", "mainScreen")
                    BottomNavigationIcon(navController, painterResource(id = R.drawable.cloudicon), "Cloud", "produtos")
                }
            }
        }
    }
}

@Composable
fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins)
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0FF), shape = RoundedCornerShape(15.dp))
                .padding(horizontal = 16.dp, vertical = 3.dp)  // Ajustado para ser mais fino
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(color = Color(0xFF003399), fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.editicon),
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationIcon(navController: NavHostController, icon: Painter, contentDescription: String, destination: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(36.dp)  // Reduzido 10%
            .clickable { navController.navigate(destination) }
            .offset(y = 30.dp)
    )
}
