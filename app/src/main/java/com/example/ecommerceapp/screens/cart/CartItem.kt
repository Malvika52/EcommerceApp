package com.example.ecommerceapp.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.model.Products

@Composable
fun CartItem(cartItem : Products, onRemove: () -> Unit){
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)) {

        Row(modifier = Modifier.fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically){

            Image(painter = rememberAsyncImagePainter(cartItem.imageUrl), contentDescription = "",
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Fit)

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,) {
                Text(text = cartItem.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "${cartItem.price}", style = MaterialTheme.typography.bodyMedium)
            }

            Column(modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = cartItem.title, style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp))
                }

                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                }

            }




        }


    }




}