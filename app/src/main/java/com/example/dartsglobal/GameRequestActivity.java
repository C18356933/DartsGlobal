package com.example.dartsglobal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dartsglobal.adapter.RequestAdapter;
import com.example.dartsglobal.databinding.ActivityGameRequestBinding;
import com.example.dartsglobal.models.Requests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class GameRequestActivity extends AppCompatActivity implements RequestAdapter.OnItemClickListener {
    private ActivityGameRequestBinding binding;
    private DatabaseReference requestRef, gameRef;
    private FirebaseAuth mAuth;

    ArrayList<Requests> requestsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        requestRef = FirebaseDatabase.getInstance().getReference().child("requests");
        gameRef = FirebaseDatabase.getInstance().getReference().child("game");
        mAuth = FirebaseAuth.getInstance();
        binding.playBtn.setOnClickListener(view -> addNameDialog());


    }

    @Override
    protected void onStart() {
        super.onStart();
        getRequests();
    }

    private void getRequests() {
        requestsArrayList.clear();
        binding.loading.setVisibility(View.VISIBLE);
        requestRef.orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Requests requests = ds.getValue(Requests.class);
                        if (!requests.getPlayerOneId().equals(mAuth.getUid())) {
                            requestsArrayList.add(requests);
                        }
                    }
                    binding.loading.setVisibility(View.GONE);
                    RequestAdapter requestAdapter = new RequestAdapter(GameRequestActivity.this, requestsArrayList);
                    binding.gameRequestRv.setLayoutManager(new LinearLayoutManager(GameRequestActivity.this));
                    binding.gameRequestRv.setAdapter(requestAdapter);
                    requestAdapter.setOnItemClickListener(GameRequestActivity.this);

                }else {
                    binding.loading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.loading.setVisibility(View.VISIBLE);
                Toast.makeText(GameRequestActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNameDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_add_name_dialog);

        dialog.setCancelable(true);
        EditText nameEt = dialog.findViewById(R.id.name_et);
        CardView pickNameCard = dialog.findViewById(R.id.pick_name_card);


        RelativeLayout header = dialog.findViewById(R.id.header);





        pickNameCard.setOnClickListener(v -> {

            if (!nameEt.getText().toString().isEmpty()){
                addRequest(nameEt.getText().toString(), dialog);
            }else {
                nameEt.setError("Please enter name");
            }



        });


        dialog.show();

    }


    private void addRequest(String name,Dialog dialog) {
        String key = requestRef.push().getKey();
        Map map = new HashMap();
        map.put("playerOneId", mAuth.getUid());
        map.put("status", "pending");
        map.put("requestId", key);
        map.put("playerOneName", name);

        requestRef.child(key)
                .setValue(map)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        dialog.dismiss();
                        Toast.makeText(this, "Game Play Request Send Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GameRequestActivity.this, Online.class);
                        intent.putExtra("playerName", name);
                        intent.putExtra("gameId", key);
                        startActivity(intent);
                    }
                }).addOnFailureListener(e -> Toast.makeText(GameRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());



    }

    @Override
    public void onPlayClick(Requests requests) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_add_name_dialog);

        dialog.setCancelable(true);
        EditText nameEt = dialog.findViewById(R.id.name_et);
        CardView pickNameCard = dialog.findViewById(R.id.pick_name_card);


        RelativeLayout header = dialog.findViewById(R.id.header);



        pickNameCard.setOnClickListener(v -> {

            if (!nameEt.getText().toString().isEmpty()){
                startGame(nameEt.getText().toString(), dialog,requests);
            }else {
                nameEt.setError("Please enter name");
            }



        });


        dialog.show();
    }

    private void startGame(String name, Dialog dialog, Requests request) {


        Map playerOneMap = new HashMap();
        playerOneMap.put("playerOneId", request.getPlayerOneId());
        playerOneMap.put("gameId", request.getRequestId());
        playerOneMap.put("playerOneScore", 501);
        playerOneMap.put("playerOneName", request.getPlayerOneName());

        Map playerTwoMap = new HashMap();
        playerTwoMap.put("playerTwoId", mAuth.getUid());
        playerTwoMap.put("gameId", request.getRequestId());
        playerTwoMap.put("playerTwoScore", 501);
        playerTwoMap.put("playerTwoName", name);

        Map map = new HashMap();
        map.put("status", "accepted");
        map.put("playerTwoId", mAuth.getUid());
        map.put("playerTwoName", name);
        map.put("playerOneScore", 501);
        map.put("playerTwoScore", 501);
        map.put("turn", request.getPlayerOneId());
        requestRef.child(request.getRequestId())
                .updateChildren(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        gameRef.child(request.getRequestId()).child(request.getPlayerOneId())
                                .setValue(playerOneMap).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        gameRef.child(request.getRequestId()).child(mAuth.getUid()).setValue(playerTwoMap).addOnCompleteListener(task11 -> {
                                            Intent intent = new Intent(GameRequestActivity.this, Online.class);
                                            intent.putExtra("playerName2", name);
                                            intent.putExtra("gameId", request.getRequestId());
                                            startActivity(intent);
                                        });
                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(e -> dialog.dismiss());
                    }
                });





    }
}