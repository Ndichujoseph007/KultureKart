package com.ndichu.kulturekart.ui.screens.buyer//package com.ndichu.kulturekart.ui.screens.buyer
//
//import android.R.attr.name
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.expandVertically
//import androidx.compose.animation.shrinkVertically
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.KeyboardArrowLeft
//import androidx.compose.material.icons.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import com.ndichu.kulturekart.data.CartViewModel
//import com.ndichu.kulturekart.model.CartItem
//import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(
//    navController: NavHostController,
//    viewModel: CartViewModel = viewModel()
//) {
//    val cartItems by viewModel.cartItems
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }
//
//
//    val shippingCost = 5.00
//    val subtotal = cartItems.sumOf { it.price * it.quantity }
//    val total = subtotal + shippingCost
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        " Your  Cart ",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = MaterialTheme.colorScheme.onPrimary
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            Icons.Filled.ArrowBack,
//                            contentDescription = "Back to previous screen",
//                            tint = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                },
//                actions = {
//                    // Optional: Clear Cart Button
//                    if (cartItems.isNotEmpty()) {
//                        TextButton(onClick = {
//                            scope.launch {
//                                val result = snackbarHostState.showSnackbar(
//                                    message = "Clear all items from cart?",
//                                    actionLabel = "Yes",
//                                    withDismissAction = true,
//                                    duration = SnackbarDuration.Long
//                                )
//                                if (result == SnackbarResult.ActionPerformed) {
//                                    viewModel.clearCart(context) // Pass context here
//                                    snackbarHostState.showSnackbar("Cart cleared!")
//                                }
//                            }
//                        }) {
//                            Text(
//                                "Clear All",
//                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
//                            )
//                        }
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        },
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(MaterialTheme.colorScheme.surface) // Footer background
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Rounded top corners
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // Cart Summary Section
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Subtotal:",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                    Text(
//                        "$${"%.2f".format(subtotal)}",
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.SemiBold,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//                Spacer(Modifier.height(4.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Shipping:",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                    Text(
//                        "$${"%.2f".format(shippingCost)}",
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.SemiBold,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//                Divider(
//                    modifier = Modifier.padding(vertical = 8.dp),
//                    thickness = 1.dp,
//                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Total:",
//                        style = MaterialTheme.typography.headlineSmall, // Prominent total
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary // Golden total
//                    )
//                    Text(
//                        "$${"%.2f".format(total)}",
//                        style = MaterialTheme.typography.headlineSmall, // Prominent total
//                        fontWeight = FontWeight.ExtraBold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//                Spacer(Modifier.height(16.dp))
//
//                Button(
//                    onClick = {
//                        viewModel.checkout(context) { success -> // Pass context here
//                            scope.launch {
//                                if (success) {
//                                    snackbarHostState.showSnackbar(
//                                        "Checkout successful! Total: $${"%.2f".format(total)}"
//                                    )
//                                    // Clear cart after successful checkout, passing context
//                                    viewModel.clearCart(context)
//                                    navController.navigate(ROUTE_DASHBOARD) {
//                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                                        launchSingleTop = true
//                                    }
//                                } else {
//                                    snackbarHostState.showSnackbar(
//                                        "Checkout failed. Please try again."
//                                    )
//                                }
//                            }
//                        }
//                    },
//                    enabled = cartItems.isNotEmpty(), // Only enable if items exist
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp), // Taller button
//                    shape = RoundedCornerShape(12.dp), // Consistent rounded corners
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.onPrimary
//                    ),
//                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Subtle shadow
//                ) {
//                    Text("Proceed to Checkout", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//                }
//
//                Spacer(Modifier.height(8.dp))
//
//                TextButton(
//                    onClick = { navController.navigate(ROUTE_DASHBOARD) }, // Navigate to Dashboard
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary) // Golden accent for continue shopping
//                ) {
//                    Text("Continue Shopping", fontSize = 16.sp)
//                }
//            }
//        },
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { paddingValues -> // Use paddingValues from Scaffold
//        // Main content area
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues) // Apply Scaffold padding
//                .background(MaterialTheme.colorScheme.background) // Consistent background
//        ) {
//            if (cartItems.isEmpty()) {
//                // --- Empty Cart State ---
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .weight(1f), // Allow it to take available space
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        Icons.Default.ShoppingCart,
//                        contentDescription = "Empty Cart",
//                        modifier = Modifier.size(96.dp), // Large icon
//                        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f) // Muted tint
//                    )
//                    Spacer(Modifier.height(24.dp))
//                    Text(
//                        "Your KultureKart is empty!",
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
//                        textAlign = TextAlign.Center
//                    )
//                    Spacer(Modifier.height(12.dp))
//                    Text(
//                        "Start exploring unique cultural treasures.",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
//                        textAlign = TextAlign.Center
//                    )
//                    Spacer(Modifier.height(24.dp))
//                    Button(
//                        onClick = { navController.navigate(ROUTE_DASHBOARD) },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.secondary,
//                            contentColor = MaterialTheme.colorScheme.onSecondary
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
//                    ) {
//                        Text("Discover Treasures", fontSize = 16.sp)
//                    }
//                }
//            } else {
//                // --- Cart Items List ---
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f) // Makes LazyColumn fill available space, pushing summary to bottom
//                        .animateContentSize(animationSpec = tween(durationMillis = 300)), // Animate content size changes
//                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp), // Padding around the list
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(cartItems, key = { it.id }) { item ->
//                        CartItemRow(
//                            item = item,
//                            onIncrement = { viewModel.incrementQuantity(item.id) },
//                            onDecrement = { viewModel.decrementQuantity(item.id) },
//                            onDelete = { itemToDelete = item }
//                        )
//                    }
//                }
//            }
//        }
//
//        // Confirmation Dialog with animation
//        AnimatedVisibility(
//            visible = itemToDelete != null,
//            enter = fadeIn(animationSpec = tween(200)) + expandVertically(animationSpec = tween(200), expandFrom = Alignment.CenterVertically),
//            exit = fadeOut(animationSpec = tween(200)) + shrinkVertically(animationSpec = tween(200), shrinkTowards = Alignment.CenterVertically)
//        ) {
//            itemToDelete?.let { item ->
//                AlertDialog(
//                    onDismissRequest = { itemToDelete = null },
//                    confirmButton = {
//                        TextButton(
//                            onClick = {
//                                viewModel.removeFromCart(item.id)
//                                itemToDelete = null
//                                scope.launch {
//                                    snackbarHostState.showSnackbar(
//                                        "\"${item.name}\" removed from cart."
//                                    )
//                                }
//                            },
//                            colors = ButtonDefaults.textButtonColors(
//                                contentColor = MaterialTheme.colorScheme.error // Red for confirm delete
//                            )
//                        ) {
//                            Text("Remove")
//                        }
//                    },
//                    dismissButton = {
//                        TextButton(
//                            onClick = { itemToDelete = null },
//                            colors = ButtonDefaults.textButtonColors(
//                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant // Muted for cancel
//                            )
//                        ) {
//                            Text("Cancel")
//                        }
//                    },
//                    title = {
//                        Text("Remove Item", style = MaterialTheme.typography.titleLarge)
//                    },
//                    text = {
//                        Text("Remove \"${item.name}\" from your cart?")
//                    },
//                    containerColor = MaterialTheme.colorScheme.surface,
//                    titleContentColor = MaterialTheme.colorScheme.onSurface,
//                    textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    shape = RoundedCornerShape(28.dp),
//                    tonalElevation = 6.dp
//                )
//            }
//        }
//    }
//}
//
///** Individual cart row with quantity controls and styling */
//@Composable
//private fun CartItemRow(
//    item: CartItem,
//    onIncrement: () -> Unit,
//    onDecrement: () -> Unit,
//    onDelete: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 8.dp, vertical = 4.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp), // Slightly more rounded for individual items
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // More pronounced elevation
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Use surface for item cards
//    ) {
//        Row(
//            Modifier
//                .padding(16.dp) // Increased internal padding
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Product Image
//            AsyncImage(
//                model = item.imageUrl,
//                contentDescription = item.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(96.dp) // Larger image
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)),
//                contentScale = androidx.compose.ui.layout.ContentScale.Crop
//            )
//
//            Spacer(Modifier.width(16.dp)) // Increased spacing
//
//            // Product Details and Quantity Controls
//            Column(Modifier.weight(1f)) {
//                Text(
//                    item.name,
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
//                    color = MaterialTheme.colorScheme.onSurface,
//                    maxLines = 2, // Allow name to wrap
//                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
//                )
//                Spacer(Modifier.height(4.dp))
//                Text(
//                    "$${"%.2f".format(item.price)}", // Individual price
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.secondary // Golden secondary for individual price
//                )
//                Spacer(Modifier.height(8.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    // Decrement Button
//                    IconButton(
//                        onClick = onDecrement,
//                        enabled = item.quantity > 1, // Disable if quantity is 1
//                        colors = IconButtonDefaults.iconButtonColors(
//                            contentColor = MaterialTheme.colorScheme.primary, // Golden tint
//                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f) // Muted when disabled
//                        )
//                    ) {
//                        Icon(
//                            Icons.Default.KeyboardArrowLeft,
//                            contentDescription = "Decrease quantity"
//                        )
//                    }
//
//                    // Quantity Display
//                    Surface( // Use Surface for quantity to give it a background
//                        modifier = Modifier
//                            .width(48.dp) // Wider for quantity
//                            .clip(RoundedCornerShape(8.dp)),
//                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f) // Subtle background
//                    ) {
//                        Text(
//                            "${item.quantity}",
//                            style = MaterialTheme.typography.titleMedium,
//                            modifier = Modifier.padding(vertical = 4.dp), // Padding inside surface
//                            textAlign = TextAlign.Center,
//                            color = MaterialTheme.colorScheme.onSurface // Golden text
//                        )
//                    }
//
//
//                    // Increment Button
//                    IconButton(
//                        onClick = onIncrement,
//                        colors = IconButtonDefaults.iconButtonColors(
//                            contentColor = MaterialTheme.colorScheme.primary // Golden tint
//                        )
//                    ) {
//                        Icon(
//                            Icons.Default.KeyboardArrowRight,
//                            contentDescription = "Increase quantity"
//                        )
//                    }
//                    Spacer(Modifier.width(8.dp))
//                    Text(
//                        "Subtotal: $${"%.2f".format(item.price * item.quantity)}", // Item subtotal
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontWeight = FontWeight.SemiBold,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant // Muted subtotal
//                    )
//                }
//            }
//
//            // Delete Item Button
//            IconButton(onClick = onDelete) {
//                Icon(
//                    Icons.Default.Delete,
//                    contentDescription = "Remove item",
//                    tint = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//    }
//}





import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.* // Make sure this is present
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndichu.kulturekart.data.CartViewModel
import com.ndichu.kulturekart.model.CartItem
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel = viewModel() // Ensure this is viewModel() to get the correct instance
) {
    // THE CRUCIAL PART: Collect the StateFlow from the ViewModel
    // This will trigger recomposition whenever cartItems changes in the ViewModel
    val cartItems by viewModel.cartItems.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var itemToDelete by remember { mutableStateOf<CartItem?>(null) } // For AlertDialog

    val shippingCost = 5.00
    val subtotal = cartItems.sumOf { it.price * it.quantity } // Ensure price is double
    val total = subtotal + shippingCost

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        " Your Cart ",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back to previous screen",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    if (cartItems.isNotEmpty()) {
                        TextButton(onClick = {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Clear all items from cart?",
                                    actionLabel = "Yes",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.clearCart(context) // Call clearCart with context
                                    snackbarHostState.showSnackbar("Cart cleared!")
                                }
                            }
                        }) {
                            Text(
                                "Clear All",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Subtotal:", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$${"%.2f".format(subtotal)}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Shipping:", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$${"%.2f".format(shippingCost)}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                }
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("$${"%.2f".format(total)}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.checkout(context) { success -> // Call checkout with context and callback
                            scope.launch {
                                if (success) {
                                    snackbarHostState.showSnackbar(
                                        "Checkout successful! Total: $${"%.2f".format(total)}"
                                    )
                                    // No need to call clearCart here, checkout already does it
                                    navController.navigate(ROUTE_DASHBOARD) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    snackbarHostState.showSnackbar(
                                        "Checkout failed. Please try again."
                                    )
                                }
                            }
                        }
                    },
                    enabled = cartItems.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("Proceed to Checkout", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(8.dp))

                TextButton(
                    onClick = { navController.navigate(ROUTE_DASHBOARD) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Continue Shopping", fontSize = 16.sp)
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (cartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Empty Cart",
                        modifier = Modifier.size(96.dp),
                        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "Your KultureKart is empty!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Start exploring unique cultural treasures.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate(ROUTE_DASHBOARD) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Text("Discover Treasures", fontSize = 16.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .animateContentSize(animationSpec = tween(durationMillis = 300)),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems, key = { it.id }) { item ->
                        CartItemRow(
                            item = item,
                            onIncrement = { viewModel.incrementQuantity(item.id) },
                            onDecrement = { viewModel.decrementQuantity(item.id) },
                            onDelete = { itemToDelete = item } // Set itemToDelete for dialog
                        )
                    }
                }
            }
        }

        // Confirmation Dialog with animation
        AnimatedVisibility(
            visible = itemToDelete != null,
            enter = fadeIn(animationSpec = tween(200)) + expandVertically(animationSpec = tween(200), expandFrom = Alignment.CenterVertically),
            exit = fadeOut(animationSpec = tween(200)) + shrinkVertically(animationSpec = tween(200), shrinkTowards = Alignment.CenterVertically)
        ) {
            itemToDelete?.let { item ->
                AlertDialog(
                    onDismissRequest = { itemToDelete = null },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.removeFromCart(item.id)
                                itemToDelete = null
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "\"${item.name}\" removed from cart."
                                    )
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Remove")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { itemToDelete = null },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text("Cancel")
                        }
                    },
                    title = {
                        Text("Remove Item", style = MaterialTheme.typography.titleLarge)
                    },
                    text = {
                        Text("Remove \"${item.name}\" from your cart?")
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(28.dp),
                    tonalElevation = 6.dp
                )
            }
        }
    }
}

/** Individual cart row with quantity controls and styling */
@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "$${"%.2f".format(item.price)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onDecrement,
                        enabled = item.quantity > 1,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Decrease quantity")
                    }

                    Surface(
                        modifier = Modifier
                            .width(48.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                    ) {
                        Text(
                            "${item.quantity}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 4.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = onIncrement,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Increase quantity")
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Subtotal: $${"%.2f".format(item.price * item.quantity)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove item",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}