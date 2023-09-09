package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName() + "_ONCLICK";
    private static final String DIGITS  = "0123456789";
    private static final String SPACE   = " ";
    private static final String EMPTY   = "";
    private static final String EOL     = "\n";
    private static final char SEPARATOR = ',';

    private String expression;
    private String answer;
    private String error;

    private EditText display;
    private TextView txtView_info;
    private ToggleButton toggleBtn;

    // Calculator: Java version
    private final Calculator jCalculator = new Calculator();

    // Calculator: Cpp version
    static {
        System.loadLibrary("calculatorLib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.txt_result);
        toggleBtn = findViewById(R.id.toggleButton_useCpp);
        txtView_info = findViewById(R.id.textView_info);

        expression = EMPTY;
        answer = EMPTY;
        error = EMPTY;

        toggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                txtView_info.setText(helloJNI());
            } else {
                txtView_info.setText(R.string.txt_label_java);
            }
        });
    }

    public void onClick(@NonNull View view) {
        final int id = view.getId();
        String result = EMPTY;
        String calc = EMPTY;

        if (id == R.id.btn_ce) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < expression.length() - 1; i++) {
                str.append(expression.charAt(i));
            }
            expression = str.toString();
        }

        if (id == R.id.btn_ac) {
            expression = EMPTY;
            answer = EMPTY;
            error = EMPTY;
        }

        if (id == R.id.btn_equal) {
            if (!expression.isEmpty()) {
                calc = answer + expression;
                if (DIGITS.contains(String.valueOf(expression.charAt(0))) && !answer.isEmpty()) {
                    calc = answer + "?" + expression;
                }
                String ERROR;
                if (toggleBtn.isChecked()) {
                    result = cpp_calculate(calc);
                    ERROR = cpp_error();
                } else {
                    result = jCalculator.calculate(calc);
                    ERROR = Calculator.ERROR;
                }
                if (!result.equals(ERROR)) {
                    answer = result;
                    expression = EMPTY;
                    error = EMPTY;
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

        String msg = "Ans: " + answer + " Expr: " + expression + " Calc: " + calc + " Result: " + result + " Run: " + (toggleBtn.isChecked() ? "Cpp" : "Java");
        Log.d(LOG_TAG, msg);
    }

    private void updateDisplay(String answer, String expression) {
        String result = answer.replace(SPACE, EMPTY) + EOL + error + EOL + expression;
        if (!answer.isEmpty()) {
            result = getResources().getString(R.string.txt_answer) + SPACE + result;
        }
        display.setText(result);
    }

    // native-lib.cpp
    private native String cpp_calculate(String expression);
    private native String cpp_error();
    private native String helloJNI();
}