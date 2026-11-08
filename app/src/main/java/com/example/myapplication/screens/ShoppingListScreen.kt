package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ShoppingItem
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.BorderOutline
import com.example.myapplication.ui.theme.Error
import com.example.myapplication.ui.theme.FieldAndBarBackground
import com.example.myapplication.ui.theme.TextPrimary
import com.example.myapplication.ui.theme.TextSecondaryAndIcons


@Composable
fun ShoppingListScreen() {
    // Тестовые данные (в будущем будут браться из БД)
    val shoppingItems = remember {
        mutableStateListOf(
            ShoppingItem(1, "Молоко", "1 л", "Молочное", false),
            ShoppingItem(2, "Сыр", "200 г", "Молочное", true),
            ShoppingItem(3, "Куриное филе", "500 г", "Мясо", false),
            ShoppingItem(4, "Фарш", "300 г", "Мясо", false),
            ShoppingItem(5, "Лук", "2 шт", "Овощи", true),
            ShoppingItem(6, "Чеснок", "1 головка", "Овощи", false)
        )
    }

    // Сколько осталось купить
    val itemsLeft = shoppingItems.count { !it.isBought }
    val totalItems = shoppingItems.size

    // Группировка по категориям
    val groupedItems = shoppingItems.groupBy { it.category }

    // --- СОСТОЯНИЯ ДЛЯ ДИАЛОГА ---
    var showAddDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }
    var newItemAmount by remember { mutableStateOf("") }

    // Всплывающее окно ввода
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Добавить покупку") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        label = { Text("Название (например, Хлеб)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newItemAmount,
                        onValueChange = { newItemAmount = it },
                        label = { Text("Количество (например, 1 шт)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newItemName.isNotBlank()) {
                            // Новый продукт идет в Разное
                            shoppingItems.add(
                                ShoppingItem(
                                    id = (shoppingItems.maxOfOrNull { it.id } ?: 0) + 1,
                                    name = newItemName,
                                    amount = newItemAmount.ifBlank { "1 шт" },
                                    category = "Разное",
                                    isBought = false
                                )
                            )
                            // Очистка и закрытие окна
                            newItemName = ""
                            newItemAmount = ""
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Добавить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Отмена")
                }
            }
        )
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Список покупок",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            // Иконка корзины
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(FieldAndBarBackground)
                    .clickable { /* TODO: Очистить список */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Очистить",
                    tint = Error
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Счетчик
        Text(
            text = "Осталось $itemsLeft из $totalItems",
            fontSize = 14.sp,
            color = TextSecondaryAndIcons
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список по категориям
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            groupedItems.forEach { (category, items) ->
                item {
                    // Заголовок категории
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                //  куплено / не куплено
                                val index = shoppingItems.indexOf(item)
                                if (index != -1) {
                                    shoppingItems[index] = item.copy(isBought = !item.isBought)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Чекбокс
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (item.isBought) TextPrimary else Color.White)
                                .border(1.dp, if (item.isBought) TextPrimary else BorderOutline, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (item.isBought) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Название (куплено - зачеркнуто)
                        Text(
                            text = item.name,
                            fontSize = 16.sp,
                            color = if (item.isBought) TextSecondaryAndIcons else TextPrimary,
                            textDecoration = if (item.isBought) TextDecoration.LineThrough else TextDecoration.None,
                            modifier = Modifier.weight(1f)
                        )

                        // Количество
                        Text(
                            text = item.amount,
                            fontSize = 16.sp,
                            color = TextSecondaryAndIcons,
                            textDecoration = if (item.isBought) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }
            }
        }

        // Кнопка Добавить вручную
        Button(
            onClick = { showAddDialog = true },  /* TODO: Открыть окно добавления */
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = TextPrimary),
            border = BorderStroke(1.dp, BorderOutline),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Добавить",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Добавить вручную",
                fontSize = 16.sp
            )
        }
    }
}
