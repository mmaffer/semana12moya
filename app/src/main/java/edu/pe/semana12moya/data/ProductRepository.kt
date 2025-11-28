package edu.pe.semana12moya.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import edu.pe.semana12moya.model.Product
import kotlinx.coroutines.tasks.await

class ProductRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("productos")

    private fun uid() = auth.currentUser?.uid ?: ""

    // CREATE
    suspend fun addProduct(product: Product) {
        val docId = productsRef.document().id

        val newProduct = product.copy(
            id = docId,
            userId = uid()  // 🔥 ASOCIAMOS EL PRODUCTO AL USUARIO LOGUEADO
        )

        productsRef.document(docId).set(newProduct).await()
    }

    // READ – SOLO PRODUCTOS DEL USUARIO
    suspend fun getMyProducts(): List<Product> {
        val snapshot = productsRef
            .whereEqualTo("userId", uid())
            .get()
            .await()

        return snapshot.toObjects(Product::class.java)
    }

    // UPDATE
    suspend fun updateProduct(product: Product) {
        productsRef.document(product.id).set(product).await()
    }

    // DELETE
    suspend fun deleteProduct(id: String) {
        productsRef.document(id).delete().await()
    }
}
