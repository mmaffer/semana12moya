package edu.pe.semana12moya

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import edu.pe.semana12moya.auth.LoginScreen
import edu.pe.semana12moya.auth.RegisterScreen
import edu.pe.semana12moya.ui.product.ProductFormScreen
import edu.pe.semana12moya.ui.product.ProductListScreen
import edu.pe.semana12moya.viewmodel.ProductViewModel

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"

    const val ADD_PRODUCT = "addProduct"
    const val EDIT_PRODUCT = "editProduct"
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    AuthNavGraph(navController)
}

@Composable
fun AuthNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {

        // ---------------------- LOGIN ----------------------
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ---------------------- REGISTER ----------------------
        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------- LISTA DE PRODUCTOS (HOME) ----------------------
        composable(Destinations.HOME) {

            val vm: ProductViewModel = viewModel()

            ProductListScreen(
                viewModel = vm,
                onAddClick = {
                    navController.navigate(Destinations.ADD_PRODUCT)
                },
                onEditClick = { product ->
                    navController.navigate("${Destinations.EDIT_PRODUCT}/${product.id}")
                }
            )
        }

        // ---------------------- AGREGAR PRODUCTO ----------------------
        composable(Destinations.ADD_PRODUCT) {

            val vm: ProductViewModel = viewModel()

            ProductFormScreen(
                viewModel = vm,
                onSave = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------- EDITAR PRODUCTO ----------------------
        composable("${Destinations.EDIT_PRODUCT}/{productId}") { backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId") ?: ""

            val vm: ProductViewModel = viewModel()

            val products by vm.products.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                vm.loadProducts()
            }

            val productToEdit = products.find { it.id == productId }

            ProductFormScreen(
                viewModel = vm,
                product = productToEdit,
                onSave = {
                    navController.popBackStack()
                }
            )
        }
    }
}
