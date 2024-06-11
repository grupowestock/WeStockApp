package br.com.fiap.westockmobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import br.com.fiap.westockmobile.database.repository.ProdutoRepository
import br.com.fiap.westockmobile.model.Produto
import br.com.fiap.westockmobile.ui.theme.Poppins
import br.com.fiap.westockmobile.ui.theme.Raleway
import coil.compose.rememberImagePainter

@Composable
fun ProdutosScreen(navController: NavHostController, userId: Long, produtoRepository: ProdutoRepository = ProdutoRepository()) {
    var searchQuery by remember { mutableStateOf("") }
    val produtos by produtoRepository.produtos.observeAsState(emptyList())

    LaunchedEffect(userId) {
        produtoRepository.fetchProdutosByUser(userId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Content Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.titleblack), // Verifique se o nome do recurso está correto
                        contentDescription = "WeStock",
                        modifier = Modifier.size(130.dp).offset(y = -60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -100.dp) // Move all content up by 80.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.menuburger), // Verifique se o nome do recurso está correto
                            contentDescription = "Menu Burger",
                            modifier = Modifier.size(24.dp).clickable { navController.navigate("menuScreen") }
                        )
                        Text("Produtos", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Raleway)
                    }

                    // Search Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .background(Color(0xFFE6E6E6), shape = RoundedCornerShape(15.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
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

                    // Filter Options
                    Text("Filtrar por:", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Descrição")
                                FilterCheckbox("Cód. SKU")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Tamanho")
                                FilterCheckbox("Quantidade")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Data")
                                FilterCheckbox("Cor")
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Categoria")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Valor")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                FilterCheckbox("Peso")
                            }
                        }
                    }

                    // Products List
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(produtos.chunked(2)) { rowItems ->
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                items(rowItems) { produto ->
                                    ProductCard(navController, produto)
                                }
                            }
                        }
                    }
                }
            }

            // Fixed Bottom Navigation
            BottomNavigationBar(navController)
        }
    }
}

@Composable
fun FilterCheckbox(label: String) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)  // Adjusted for smaller size
            .clickable { checked = !checked },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier.size(16.dp),  // Smaller checkbox
            colors = CheckboxDefaults.colors(checkmarkColor = Color(0xFF003399))
        )
        Spacer(modifier = Modifier.width(4.dp))  // Adjusted for smaller size
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = Poppins)  // Smaller font size
    }
}

@Composable
fun ProductCard(navController: NavHostController, produto: Produto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)  // Adjust the width for two cards per row
            .height(150.dp)  // Adjust the height as needed
            .background(Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)  // Add elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(produto.nome, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            Image(
                painter = rememberImagePainter(produto.imagemUrl), // Use rememberImagePainter to load images from URLs
                contentDescription = "Product Image",
                modifier = Modifier.size(70.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Ver detalhes", color = Color.Black, fontSize = 14.sp, fontFamily = Poppins)
                Icon(
                    painter = painterResource(id = R.drawable.moreicon), // Verifique se o nome do recurso está correto
                    contentDescription = "Arrow Icon",
                    modifier = Modifier.size(24.dp).clickable { navController.navigate("edicaoProduto") }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp)
            .offset(y = (-10).dp),  // Move icons up by 10.dp
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavigationIcon1(navController, painterResource(id = R.drawable.perfilicon), "Profile", "perfil")
        BottomNavigationIcon1(navController, painterResource(id = R.drawable.homeicon), "Home", "mainScreen")
        BottomNavigationIcon1(navController, painterResource(id = R.drawable.cloudicon), "Cloud", "produtos")
    }
}

@Composable
fun BottomNavigationIcon1(navController: NavHostController, icon: Painter, contentDescription: String, destination: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(32.dp)  // Reduced size
            .clickable { navController.navigate(destination) }
    )
}
