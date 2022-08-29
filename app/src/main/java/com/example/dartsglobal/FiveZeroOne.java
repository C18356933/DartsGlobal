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

public class FiveZeroOne extends AppCompatActivity {
    private TextView player1,player2,textView1,textview2;
    private String remaining1,remaining2;
    private EditText CS;
    private String current;
    private Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,enter,cl;
    public int game,length,p1CS,p2CS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_zero_one);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        player1 = (TextView) findViewById(R.id.editP1);
        player2= (TextView)  findViewById(R.id.editP2);
        textView1 = (TextView) findViewById(R.id.textView1);
        textview2= (TextView)  findViewById(R.id.textView2);
        CS= (EditText)  findViewById(R.id.editCS);

        remaining1 = "501";
        remaining2 = "501";

        p1CS = 0;
        p2CS = 0;

        current = "";

        game = 0;
        length = 0;


        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);

        enter = (Button)  findViewById(R.id.enter);
        cl = (Button)  findViewById(R.id.clear);
        textView1.setBackgroundResource(R.drawable.border);
        textview2.setBackgroundResource(R.drawable.noborder);

        displayP1();
        displayP2();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "1";
                displayCurrent();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "2";
                displayCurrent();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "3";
                displayCurrent();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "4";
                displayCurrent();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "5";
                displayCurrent();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "6";
                displayCurrent();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "7";
                displayCurrent();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "8";
                displayCurrent();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "9";
                displayCurrent();

            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current + "0";
                displayCurrent();

            }
        });
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                displayCurrent();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                length = Integer.parseInt(CS.getText().toString());
                if (game == 0) {
                    p1CS = Integer.parseInt(player1.getText().toString());
                    if (length > 180) {
                        Toast.makeText(FiveZeroOne.this, "Can't Score More Than 180", Toast.LENGTH_SHORT).show();
                        clear();
                        displayCurrent();
                        if (length > p1CS) {
                            Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                        }
                    } else {
                        if (length > p1CS) {
                            Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                            displayP1();
                            game = 1;
                            textview2.setBackgroundResource(R.drawable.border);
                            textView1.setBackgroundResource(R.drawable.noborder);

                        }else {
                            p1CS = p1CS - length;
                            remaining1 = String.valueOf(p1CS);
                            if (p1CS==1){
                                Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                                p1CS = p1CS + length;
                                remaining1 = String.valueOf(p1CS);
                                clear();
                                displayCurrent();
                                displayP1();
                                game = 1;
                                textview2.setBackgroundResource(R.drawable.border);
                                textView1.setBackgroundResource(R.drawable.noborder);
                            }if (p1CS > 0) {
                                clear();
                                displayCurrent();
                                displayP1();
                                game = 1;
                                textview2.setBackgroundResource(R.drawable.border);
                                textView1.setBackgroundResource(R.drawable.noborder);
                            } else {
                                Toast.makeText(FiveZeroOne.this, "Player 1 Wins", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FiveZeroOne.this, Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                    }

                }else {
                    p2CS = Integer.parseInt(player2.getText().toString());
                    if (length > 180) {
                        Toast.makeText(FiveZeroOne.this, "Can't Score More Than 180", Toast.LENGTH_SHORT).show();
                        clear();
                        displayCurrent();
                        if (length > p2CS) {
                            Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                        }
                    } else {
                        if (length > p2CS) {
                            Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                            displayP2();
                            game = 0;
                            textView1.setBackgroundResource(R.drawable.border);
                            textview2.setBackgroundResource(R.drawable.noborder);
                        } else {
                            p2CS = p2CS - length;
                            remaining2 = String.valueOf(p2CS);
                            if (p2CS==1){
                                Toast.makeText(FiveZeroOne.this, "Bust Score", Toast.LENGTH_SHORT).show();
                                p2CS = p2CS + length;
                                remaining2 = String.valueOf(p2CS);
                                clear();
                                displayCurrent();
                                displayP2();
                                game = 0;
                                textView1.setBackgroundResource(R.drawable.border);
                                textview2.setBackgroundResource(R.drawable.noborder);

                            }
                            if (p2CS > 0) {
                                clear();
                                displayCurrent();
                                displayP2();
                                game = 0;
                                textView1.setBackgroundResource(R.drawable.border);
                                textview2.setBackgroundResource(R.drawable.noborder);
                            } else {
                                Toast.makeText(FiveZeroOne.this, "Player 2 Wins", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FiveZeroOne.this, Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                    }
                }
            }
        });
    }

    private void clear() {
        current="";
    }

    private void displayCurrent() {
        CS.setText(current);
    }
    private void displayP1() {
        player1.setText(remaining1);
    }
    private void displayP2() {
        player2.setText(remaining2);
    }


}