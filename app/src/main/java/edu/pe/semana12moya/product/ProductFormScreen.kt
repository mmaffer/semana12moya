package edu.pe.semana12moya.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.pe.semana12moya.model.Product
import edu.pe.semana12moya.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel,
    product: Product? = null,
    onSave: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    // 🔥 Cargar valores al editar
    LaunchedEffect(product) {
        if (product != null) {
            nombre = product.nombre
            codigo = product.codigo
            descripcion = product.descripcion
            precio = product.precio.toString()
            cantidad = product.cantidad.toString()
            estado = product.estado
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3EDF7))
            .padding(18.dp)
    ) {

        Text(
            text = if (product == null) "Nuevo Producto" else "Editar Producto",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF4A148C),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                CustomTextField(nombre, "Nombre") { nombre = it }
                CustomTextField(codigo, "Código") { codigo = it }
                CustomTextField(descripcion, "Descripción") { descripcion = it }
                CustomTextField(precio, "Precio (S/.)") { precio = it }
                CustomTextField(cantidad, "Cantidad") { cantidad = it }
                CustomTextField(estado, "Estado") { estado = it }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = {

                // ⚠ ID REAL DEL PRODUCTO
                val realId = product?.id ?: ""

                val finalProduct = Product(
                    id = realId,
                    nombre = nombre,
                    codigo = codigo,
                    descripcion = descripcion,
                    precio = precio.toDoubleOrNull() ?: 0.0,
                    cantidad = cantidad.toIntOrNull() ?: 0,
                    estado = estado,
                    userId = product?.userId ?: ""   // Mantener el userId original
                )

                if (product == null) {
                    viewModel.addProduct(finalProduct)
                } else {
                    viewModel.updateProduct(finalProduct)
                }

                onSave()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E57C2),
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (product == null) "Guardar" else "Actualizar",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = Color(0xFF5E35B1),
            focusedBorderColor = Color(0xFF7E57C2),
            cursorColor = Color(0xFF7E57C2)
        )
    )
}
