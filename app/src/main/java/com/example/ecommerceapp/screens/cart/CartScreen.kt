package com.example.ecommerceapp.screens.cart

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.screens.home.FeaturedProductCard
import com.example.ecommerceapp.viewmodels.CartViewmodel

@Composable
fun CartScreen(navController: NavController,
               cartViewmodel: CartViewmodel = hiltViewModel()
){

    val cartitemsState = cartViewmodel.cartItems.collectAsState(initial = emptyList())
    val cartItems = cartitemsState.value


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Your Cart", style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp))

        if(cartItems.isEmpty()){
            Column(modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text("Cart is Empty", style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Start shopping")
                }
            }
        }
        else{

            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(cartItems){ product ->
                    CartItem(product, onRemove = {
                        cartViewmodel.removeFromCart(product)
                    })
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(text = "Total : ", style = MaterialTheme.typography.titleMedium)
                    Text(text = "INR  ${cartViewmodel.calculateTotal(cartItems)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {

                }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                    Text("Proceed to checkout",style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold )
                }

            }

        }


    }
}