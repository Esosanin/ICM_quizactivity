package com.example.quizactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTrue, btnFalse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFalse = (Button) findViewById(R.id.btn_false);
        btnTrue = (Button) findViewById(R.id.btn_true);

        btnFalse.setOnClickListener(this::onClick);
        btnTrue.setOnClickListener(this::onClick);

    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View v){
        switch(v.getId()){
            case R.id.btn_false:
                Toast.makeText(v.getContext(),"Correcto",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_true:
                Toast.makeText(v.getContext(),"Incorrecto",Toast.LENGTH_SHORT).show();
                break;
        }

    }

}