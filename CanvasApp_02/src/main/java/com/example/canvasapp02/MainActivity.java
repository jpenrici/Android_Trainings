package com.example.canvasapp02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCanvas vc = new ViewCanvas(this);

        linearLayout = findViewById(R.id.linearLayoutView);
        linearLayout.addView(vc);

        vc.setContentDescription(getResources().getString(R.string.description));

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> vc.clean());
    }
}