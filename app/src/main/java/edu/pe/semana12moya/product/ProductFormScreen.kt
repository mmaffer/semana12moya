package edu.pe.semana12moya.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.pe.semana12moya.model.Product
import edu.pe.semana12moya.viewmodel.ProductViewModel

@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel,
    product: Product? = null,
    onSave: () -> Unit
) {

    // ---------------------------
    // CAMPOS DEL PRODUCTO
    // ---------------------------

    var nombre by remember { mutableStateOf(product?.nombre ?: "") }
    var codigo by remember { mutableStateOf(product?.codigo ?: "") }
    var descripcion by remember { mutableStateOf(product?.descripcion ?: "") }
    var precio by remember { mutableStateOf(product?.precio?.toString() ?: "") }
    var cantidad by remember { mutableStateOf(product?.cantidad?.toString() ?: "") }
    var estado by remember { mutableStateOf(product?.estado ?: "") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Text(
            text = if (product == null) "Nuevo Producto" else "Editar Producto",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 CÓDIGO
        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 DESCRIPCIÓN
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 PRECIO
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 CANTIDAD
        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 ESTADO
        OutlinedTextField(
            value = estado,
            onValueChange = { estado = it },
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ---------------------------
        // GUARDAR PRODUCTO
        // ---------------------------
        Button(
            onClick = {

                val finalProduct = Product(
                    id = product?.id ?: "",
                    nombre = nombre,
                    codigo = codigo,
                    descripcion = descripcion,
                    precio = precio.toDoubleOrNull() ?: 0.0,
                    cantidad = cantidad.toIntOrNull() ?: 0,
                    estado = estado
                )

                if (product == null) {
                    // AGREGAR NUEVO
                    viewModel.addProduct(finalProduct)
                } else {
                    // EDITAR EXISTENTE
                    viewModel.updateProduct(finalProduct)
                }

                onSave()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (product == null) "Guardar" else "Actualizar")
        }
    }
}
