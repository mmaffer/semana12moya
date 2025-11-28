package edu.pe.semana12moya.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.pe.semana12moya.model.Product
import edu.pe.semana12moya.viewmodel.ProductViewModel

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
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text("Mis Productos", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(12.dp))

            LazyColumn {
                items(products) { product ->

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Código: ${product.codigo}")
                            Text("Descripción: ${product.descripcion}")
                            Text("Precio: S/.${product.precio}")
                            Text("Cantidad: ${product.cantidad}")
                            Text("Estado: ${product.estado}")

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(onClick = { onEditClick(product) }) {
                                    Text("Editar")
                                }
                                TextButton(onClick = {
                                    viewModel.deleteProduct(product.id)
                                }) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
