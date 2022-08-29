package com.example.dartsglobal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Online extends AppCompatActivity {
    private TextView player1, player2;
    private TextView player1TV, player2TV;
    private String remaining1, remaining2;
    private EditText CS;
    private String current;
    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, enter, cl;
    public int game, length, p1CS, p2CS;

    //player unique ID
    private String playerUniqueID = "0";

    //firebase darabase refernce from URL
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://darts-global-d6a5a-default-rtdb.firebaseio.com/");

    //true when opponent found
    private boolean opponentFound = false;

    //unique ID of opponent
    private String opponentUniqueID = "O";

    //value must be matching or waiting. when a user creates new connection he is waiting for someone to join  then the value will be waiting
    private String status = "matching";

    //playerTurn
    private String playerTurn = "";

    private String connectionID = "";

    ValueEventListener turnsEventListener, wonEventListener;
    private String gameId;
    private DatabaseReference gameRef, requestRef;
    private FirebaseAuth mAuth;
    private String playerOneId = "";
    private String playerTwoId = "";
    private String score = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //get player name


        player1 = findViewById(R.id.textView1);
        player2 = findViewById(R.id.textView2);

        player1TV = findViewById(R.id.editP1);
        player2TV = findViewById(R.id.editP2);
        CS = (EditText) findViewById(R.id.editCS);

        mAuth = FirebaseAuth.getInstance();
        playerUniqueID = mAuth.getUid();
        gameId = getIntent().getStringExtra("gameId");
        gameRef = FirebaseDatabase.getInstance().getReference().child("game");
        requestRef = FirebaseDatabase.getInstance().getReference().child("requests").child(getIntent().getStringExtra("gameId"));

//waiting for opponent to join
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Waiting for Oppenent");
        progressDialog.show();

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChild("playerOneId")) {
                        playerOneId = Objects.requireNonNull(snapshot.child("playerOneId").getValue()).toString();
                    }
                    if (snapshot.hasChild("playerTwoId")) {
                        playerTwoId = Objects.requireNonNull(snapshot.child("playerTwoId").getValue()).toString();
                    }
                    if (snapshot.hasChild("playerOneName")) {
                        player1.setText(Objects.requireNonNull(snapshot.child("playerOneName").getValue()).toString());
                    }
                    if (snapshot.hasChild("playerTwoName")) {
                        progressDialog.dismiss();
                        player2.setText(Objects.requireNonNull(snapshot.child("playerTwoName").getValue()).toString());
                    }
                    if (snapshot.hasChild("turn")) {
                        playerTurn = Objects.requireNonNull(snapshot.child("turn").getValue()).toString();

                        gameRef.child(gameId).child(playerTurn)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot s) {
                                        if (s.exists()) {

                                            if (s.hasChild("playerOneId")) {
                                                player1.setBackgroundResource(R.drawable.border);
                                                player2.setBackgroundResource(R.drawable.noborder);

                                            }

                                            if (s.hasChild("playerTwoId")) {
                                                player2.setBackgroundResource(R.drawable.border);
                                                player1.setBackgroundResource(R.drawable.noborder);

                                            }

                                            if (snapshot.hasChild("playerOneScore")) {
                                                player1TV.setText(Objects.requireNonNull(snapshot.child("playerOneScore").getValue()).toString());
//                                                if (Integer.parseInt(snapshot.child("playerOneScore").getValue().toString()) == 1) {
////                                                    Toast.makeText(Online.this, snapshot.child("playerTwoName").getValue().toString()+ " wins", Toast.LENGTH_SHORT).show();
////                                                    Intent intent = new Intent(Online.this, Home.class);
////                                                    startActivity(intent);
////                                                    finishAffinity();
//                                                }
                                                score = Objects.requireNonNull(snapshot.child("playerOneScore").getValue()).toString();

                                                if (Integer.parseInt(snapshot.child("playerTwoScore").getValue().toString()) == 0) {
                                                    Toast.makeText(Online.this, snapshot.child("playerTwoName").getValue().toString() + " wins", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Online.this, Home.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }
                                            }
                                            if (snapshot.hasChild("playerTwoScore")) {
                                                player2TV.setText(Objects.requireNonNull(snapshot.child("playerTwoScore").getValue()).toString());
                                                score = Objects.requireNonNull(snapshot.child("playerTwoScore").getValue()).toString();

//                                                if (Integer.parseInt(snapshot.child("playerTwoScore").getValue().toString()) == 1) {
////                                                    Toast.makeText(Online.this, snapshot.child("playerOneName").getValue().toString()+ " wins", Toast.LENGTH_SHORT).show();
////                                                    Intent intent = new Intent(Online.this, Home.class);
////                                                    startActivity(intent);
////                                                    finishAffinity();
//                                                }
                                                if (Integer.parseInt(snapshot.child("playerTwoScore").getValue().toString()) == 0) {
                                                    Toast.makeText(Online.this, snapshot.child("playerTwoName").getValue().toString() + " wins", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Online.this, Home.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                    }


                }
                Log.e("playerIDs", "playerOneID " + playerOneId + " playertwoId " + playerTwoId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        p1CS = 0;
        p2CS = 0;

        current = "";
        remaining1 = "501";
        remaining2 = "501";

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

        enter = (Button) findViewById(R.id.enter);
        cl = (Button) findViewById(R.id.clear);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "1";
                    displayCurrent();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "2";
                    displayCurrent();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "3";
                    displayCurrent();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "4";
                    displayCurrent();
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "5";
                    displayCurrent();
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "6";
                    displayCurrent();
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "7";
                    displayCurrent();
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "8";
                    displayCurrent();
                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "9";
                    displayCurrent();
                }

            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    current = current + "0";
                    displayCurrent();
                }

            }
        });
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {
                    clear();
                    displayCurrent();
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTurn.equals(playerUniqueID)) {

                    if (Integer.parseInt(CS.getText().toString()) > 180) {
                        Toast.makeText(Online.this, "Can't Score More Than 180", Toast.LENGTH_SHORT).show();
                        clear();
                        displayCurrent();

                    } else {
                        int finalAns = Integer.parseInt(score) - Integer.parseInt(CS.getText().toString());
                        Log.e("finalAns", String.valueOf(finalAns));
                        if (finalAns == 1) {
                            Toast.makeText(Online.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                            changeTurn();
                            return;
                        }
                        if (Integer.parseInt(CS.getText().toString()) > Integer.parseInt(score)) {
                            Toast.makeText(Online.this, "Bust Score", Toast.LENGTH_SHORT).show();
                            clear();
                            displayCurrent();
                            changeTurn();
                            return;

                        } else {
                            gameRef.child(gameId).child(playerTurn)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {

                                                Map map = new HashMap();
                                                if (snapshot.hasChild("playerOneScore")) {

                                                    int cur = Integer.parseInt(CS.getText().toString());
                                                    int rem = Integer.parseInt(snapshot.child("playerOneScore").getValue().toString());
                                                    rem = rem - cur;

                                                    map.put("playerOneScore", rem);
                                                    if (playerTurn.equals(playerOneId)) {
                                                        map.put("turn", playerTwoId);
                                                        Map a = new HashMap();
                                                        a.put("playerOneScore", rem);
                                                        gameRef.child(gameId).child(playerTurn).updateChildren(a);

                                                    }
                                                    if (playerTurn.equals(playerTwoId)) {
                                                        map.put("turn", playerOneId);

                                                    }
                                                }

                                                if (snapshot.hasChild("playerTwoScore")) {
                                                    int cur = Integer.parseInt(CS.getText().toString());
                                                    int rem = Integer.parseInt(Objects.requireNonNull(snapshot.child("playerTwoScore").getValue()).toString());
                                                    rem = rem - cur;
                                                    map.put("playerTwoScore", rem);
                                                    if (playerTurn.equals(playerOneId)) {
                                                        map.put("turn", playerTwoId);

                                                    }
                                                    if (playerTurn.equals(playerTwoId)) {
                                                        map.put("turn", playerOneId);
                                                        Map a = new HashMap();
                                                        a.put("playerTwoScore", rem);
                                                        gameRef.child(gameId).child(playerTurn).updateChildren(a);

                                                    }
                                                }
                                                requestRef.updateChildren(map).addOnCompleteListener(task -> {

                                                    if (task.isSuccessful()) {
                                                        CS.setText("");
                                                        clear();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                        }


                    }


                }
            }
        });


    }

    private void changeTurn() {

        gameRef.child(gameId).child(playerTurn)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Map map = new HashMap();

                            if (snapshot.hasChild("playerOneScore")) {


                                if (playerTurn.equals(playerOneId)) {
                                    map.put("turn", playerTwoId);

                                }
                                if (playerTurn.equals(playerTwoId)) {
                                    map.put("turn", playerOneId);

                                }
                            }

                            if (snapshot.hasChild("playerTwoScore")) {

                                if (playerTurn.equals(playerOneId)) {
                                    map.put("turn", playerTwoId);

                                }
                                if (playerTurn.equals(playerTwoId)) {
                                    map.put("turn", playerOneId);

                                }
                            }
                            requestRef.updateChildren(map);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void clear() {
        current = "";
    }

    private void displayCurrent() {
        CS.setText(current);
    }






}