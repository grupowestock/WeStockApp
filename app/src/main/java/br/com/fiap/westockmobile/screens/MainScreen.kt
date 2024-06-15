package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import br.com.fiap.westockmobile.database.repository.ProdutoRepository
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.model.UsuarioViewModel
import androidx.compose.runtime.livedata.observeAsState
import br.com.fiap.westockmobile.model.UsuarioViewModelFactory
import br.com.fiap.westockmobile.repository.UsuarioRepository
import br.com.fiap.westockmobile.service.RetrofitInstance
import coil.compose.rememberImagePainter

@Composable
fun MainScreen(
    navController: NavHostController,
    usuarioId: Long,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioRepository = remember { UsuarioRepository(RetrofitInstance.usuarioService) }
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(usuarioRepository)
    )

    LaunchedEffect(usuarioId) {
        usuarioViewModel.fetchUsuario(usuarioId)
    }

    val usuario by usuarioViewModel.usuario.observeAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp) // Add padding to avoid overlap with the bottom navigation bar
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .offset(y = -50.dp), // Move the logo up by 100 dp
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.titlemenublue),
                    contentDescription = "WeStock Logo",
                    modifier = Modifier.size(150.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .offset(y = -100.dp), // Move the row up by 100 dp
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menuburger),
                    contentDescription = "Menu Burger",
                    modifier = Modifier.size(24.dp).clickable { navController.navigate("menuScreen") }
                )
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Olá, ${usuario?.nome ?: "Usuário"}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                    Text(
                        text = "Boas-Vindas!",
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                }
            }

            // Search Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFE6E6E6), shape = RoundedCornerShape(15.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Make the field thinner
                    .offset(y = -100.dp) // Move the search bar up by 100 dp
            ) {
                var searchQuery by remember { mutableStateOf("") }
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp, fontFamily = Poppins),
                    modifier = Modifier.fillMaxWidth()
                )
                if (searchQuery.isEmpty()) {
                    Text(text = "Procurar aqui...", color = Color.Gray, fontSize = 16.sp, fontFamily = Poppins)
                }
            }

            // Estoque de Produtos Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(100.dp)
                    .offset(y = -100.dp), // Move the card up by 100 dp
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(8.dp), // Increase elevation for higher shadow
                colors = CardDefaults.cardColors(containerColor = Color.White) // White background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_candlestick_chart_24),
                            contentDescription = "Candle Icon",
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Estoque de Produtos", fontSize = 20.sp, fontWeight = FontWeight.Light, fontFamily = Poppins)
                    }
                    Button(
                        onClick = { /* Handle click event */ },
                        shape = RoundedCornerShape(50), // Rounded button shape
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Button background color
                        modifier = Modifier.offset(y = 25.dp, x = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowright),
                            contentDescription = "Arrow Icon",
                            modifier = Modifier.size(28.dp),
                            tint = Color.Black // Icon color
                        )
                    }
                }
            }

            // Adicionar Produto Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color(0xFFE6E6E6), shape = RoundedCornerShape(15.dp))
                    .padding(16.dp)
                    .heightIn(min = 50.dp) // Minimum height for the row
                    .offset(y = -100.dp) // Move the row up by 100 dp
            ) {
                Text(
                    text = "Adicionar produto",
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { navController.navigate("edicaoProduto") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "+ Cadastrar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Últimos itens cadastrados Card
            LastAddedItemsCard(navController, usuario?.id)
        }

        // Fixed Bottom Navigation
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavigationBar3(navController)
        }
    }
}

@Composable
fun LastAddedItemsCard(navController: NavHostController, userId: Long?) {
    if (userId == null) return

    val produtoRepository = ProdutoRepository()
    val produtos by produtoRepository.produtos.observeAsState(emptyList())

    LaunchedEffect(userId) {
        produtoRepository.fetchUltimosProdutos(userId)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = -100.dp), // Move the card up by 100 dp
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Increase elevation for higher shadow
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Últimos itens cadastrados", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            Spacer(modifier = Modifier.height(8.dp))
            produtos.take(3).forEach { produto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberImagePainter(data = produto.imagemUrl), // Use a URL de imagem do produto
                            contentDescription = "Product Image",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(produto.nome, fontSize = 16.sp, fontFamily = Poppins)
                    }
                    Text(produto.dataCadastro, fontSize = 16.sp, fontFamily = Poppins)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar3(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavigationIcon3(navController, painterResource(id = R.drawable.perfilicon), "Profile", "perfil")
        BottomNavigationIcon3(navController, painterResource(id = R.drawable.homeicon), "Home", "mainScreen")
        BottomNavigationIcon3(navController, painterResource(id = R.drawable.cloudicon), "Cloud", "produtos")
    }
}

@Composable
fun BottomNavigationIcon3(navController: NavHostController, icon: Painter, contentDescription: String, destination: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(32.dp)
            .clickable { navController.navigate(destination) }
    )
}
