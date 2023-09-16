package com.example.calculator.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        reset()
    }

    fun reset() {
        _uiState.value = CalculatorUiState()
    }

    fun write(expression: String) {
        _uiState.value = CalculatorUiState(
            displayExpression = _uiState.value.displayExpression + expression,
            displayResult = _uiState.value.displayResult
        )
    }

    fun remove() {
        var expression = _uiState.value.displayExpression
        if (expression.isNotEmpty()) {
            if (expression.last().isWhitespace()) {
                expression = expression.dropLast(2)
            }
            expression = expression.dropLast(1)
        }
        _uiState.value = CalculatorUiState(
            displayExpression = expression,
            displayResult = _uiState.value.displayResult
        )
    }

    fun calculate() {
        val expression = _uiState.value.displayExpression
        val lastResult = _uiState.value.displayResult
        var newExpression = "$lastResult $expression"
        if (lastResult.isNotEmpty()) {
            if (!expression.startsWith(" +") && !expression.startsWith(" -")
                && !expression.startsWith(" *") && !expression.startsWith(" /")
            ) {
                newExpression += "?"
            }
        }
        val result = cppCalculate(newExpression)
        if (result == cppError()) {
            _uiState.value = CalculatorUiState(
                displayExpression = expression,
                displayResult = lastResult,
                calculationState = "ERROR: Invalid expression!"
            )
        } else {
            _uiState.value = CalculatorUiState(
                displayResult = result,
            )
        }
        Log.i("App Calculator", "Expression: $newExpression Result: $result")
    }

    // native-lib.cpp
    private external fun cppCalculate(expression: String) : String
    private external fun cppError() : String
}