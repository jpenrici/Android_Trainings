package com.example.basicapp03;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.toString() + "_STATUS";
    private static final String COUNTER_STATE = "COUNTER_STATE";
    private static final String FILENAME = "counter.txt";

    private TextView textView;
    private Button button;

    private File root;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate ...");

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1000);
        }

        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        counter = 0;
        if (savedInstanceState != null) {
            counter = Integer.parseInt(savedInstanceState.getString(COUNTER_STATE));
            Log.d(LOG_TAG, "counter updated ...");
        }

        button = findViewById(R.id.button1);
        textView = findViewById(R.id.text_view);
        textView.setText(String.valueOf(counter));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart ...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume ...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause ...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop ...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy ...");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        counter++;
        outState.putString(COUNTER_STATE, String.valueOf(counter));
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState COUNTER_STATE ...");
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        textView.setText(savedInstanceState.getString(COUNTER_STATE));
        Log.d(LOG_TAG, "onRestoreInstanceState COUNTER_STATE ...");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                button.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                button.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void save(View view) {
        try {
            File file = new File(root, FILENAME);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(String.valueOf(counter).getBytes());
            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "counter saved ...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "file not found!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "error saving!");
        }
    }

}