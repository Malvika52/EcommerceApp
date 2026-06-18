package com.example.ecommerceapp.screens.products

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CartViewmodel
import com.example.ecommerceapp.viewmodels.ProductViewModel

@Composable
fun ProductScreen(categoryId : String, navController: NavController,
                  productViewModel: ProductViewModel = hiltViewModel(),
                  cartViewmodel: CartViewmodel = hiltViewModel()){

    LaunchedEffect(categoryId) {
        Log.i("Malvika", "categoryID  received = $categoryId")
        productViewModel.fetchProductsById(categoryId)
    }

    val productListState = productViewModel.products.collectAsState()
    val products = productListState.value


    /*val products = listOf<Products>(Products(
        "1", id = "1", imageUrl = "https://cdn-icons-png.flaticon.com/512/15/15874.png",
        55000.0, "Smartphone"
    ),
        Products(
            "1", id = "1", imageUrl = "https://cdn-icons-png.flaticon.com/512/179/179386.png",
            55000.0, "Laptop"
        ),
        Products(
            id = "1", imageUrl = "https://cdn-icons-png.flaticon.com/512/3105/3105807.png",
            price = 400.0, title = "Bottle"
        ),
        Products(
            id = "1", imageUrl = "https://cdn-icons-png.flaticon.com/512/3659/3659899.png",
            price = 5000.0, title = "Cooler"
        ))*/

    Column(modifier = Modifier.fillMaxSize()){

        Text(text = "Products  for category : $categoryId",style =  MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp) )


        if(products.isEmpty()){
            Text(text = "No products  for category : $categoryId",style =  MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp) )
        }else{
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp)){
                items(products){
                    product ->
                    ProductItem(product, onProductClick = {
                        navController.navigate(Screens.ProductDetails.createRoute(product.id))
                    }, onAddToCart = {
                        cartViewmodel.addToCart(product)
                    } )
                }
            }
        }
    }

}