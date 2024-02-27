package com.example.calcy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notkamui.keval.Keval

class CalculatorViewmodel : ViewModel() {


    private val _expression = MutableLiveData<String>()
    val expression: LiveData<String>
        get() = _expression

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    init {
        _expression.value = "0"
        _result.value = "0"
    }

    fun onDigitClick(digit: String) {
        if (_expression.value == "0") {
            _expression.value= digit
        } else {
            _expression.value += digit
        }

    }

    fun onOperatorClick(operator: String) {
        val currentExpression = _expression.value ?: ""
        // Checking if the current expression ends with an operator
        if (currentExpression.isNotEmpty() && currentExpression.last().toString().matches(Regex("[%*/+-.]"))) {
            // If the current expression already ends with an operator, replace it with the new operator
            _expression.value = currentExpression.dropLast(1) + operator
        } else {
            // Otherwise, append the new operator
            _expression.value += operator
        }
    }

    fun onEqualClick() {
        val expression = _expression.value ?: ""
        try {
            val result = Keval.eval(expression)
            val formattedResult = if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            _result.value = formattedResult
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }
    fun onDeleteClick() {
        val currentExpression = _expression.value ?: ""
        if (currentExpression.isNotEmpty()) {
            _expression.value = currentExpression.dropLast(1)
        }
    }

    fun clear() {
        _expression.value = "0"
        _result.value = "0"
    }
}
