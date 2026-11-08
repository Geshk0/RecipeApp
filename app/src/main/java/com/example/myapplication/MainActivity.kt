package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.screens.AddProductScreen
import com.example.myapplication.screens.CookingStepsScreen
import com.example.myapplication.screens.FavoritesScreen
import com.example.myapplication.screens.OnboardingScreen
import com.example.myapplication.screens.ProductsScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.screens.RecipeDetailScreen
import com.example.myapplication.screens.RecipesScreen
import com.example.myapplication.screens.ShoppingListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

// Описание моделей
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)

@Composable
fun MainScreen() {
    // Состояния данных
    val favoriteRecipes = remember { mutableStateListOf<Recipe>() }
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    // Состояния навигации
    var showOnboarding by remember { mutableStateOf(!prefs.getBoolean("onboarding_done", false)) }
    var selectedItemIndex by remember { mutableStateOf(0) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    var isCooking by remember { mutableStateOf(false) }
    var showProfile by remember { mutableStateOf(false) }
    var showAddProduct by remember { mutableStateOf(false) }

    // Экраны поверх основных

    //  Онбординг
    if (showOnboarding) {
        OnboardingScreen(onFinish = {
            // Запоминаем, что онбординг пройден
            prefs.edit().putBoolean("onboarding_done", true).apply()
            showOnboarding = false
        })
        return
    }

    // Добавление продукта
    if (showAddProduct) {
        AddProductScreen(
            onBackClick = { showAddProduct = false },
            onSaveClick = { showAddProduct = false }
        )
        return
    }


    // Профиль
    if (showProfile) {
        ProfileScreen(onBackClick = { showProfile = false })
        return
    }

    // Пошаговое приготовления
    if (isCooking && selectedRecipe != null) {
        CookingStepsScreen(
            recipeTitle = selectedRecipe!!.title,
            stepsImage = selectedRecipe!!.stepsImages,
            onBackClick = { isCooking = false },
            onFinishClick = {
                // Возвращение на главный экран
                isCooking = false
                selectedRecipe = null
            }
        )
        return
    }

    // Карточки рецепта
    if (selectedRecipe != null) {
        val isFav = favoriteRecipes.contains(selectedRecipe!!)
        RecipeDetailScreen(
            recipe = selectedRecipe!!,
            isFavorite = isFav,
            onFavoriteClick = {
                // Если уже в избранном - удаляем. Если нет - добавляем.
                if (isFav) favoriteRecipes.remove(selectedRecipe!!)
                else favoriteRecipes.add(selectedRecipe!!)
            },
            onBackClick = { selectedRecipe = null },
            onStartCookingClick = { isCooking = true }
        )
        return
    }

    // Нижний тулбар
    val navItems = listOf(
        BottomNavItem("Продукты", Icons.Default.Kitchen) {
            ProductsScreen(
                onProfileClick = { showProfile = true },
                onAddProductClick = { showAddProduct = true },
                onFindRecipesClick = { selectedItemIndex = 1 }
            )
        },
        BottomNavItem("Рецепты", Icons.Default.Restaurant) {
            RecipesScreen(
                onRecipeClick = { recipe -> selectedRecipe = recipe },
                onSettingsClick = { showProfile = true },
                favoriteRecipes = favoriteRecipes
            )
        },
        BottomNavItem("Покупки", Icons.Default.ShoppingCart) { ShoppingListScreen() },
        BottomNavItem("Избранное", Icons.Default.Favorite) {
            FavoritesScreen(
                onRecipeClick = { recipe -> selectedRecipe = recipe },
                favoriteRecipes = favoriteRecipes
            )
        }
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // Отображается контент выбранного экрана
        // padding чтобы контент не залезал под нижний бар
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
            navItems[selectedItemIndex].screen()
        }
    }
}