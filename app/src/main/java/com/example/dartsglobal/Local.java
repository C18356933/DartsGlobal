package com.example.dartsglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Local extends AppCompatActivity {
    Button match;
    Button singles;
    Button doubles;
    Button treble;
    Button atb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        match=findViewById(R.id.buttonMatch);
        singles=findViewById(R.id.buttonSingles);
        doubles=findViewById(R.id.buttonDoubles);
        treble=findViewById(R.id.buttonTrebles);
        atb=findViewById(R.id.buttonAroundtheboard);

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Local.this,Match.class));
            }
        });
        singles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Local.this,Singles.class));
            }
        });
        doubles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Local.this,Doubles.class));
            }
        });
        treble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Local.this,Trebles.class));
            }
        });

        atb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Local.this,ATB.class));
            }
        });

    }
}