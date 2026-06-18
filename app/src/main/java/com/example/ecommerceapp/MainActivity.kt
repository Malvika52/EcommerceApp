package com.example.ecommerceapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.cart.CartScreen
import com.example.ecommerceapp.screens.categories.CategoryScreen
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.products.ProductDetailsScreen
import com.example.ecommerceapp.screens.products.ProductScreen
import com.example.ecommerceapp.screens.profile.LoginScreen
import com.example.ecommerceapp.screens.profile.ProfileScreen
import com.example.ecommerceapp.screens.profile.SignupScreen
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import com.example.ecommerceapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //nav system

            val navController = rememberNavController()

            val authViewModel : AuthViewModel = hiltViewModel()

            val isLoggedIn by remember {
                derivedStateOf {
                    authViewModel.isLoggedIn
                }
            }

            NavHost(navController, Screens.HomeScreen.route){

                composable(Screens.HomeScreen.route) {
                    HomeScreen(navController = navController,
                        onProfileClick = {navController.navigate(Screens.Profile.route)},
                        onCartClick = {navController.navigate(Screens.Cart.route)})
                }
                composable(Screens.Cart.route) {
                    CartScreen(navController = navController)
                }
                composable(Screens.Profile.route) {
                    ProfileScreen(navController = navController, onSignOut = {
                        authViewModel.signOut()
                        navController.navigate(Screens.Login.route)
                    })
                }
                composable(Screens.CategoryList.route) {
                    CategoryScreen(navController = navController,
                        onCartClick = {
                            navController.navigate(Screens.Cart.route)
                        },
                        onProfileClick = {
                            if(isLoggedIn){
                                navController.navigate(Screens.Profile.route)
                            }else{
                                navController.navigate(Screens.Login.route)
                            }
                        })
                }
                composable(Screens.ProductDetails.route) {
                    Log.i("Malvika", "product Id = ")
                    val productId = it.arguments?.getString("productId")
                    if(productId!=null){
                        Log.i("Malvika", "Products Screen Details")
                        ProductDetailsScreen(productId)
                    }
                }

                composable(Screens.ProductList.route) {
                    val category = it.arguments?.getString("categoryId")
                    if(category!=null){
                        ProductScreen(category, navController=navController)
                    }
                }

                composable(Screens.Signup.route) {
                    SignupScreen(onNavigateToLogin =  {navController.navigate(Screens.Signup.route)},
                        onSignUpError = {
                            
                        }, onSignUpSuccess = {
                            navController.navigate(Screens.HomeScreen.route)
                        })
                }

                composable(Screens.Login.route){
                    LoginScreen(onNavigateToSignUp = {navController.navigate(Screens.Signup.route)},
                       onLoginSuccess = {navController.navigate(Screens.HomeScreen.route)}, )
                }

            }
        }
    }
}

