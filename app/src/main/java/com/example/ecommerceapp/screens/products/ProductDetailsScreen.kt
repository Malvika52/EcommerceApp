package com.example.ecommerceapp.screens.products

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.viewmodels.CartViewmodel
import com.example.ecommerceapp.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen( productId : String,
                          productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
                          cartViewmodel: CartViewmodel = hiltViewModel()){


    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProductsDetailsByProductId(productId)
    }

    val productState = productDetailsViewModel.productDetails.collectAsState()
    val product = productState.value

    if(product == null){
        Text("Product Not found")
    }
    else{
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
            Image(painter = rememberAsyncImagePainter(product.imageUrl),
                "", contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(4.dp)))


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                product.title, style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${product.price}", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${product.categoryId}", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

        }
        IconButton(onClick = {
            cartViewmodel.addToCart(product)
        }, modifier = Modifier.padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)){

            Icon(imageVector = Icons.Default.ShoppingCart, "", tint = Color.White)

        }
    }
}