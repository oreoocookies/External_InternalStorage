package com.example.externalinternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button internalStorageButton;
    private Button externalStorageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internalStorageButton = findViewById(R.id.buttonInternalStorage);
        externalStorageButton = findViewById(R.id.buttonEksternalStorage);

        internalStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InternalActivity.class);
                startActivity(intent);
            }
        });

        externalStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EksternalActivity.class);
                startActivity(intent);
            }
        });
    }
}
