package com.example.ecommerceapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.ui.theme.DarkBlue
import com.example.ecommerceapp.ui.theme.SandYellow
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.products.ProductItem
import com.example.ecommerceapp.viewmodels.CartViewmodel
import com.example.ecommerceapp.viewmodels.SearchViewModel


@Composable
fun SearchBar(query : String,
              onQueryChange : (String) -> Unit,
              onSearch : () -> Unit, ){


    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    /*LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }*/


    Box(modifier = Modifier.height(50.dp)
        .clip(RoundedCornerShape(25.dp))
        .background(color = Color.Gray.copy(alpha = 0.15f)),
        contentAlignment = Alignment.CenterStart){

        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically){

            Icon(Icons.Default.Search, "Search bar", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(value = query, onValueChange = onQueryChange,
                singleLine = true,
                placeholder = {
                    Text("Search", color = Color.Gray, fontSize = 16.sp)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {onSearch()}
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = DarkBlue
                )
            )

        }
    }
}


@Composable
fun SearchResultsSection(navController: NavController,
                         searchViewModel: SearchViewModel = hiltViewModel(),
                         cartViewmodel: CartViewmodel = hiltViewModel()){

    val searchResults = searchViewModel.searchResult.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()){

        Text(text = "Search Results",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

        if(isSearching.value){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }else{
            LazyColumn(contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxWidth()) {
                items(searchResults.value.size){
                    index -> val product = searchResults.value[index]
                    ProductItem(product, onProductClick = {
                        navController.navigate(Screens.ProductDetails.createRoute(product.id))
                    },
                        onAddToCart = {cartViewmodel.addToCart(product)})
                }

            }
        }
    }

}