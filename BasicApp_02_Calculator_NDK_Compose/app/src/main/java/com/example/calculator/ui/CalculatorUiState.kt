package com.example.calculator.ui

data class CalculatorUiState(
    val displayExpression: String = "",
    val displayResult: String = "",
    val calculationState: String = ""
)