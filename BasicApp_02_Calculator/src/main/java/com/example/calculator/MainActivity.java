package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName() + "_ONCLICK";
    private static final char SEPARATOR = Calculator.SEPARATOR;
    private static final String DIGITS = Calculator.DIGITS;
    private static final String SPACE = Calculator.SPACE;
    private static final String EMPTY = Calculator.EMPTY;
    private static final String ERROR = Calculator.ERROR;
    private static final String EOL = "\n";

    private final Calculator calculator = new Calculator();
    private String expression;
    private String answer;
    private String error;

    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.txt_result);
        expression = "";
        answer = "";
        error = "";
    }

    public void onClick(View view) {

        final int id = view.getId();
        String result = "";
        String calc = "";

        if (id == R.id.btn_ce) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < expression.length() - 1; i++) {
                str.append(expression.charAt(i));
            }
            expression = str.toString();
        }

        if (id == R.id.btn_ac) {
            expression = "";
            answer = "";
            error = "";
        }

        if (id == R.id.btn_equal) {
            if (!expression.isEmpty()) {
                calc = answer + expression;
                if (DIGITS.contains(String.valueOf(expression.charAt(0))) &&
                        !answer.isEmpty()) {
                    calc = answer + "?" + expression;
                }
                result = calculator.calculate(calc);
                if (!result.equals(ERROR)) {
                    answer = result;
                    expression = "";
                    error = "";
                } else {
                    error = getResources().getString(R.string.txt_error) + SPACE + calc.replace(SPACE, EMPTY);
                }
            }
        }

        if (id == R.id.btn_0) {
            expression += "0";
        }
        if (id == R.id.btn_1) {
            expression += "1";
        }
        if (id == R.id.btn_2) {
            expression += "2";
        }
        if (id == R.id.btn_3) {
            expression += "3";
        }
        if (id == R.id.btn_4) {
            expression += "4";
        }
        if (id == R.id.btn_5) {
            expression += "5";
        }
        if (id == R.id.btn_6) {
            expression += "6";
        }
        if (id == R.id.btn_7) {
            expression += "7";
        }
        if (id == R.id.btn_8) {
            expression += "8";
        }
        if (id == R.id.btn_9) {
            expression += "9";
        }
        if (id == R.id.btn_dot) {
            expression += SEPARATOR;
        }
        if (id == R.id.btn_sum) {
            expression += " + ";
        }
        if (id == R.id.btn_subtract) {
            expression += " - ";
        }
        if (id == R.id.btn_divide) {
            expression += " / ";
        }
        if (id == R.id.btn_multiply) {
            expression += " * ";
        }
        if (id == R.id.btn_lparenthesis) {
            expression += " ( ";
        }
        if (id == R.id.btn_rparenthesis) {
            expression += " ) ";
        }

        updateDisplay(answer, expression);

        String msg = "A: " + answer + ", E: " + expression + ", C: " + calc + ", R: " + result;
        Log.d(LOG_TAG, msg);
    }

    private void updateDisplay(String answer, String expression) {
        String result = answer.replace(SPACE, EMPTY) + EOL + error + EOL + expression;
        if (!answer.isEmpty()) {
            result = getResources().getString(R.string.txt_answer) + SPACE + result;
        }
        display.setText(result);
    }

}