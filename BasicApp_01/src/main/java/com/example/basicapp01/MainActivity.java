package com.example.basicapp01;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private CheckBox checkBox1;
    private CheckBox checkBox2;

    private static final String TAG_LOG = MainActivity.class.getSimpleName() + "_BASIC01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        textView = findViewById(R.id.textOuput);

        editText = findViewById(R.id.textInput);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d(TAG_LOG, "text ok");
                updateText(editText.getText().toString());
                return true;
            }
            return false;
        });
    }

    public void updateText(String text) {
        editText.setText("");
        textView.setText(text);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        editText.setTypeface(null, Typeface.NORMAL);
        Log.d(TAG_LOG, "updated text");
    }

    public void clearText (View v) {
        editText.setText("");
        Log.d(TAG_LOG, "clean text");
    }

    public void reverseText (View v) {
        String text = new StringBuffer(editText.getText().toString()).reverse().toString();
        editText.setText(text);
        Log.d(TAG_LOG, "inverted text");
    }

    public void switchText (View v) {

        if (checkBox1.isChecked() && checkBox2.isChecked()) {
            editText.setTypeface(null, Typeface.BOLD_ITALIC);
            Log.d(TAG_LOG, "text in bold and italic");
        } else if (checkBox1.isChecked()) {
            editText.setTypeface(null, Typeface.ITALIC);
            Log.d(TAG_LOG, "text in italic");
        } else if (checkBox2.isChecked()) {
            editText.setTypeface(null, Typeface.BOLD);
            Log.d(TAG_LOG, "text in bold");
        } else {
            editText.setTypeface(null, Typeface.NORMAL);
            Log.d(TAG_LOG, "text in normal");
        }
    }

}