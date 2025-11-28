package edu.pe.semana12moya.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.pe.semana12moya.model.Product
import edu.pe.semana12moya.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onAddClick: () -> Unit,
    onEditClick: (Product) -> Unit
) {
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF7E57C2), // Lila suave
                contentColor = Color.White
            ) {
                Text("+", fontSize = 26.sp)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF3EDF7))   // color pastel suave
                .padding(16.dp)
        ) {
            Text(
                "Mis Productos",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF4A148C),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay productos registrados 📝",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(products) { product ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {

                                // Nombre destacado
                                Text(
                                    text = product.nombre,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color(0xFF512DA8)
                                )

                                Spacer(Modifier.height(6.dp))

                                Text("Código: ${product.codigo}")
                                Text("Descripción: ${product.descripcion}")
                                Text("Precio: S/. ${product.precio}")
                                Text("Cantidad: ${product.cantidad}")
                                Text("Estado: ${product.estado}")

                                Spacer(Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextButton(onClick = { onEditClick(product) }) {
                                        Text(
                                            "Editar",
                                            color = Color(0xFF5E35B1) // Lila
                                        )
                                    }

                                    TextButton(
                                        onClick = { viewModel.deleteProduct(product.id) }
                                    ) {
                                        Text(
                                            "Eliminar",
                                            color = Color(0xFFD32F2F) // Rojo suave
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
