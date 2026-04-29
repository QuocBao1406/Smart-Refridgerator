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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridge.data.model.FoodItem
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetaildEditDialog(
    food: FoodItem,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, Long) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    
    var nameInput by remember { mutableStateOf(food.name) }
    var quantityInput by remember { mutableStateOf(food.quantity.toString()) }
    var unitInput by remember { mutableStateOf(food.unit) }
    var typeInput by remember { mutableStateOf(food.type) }
    var expiryInput by remember { mutableStateOf(food.expiry) }
    var selectedIconKey by remember { mutableStateOf(food.image) }

    var isEditing by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val unitOption = listOf("kg", "g", "hộp", "quả", "củ", "lít", "ml", "bó", "gói", "lon", "chai", "khác")
    val typeOption = listOf("Thịt & Cá", "Rau củ", "Trái cây", "Đồ uống", "Trứng", "Khác")
    val foodIconKeys = listOf(
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
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isEditing) "Sửa Món Ăn" else "Chi Tiết Món Ăn",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF2C3241)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFFF7F8FA)),
                    contentAlignment = Alignment.Center
                ) {
                    FoodIcon(iconKey = selectedIconKey, modifier = Modifier.size(40.dp))
                }

                Spacer(modifier = Modifier.width(20.dp))


                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Tên thực phẩm",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PremiumInputField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        placeholder = "Tên món ăn",
                        enabled = isEditing,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (isEditing) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Thay đổi biểu tượng",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 13.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(foodIconKeys) { key ->
                        val isSelected = selectedIconKey == key
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color(0xFF00BCD4) else Color(0xFFF7F8FA))
                                .clickable { selectedIconKey = key },
                            contentAlignment = Alignment.Center
                        ) {
                            FoodIcon(
                                iconKey = key,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(0.3f)) {
                    Text("Số lượng", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    PremiumInputField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        placeholder = "Số lượng",
                        keyboardType = KeyboardType.Number,
                        enabled = isEditing
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(0.7f)) {
                    Text("Đơn vị", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(unitOption) { unit ->
                            FilterChip(
                                selected = unitInput == unit,
                                onClick = { if (isEditing) unitInput = unit },
                                label = { Text(unit) },
                                shape = CircleShape,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF00BCD4),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            
            Text("Phân loại", modifier = Modifier.fillMaxWidth(), fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Column {
                val chunked = typeOption.chunked(3)
                chunked.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { typeName ->
                            val isSelected = typeInput == typeName
                            Surface(
                                onClick = { if (isEditing) typeInput = typeName },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) Color(0xFF00BCD4) else Color(0xFFF7F8FA),
                                modifier = Modifier.weight(1f).height(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(typeName, color = if (isSelected) Color.White else Color(0xFF8E8E93), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Text("Hạn sử dụng", modifier = Modifier.fillMaxWidth(), fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                onClick = { if (isEditing) showDatePicker = true },
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF7F8FA),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Event, null, tint = Color.Gray, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    Text(sdf.format(Date(expiryInput)), fontWeight = FontWeight.Bold, color = Color(0xFF2C3241))
                    Spacer(modifier = Modifier.weight(1f))
                    if (isEditing) {
                        Text("Thay đổi", color = Color(0xFF00BFA5), fontSize = 13.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (isEditing) {
                    Button(
                        onClick = { isEditing = false },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2F2F7))
                    ) {
                        Text("Hủy", color = Color(0xFF1C1C1E), fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            if (nameInput.isNotBlank()) {
                                onSave(nameInput, selectedIconKey, quantityInput, unitInput, typeInput, expiryInput)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA5))
                    ) {
                        Text("Lưu", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2F2F7))
                    ) {
                        Text("Đóng", color = Color(0xFF1C1C1E), fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { isEditing = true },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C1C1E))
                    ) {
                        Text("Chỉnh Sửa", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
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

@Composable
fun PremiumInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = false,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = enabled,

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