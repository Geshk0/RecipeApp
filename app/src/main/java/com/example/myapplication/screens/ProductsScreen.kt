package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Product
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.FieldAndBarBackground
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons

@Composable
fun ProductsScreen(
    onProfileClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onFindRecipesClick: () -> Unit
) {
    // Тестовые данные
    val products = remember {
        mutableStateListOf(
            Product("Морковь", "3 шт.", true, R.drawable.carrot),
            Product("Сыр", "200 гр.", true, R.drawable.cheese),
            Product("Яйца", "10 шт.", false, R.drawable.egg),
            Product("Молоко", "1 л.", true, R.drawable.milk),
            Product("Мясо", "1 кг.", true, R.drawable.meat)
        )
    }

    // Состояние для поиска
    var searchText by remember { mutableStateOf("") }

    // Фильтр для поиска
    val filteredProducts = products.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Верхний тулбар
        Row(
            modifier = Modifier.fillMaxWidth(),
            // .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Текст
            Text(
                text = "Мои продукты",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            // Иконка
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(FieldAndBarBackground)
                    .border(1.dp, BorderOutline, CircleShape)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person,
                    contentDescription = "Профиль",
                    tint = TextPrimary
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Поиск
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF4A4A4A)), // цвет кнопки серый, но у меня есть другой + оранжевый хорошо выглядит
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Search,
                    contentDescription = "Поиск",
                    tint = Color.White
                ) // цвет лупы белый
            }

            // Поле ввода
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, end = 12.dp),
                decorationBox = { innerTextField ->
                    // Плейсхолдер, если поле пустое
                    if (searchText.isEmpty()) {
                        Text("Поиск продуктов",
                            color = TextSecondaryAndIcons,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список продуктов
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredProducts) { product ->
                ProductCard(product) { isChecked ->
                    // Находим индекс в оригинальном списке, чтобы обновить галочку
                    val index = products.indexOf(product)
                    if (index != -1) {
                        products[index] = product.copy(isChecked = isChecked)
                    }
                }
            }
        }

        // Кнопка "+"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF4A4A4A)) // тоже самое
                    .clickable { onAddProductClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = Color.White, // тоже самое
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Подобрать рецепты
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(FieldAndBarBackground)
                .clickable { onFindRecipesClick() }
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Подобрать рецепты", fontSize = 16.sp, color = TextPrimary)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF4A4A4A)), // тоже самое
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Далее",
                    tint = Color.White // тоже самое
                )
            }
        }
    }
}

// Карточка одного продукта (не меняется)
@Composable
fun ProductCard(product: Product, onCheckedChange: (Boolean) -> Unit) {
    val cardBackground = Color(0xFFE6E6E6)
    val cardBorder = BorderOutline

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(cardBackground)
            .border(1.dp, cardBorder, RoundedCornerShape(12.dp))
            .clickable { onCheckedChange(!product.isChecked) }
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .padding(end = 64.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.amount,
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(1.dp, cardBorder, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (product.isChecked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Выбрано",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}