package com.example.ecommerceapp.screens.navigation

sealed class Screens(val route : String) {


    object Cart : Screens("Cart")
    object ProductDetails : Screens("product_details/{productId}"){
        fun createRoute(productId : String) = "product_details/$productId"
    }

    object ProductList : Screens("product_list/{categoryId}"){
        fun createRoute(categoryId : String) = "product_list/$categoryId"
    }
    object CategoryList : Screens("CategoryList")

    object Login : Screens("LoginScreen")
    object Signup : Screens("SignUp")
    object Profile : Screens("Profile")
    object HomeScreen : Screens("Home")


}
