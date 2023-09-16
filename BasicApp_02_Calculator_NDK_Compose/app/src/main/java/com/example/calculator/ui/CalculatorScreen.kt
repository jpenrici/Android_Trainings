package com.example.calculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.theme.LightGray
import com.example.calculator.ui.theme.MediumGray
import com.example.calculator.ui.theme.OperandColor

@Preview
@Composable
fun CalculatorScreen(calculatorViewModel: CalculatorViewModel = viewModel()) {
    val state by calculatorViewModel.uiState.collectAsState()
    val buttonSpacing = 8.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing),
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = LightGray,
                ),
                border = BorderStroke(2.dp, Color.DarkGray),
            ) {
                Text(
                    text = state.calculationState,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .padding(horizontal = 10.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 22.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = state.displayResult,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp)
                        .padding(horizontal = 10.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 32.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = state.displayExpression,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 56.dp)
                        .padding(horizontal = 10.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 32.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "(",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" ( ")
                }
                CalculatorButton(
                    label = ")",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" ) ")
                }
                CalculatorButton(
                    label = "CE",
                    color = LightGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.remove()
                }
                CalculatorButton(
                    label = "AC",
                    color = LightGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.reset()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "7",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("7")
                }
                CalculatorButton(
                    label = "8",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("8")
                }
                CalculatorButton(
                    label = "9",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("9")
                }
                CalculatorButton(
                    label = "/",
                    color = OperandColor,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" / ")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "4",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("4")
                }
                CalculatorButton(
                    label = "5",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("5")
                }
                CalculatorButton(
                    label = "6",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("6")
                }
                CalculatorButton(
                    label = "x",
                    color = OperandColor,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" * ")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "1",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("1")
                }
                CalculatorButton(
                    label = "2",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("2")
                }
                CalculatorButton(
                    label = "3",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("3")
                }
                CalculatorButton(
                    label = "-",
                    color = OperandColor,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" - ")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    label = "0",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write("0")
                }
                CalculatorButton(
                    label = ",",
                    color = MediumGray,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(",")
                }
                CalculatorButton(
                    label = "=",
                    color = OperandColor,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.calculate()
                }
                CalculatorButton(
                    label = "+",
                    color = OperandColor,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                ) {
                    calculatorViewModel.write(" + ")
                }
            }
        }
    }
}
