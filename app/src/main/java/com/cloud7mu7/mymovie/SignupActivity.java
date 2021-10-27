package com.cloud7mu7.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText etNick, etId, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etId = findViewById(R.id.id);
        etNick = findViewById(R.id.nickname);
        etPass = findViewById(R.id.password);
    }

    public void clickBtn(View view) {

        String name, id, pass;

        name = etNick.getText().toString();
        id = etId.getText().toString();
        pass = etPass.getText().toString();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("pass", pass);
        hashMap.put("id", id);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference memberRef =firestore.collection("Member");
        Task task = memberRef.document().set(hashMap);
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(SignupActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}