package com.example.dartsglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ATB extends AppCompatActivity {
    private EditText num;
    private Button b1,b2;
    private String n,total;
    private int score,taken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atb);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        num = (EditText) findViewById(R.id.editATB);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

        n="1";
        total="0";

        displayScore();

        score = Integer.parseInt(num.getText().toString());

        taken=0;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taken++;
                score++;
                n = String.valueOf(score);
                displayScore();
                if(score >20){
                    Toast.makeText(ATB.this, "You Took "+taken+" to clear the board", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ATB.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taken++;
                displayScore();
            }
        });
    }

    private void displayScore() {
        num.setText(n);
    }


}