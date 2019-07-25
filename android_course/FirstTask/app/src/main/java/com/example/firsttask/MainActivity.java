package com.example.firsttask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private EditText phrase;
    private Button toaster;
    private View.OnClickListener onToasterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(phrase.getText())) {
                Toast.makeText(getApplicationContext(), phrase.getText(), Toast.LENGTH_LONG).show();
                Intent startNextIntent = new Intent(MainActivity.this, NextActivity.class);
                startNextIntent.putExtra(NextActivity.BIG_TEXT, phrase.getText().toString());
                startActivity(startNextIntent);
            } else Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        phrase = findViewById(R.id.textChecker);
        toaster = findViewById(R.id.makeToastButton);
        toaster.setOnClickListener(onToasterClickListener);
    }
}