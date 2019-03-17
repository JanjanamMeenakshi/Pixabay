package com.example.acer.pixabay;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.etext);
        button=findViewById(R.id.ebutton);

    }

    public void search(View view) {
        search=editText.getText().toString();
        Intent intent=new Intent(this,Design.class);
        intent.putExtra("data",search);
        startActivity(intent);

    }


}
