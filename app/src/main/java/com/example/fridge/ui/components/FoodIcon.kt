package com.example.fridge.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.fridge.R

fun getFoodIconRes(iconKey: String): Int{
    return when (iconKey) {
        "meat" -> R.drawable.meat
        "chicken" -> R.drawable.chicken_leg
        "fish" -> R.drawable.fish
        "milk" -> R.drawable.milk
        "egg" -> R.drawable.egg
        "shrimp" -> R.drawable.shrimp
        "broccoli" -> R.drawable.broccoli
        "cabbage" -> R.drawable.cabbage
        "carrot" -> R.drawable.carrot
        "apple" -> R.drawable.apple
        "banana" -> R.drawable.banana
        "beer" -> R.drawable.beer_can
        "cheese" -> R.drawable.cheese
        "chocolate" -> R.drawable.dark_chocolate
        "chili" -> R.drawable.chili
        "grape" -> R.drawable.grape
        "ice_cream" -> R.drawable.ice_cream
        "potato" ->  R.drawable.potato
        "tomato" ->  R.drawable.tomato
        "sweet_potato" ->  R.drawable.sweet_potato
        "soft_drink" ->  R.drawable.soft_drink
        else -> R.drawable.beer_can
    }
}

@Composable
fun FoodIcon(
    iconKey: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    contentDescription: String? = null
) {
    Icon(
        painter = painterResource(id = getFoodIconRes(iconKey)),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier,
    )
}