package com.ndichu.kulturekart.navigation

import android.R.attr.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.ui.components.ScaffoldWithBottomBar
import com.ndichu.kulturekart.ui.screens.auth.LoginScreen
import com.ndichu.kulturekart.ui.screens.auth.RegisterScreen
import com.ndichu.kulturekart.ui.screens.splash.SplashScreen
import com.ndichu.kulturekart.ui.screens.buyer.BuyerHomeScreen
import com.ndichu.kulturekart.ui.screens.buyer.ProductDetailScreen
import com.ndichu.kulturekart.ui.screens.profile.AboutScreen
import com.ndichu.kulturekart.ui.screens.profile.ProfileScreen
import com.ndichu.kulturekart.ui.screens.seller.AddProductScreen
import com.ndichu.kulturekart.ui.screens.seller.EditProductScreen
import com.ndichu.kulturekart.ui.screens.seller.SellerHomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_SPLASH,
        modifier = modifier
    ) {
////////////COMMON TO ALL
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(ROUTE_REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(ROUTE_PROFILE) {
            ProfileScreen(navController = navController)
        }
        composable(ROUTE_ABOUT) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                AboutScreen(modifier = padding, navController = navController)
            }
        }
//////////BUYERS ROUTES

        composable(ROUTE_BUYER_HOME) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                BuyerHomeScreen(modifier = padding, navController = navController)
            }

        }

        composable(
            route = "$ROUTE_PRODUCT_DETAIL/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable

            // Provide a ViewModel for loading product
            val viewModel: ProductViewModel = viewModel()

            // Trigger Firebase fetch
            val productState = viewModel.getProductById(productId).collectAsState(initial = null)
            val product = productState.value


            if (product != null) {
                ScaffoldWithBottomBar(navController = navController) { padding ->
                    ProductDetailScreen(
                        navController = navController,
                        modifier = padding,
                        product = product,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            } else {
                // Optional: Show loading or error UI
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
//////////SELLERS ROUTES
        composable(ROUTE_SELLER_HOME) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                SellerHomeScreen(modifier = padding, navController = navController)
            }
        }
        composable(ROUTE_ADD_PRODUCT) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                AddProductScreen(
                    modifier = padding,
                    navController = navController,
                    onProductUploaded = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = "$ROUTE_EDIT_PRODUCT/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable

            ScaffoldWithBottomBar(navController = navController) { padding ->
                EditProductScreen(
                    productId = productId,
                    navController = navController
                )
            }
        }



    }
}
