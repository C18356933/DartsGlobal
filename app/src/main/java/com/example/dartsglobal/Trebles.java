package com.example.dartsglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Trebles extends AppCompatActivity {
    private TextView treble,score;
    private Button b1,b2,b3,b0;
    private String dS,disS;
    private int t,t2,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trebles);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        treble = findViewById(R.id.editTreble);
        score = findViewById(R.id.editScore);

        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);

        dS="1";
        disS="0";

        displaySingle();
        displayScore();

        t = Integer.parseInt(treble.getText().toString());
        t2 = Integer.parseInt(score.getText().toString());
        total =0;
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2 = 0;
                total = t2 + total;
                disS = String.valueOf(total);
                t = t + 1;
                dS = String.valueOf(t);
                displaySingle();
                displayScore();
                if(t >20){
                    Toast.makeText(Trebles.this, "Your Score Was "+disS, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Trebles.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2 = 1 * (t*3);
                total = t2 + total;
                disS = String.valueOf(total);
                t = t + 1;
                dS = String.valueOf(t);
                displaySingle();
                displayScore();
                if(t >20){
                    Toast.makeText(Trebles.this, "Your Score Was "+disS, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Trebles.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2 = 2 * (t*3);
                total = t2 + total;
                disS = String.valueOf(total);
                t = t + 1;
                dS = String.valueOf(t);
                displaySingle();
                displayScore();
                if(t >20){
                    Toast.makeText(Trebles.this, "Your Score Was "+disS, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Trebles.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2 = 3 * (t*3);
                total = t2 + total;
                disS = String.valueOf(total);
                t = t + 1;
                dS = String.valueOf(t);
                displaySingle();
                displayScore();
                if(t >20){
                    Toast.makeText(Trebles.this, "Your Score Was "+disS, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Trebles.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });


    }

    private void displaySingle() {
        treble.setText(dS);
    }
    private void displayScore() {
        score.setText(disS);
    }
}