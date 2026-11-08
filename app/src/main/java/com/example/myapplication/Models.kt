package com.example.myapplication

import androidx.annotation.DrawableRes

// Модель данных для продукта
data class Product(
    val name: String,
    val amount: String,
    val isChecked: Boolean,
    @DrawableRes val imageRes: Int
)

// Модель данных для рецепта
data class Recipe(
    val title: String,
    val time: String,
    val calories: String,
    val portions: Int,
    val isAvailable: Boolean,
    @DrawableRes val imageRes: Int,
    val stepsImages: List<Int> = emptyList()
)

// Модель данных для списка покупок
data class ShoppingItem(
    val id: Int,
    val name: String,
    val amount: String,
    val category: String,
    var isBought: Boolean = false
)

// Функция для склонения слова "порция"
fun getPortionsText(count: Int): String {
    val lastDigit = count % 10
    val lastTwoDigits = count % 100

    return when {
        // 11, 12, 13, 14 порций
        lastTwoDigits in 11..14 -> "$count порций"
        // 1 порция
        lastDigit == 1 -> "$count порция"
        // 2, 3, 4 порции
        lastDigit in 2..4 -> "$count порции"
        // Все остальное (0, 5, 6, 7, 8, 9, 10)
        else -> "$count порций"
    }
}