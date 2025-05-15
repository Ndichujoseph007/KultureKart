package com.ndichu.kulturekart.navigation


import DashboardScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.ui.components.ScaffoldWithBottomBar
import com.ndichu.kulturekart.ui.screens.auth.LoginScreen
import com.ndichu.kulturekart.ui.screens.auth.RegisterScreen
import com.ndichu.kulturekart.ui.screens.profile.AboutScreen
import com.ndichu.kulturekart.ui.screens.profile.ProfileScreen
import com.ndichu.kulturekart.ui.screens.seller.AddProductScreen
import com.ndichu.kulturekart.ui.screens.seller.ProductDetailScreen
import com.ndichu.kulturekart.ui.screens.seller.ProductListScreen
import com.ndichu.kulturekart.ui.screens.seller.SellerHomeScreen
import com.ndichu.kulturekart.ui.screens.splash.SplashScreen


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
        composable(ROUTE_DASHBOARD) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                DashboardScreen( navController = navController)
            }
        }
        composable(ROUTE_SELLER_HOME) {
            SellerHomeScreen(
                navController = navController,
//                onAddProductClick = {
//                    navController.navigate(ROUTE_ADD_PRODUCT)
//                },
//                onProfileClick = {
//                    navController.navigate(ROUTE_PROFILE)
//                }
            )
        }
        composable(ROUTE_ADD_PRODUCT) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                AddProductScreen( navController = navController)
            }
        }

        composable(
            route = ROUTE_PRODUCT_DETAIL_WITH_ARG,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ScaffoldWithBottomBar(navController = navController) { padding ->
            ProductDetailScreen(productId = productId, navController = navController)
        }
        }
        composable(ROUTE_PRODUCT_LIST) {
            ScaffoldWithBottomBar(navController = navController) { padding ->
                ProductListScreen(navController,ProductViewModel())
            }
        }
    }
}