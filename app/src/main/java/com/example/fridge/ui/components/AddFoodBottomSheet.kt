package com.example.fridge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridge.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodBottomSheet(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, Long) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var nameInput by remember { mutableStateOf("") }
    var quantityInput by remember { mutableStateOf("1") }
    var unitInput by remember { mutableStateOf("Kg") }
    var typeInput by remember { mutableStateOf("Thịt & Cá") }
    var expiryInput by remember { mutableStateOf(System.currentTimeMillis() + (3L * 24 * 60 * 60 * 1000)) }
    var showDatePicker by remember { mutableStateOf(false) }


    val foodIconOptions:List<String> = listOf(
        "meat",
        "chicken",
        "fish",
        "milk",
        "egg",
        "shrimp",
        "broccoli",
        "cabbage",
        "carrot",
        "apple",
        "banana",
        "beer",
        "cheese",
        "chocolate",
        "chili",
        "grape",
        "ice_cream",
        "potato",
        "tomato",
        "sweet_potato",
    )
    var selectedIconKey by remember { mutableStateOf("meat") }


    val categoryOptions = listOf(
        CategoryItem("Thịt & Cá", R.drawable.meat_fish, 6),
        CategoryItem("Rau củ", R.drawable.vegetable, 7),
        CategoryItem("Trái cây", R.drawable.fruits, 8),
        CategoryItem("Đồ uống", R.drawable.soft_drinks, 10),
        CategoryItem("Trứng", R.drawable.eggs, 180),
        CategoryItem("Khác", R.drawable.balanced_diet, 10)
    )

    val unitOptions =
        listOf("kg", "g", "hộp", "quả", "củ", "lít", "ml", "bó", "gói", "lon", "chai", "khác")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFE5E5EA)) },
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Thêm Thực Phẩm",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1C1C1E)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Chọn biểu tượng hiển thị",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(foodIconOptions) { key ->
                    val isSelected = selectedIconKey == key
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) Color(0xFF00BFA5) else Color(0xFFF2F2F7))
                            .clickable { selectedIconKey = key },
                        contentAlignment = Alignment.Center
                    ) {
                        FoodIcon(
                            iconKey = key,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            PremiumInputField(
                value = nameInput,
                onValueChange = { nameInput = it },
                placeholder = "Tên món ăn (vd: Thịt heo...)",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Phân loại",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                val chunkedCategories = categoryOptions.chunked(3)
                chunkedCategories.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEach { item ->
                            CategoryChip(
                                item = item,
                                isSelected = typeInput == item.name,
                                onClick = {
                                    typeInput = item.name

                                    expiryInput =
                                        System.currentTimeMillis() + (item.defaultDays * 24 * 60 * 60 * 1000L)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                PremiumInputField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    placeholder = "Số lượng...",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(0.4f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                LazyRow(
                    modifier = Modifier.weight(0.6f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(unitOptions) { unit ->
                        FilterChip(
                            selected = unitInput == unit,
                            onClick = { unitInput = unit },
                            label = { Text(unit) },
                            shape = CircleShape,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF1C1C1E),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                "Hạn sử dụng",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                onClick = { showDatePicker = true },
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF2F2F7),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Event,
                        null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    Text(
                        sdf.format(Date(expiryInput)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1C1E)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Sửa",
                        color = Color(0xFF00BFA5),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        onSave(
                            nameInput,
                            selectedIconKey,
                            quantityInput,
                            unitInput,
                            typeInput,
                            expiryInput
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA5))
            ) {
                Text("Lưu vào tủ lạnh", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = expiryInput)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    expiryInput = datePickerState.selectedDateMillis ?: expiryInput
                    showDatePicker = false
                }) { Text("Xong", color = Color(0xFF00BFA5), fontWeight = FontWeight.Bold) }
            }
        ) { DatePicker(state = datePickerState) }
    }
}


data class CategoryItem(val name: String, val iconResId: Int, val defaultDays: Int)

@Composable
fun CategoryChip(
    item: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF00CEB8) else Color(0xFFF2F2F7),
        modifier = modifier.height(60.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )

            Text(
                text = item.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color(0xFF8E8E93)
            )
        }
    }
}

@Composable
fun PremiumInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF2F2F7))
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) Text(placeholder, color = Color(0xFF8E8E93), fontSize = 16.sp)
                innerTextField()
            }
        }
    )
}