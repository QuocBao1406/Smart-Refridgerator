package com.example.fridge.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridge.data.model.FoodItem
import com.example.fridge.data.repository.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider

class FridgeViewModel(private val repository: FoodRepository) : ViewModel() {
    val allFoodItem: StateFlow<List<FoodItem>> = repository.allFood.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addFood(name: String, amount: Double, unit: String, expiryDate: Long, category: String) {
        viewModelScope.launch {
            val newItem = FoodItem(
                name = name,
                amount = amount,
                unit = unit,
                expiryDate = expiryDate,
                addedDate = System.currentTimeMillis(),
                category = category
            )
            repository.insert(newItem)
        }
    }

    fun deleteFood(food: FoodItem) {
        viewModelScope.launch {
            repository.delete(food)
        }
    }

    fun updateFood(food: FoodItem) {
        viewModelScope.launch {
            repository.update(food)
        }
    }

    fun getIngredientsForAI(onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            val names = repository.getFoodNamesForAI()
            onResult(names)
        }
    }
}

class FridgeViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if ( modelClass.isAssignableFrom(FridgeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FridgeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}