package edu.pe.semana12moya.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pe.semana12moya.data.ProductRepository
import edu.pe.semana12moya.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = repository.getMyProducts()
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(product)
            loadProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
            loadProducts()
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            repository.deleteProduct(id)
            loadProducts()
        }
    }
}
