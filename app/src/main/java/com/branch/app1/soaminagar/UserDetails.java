package com.branch.app1.soaminagar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {

    EditText firstName, lastName, email, branchSerial;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);
        branchSerial = findViewById(R.id.branchSerial);
        saveBtn = findViewById(R.id.saveBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference docRef = fStore.collection("users").document(userID);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !branchSerial.getText().toString().isEmpty()){
                    String first = firstName.getText().toString();
                    String last = lastName.getText().toString();
                    String userEmail = email.getText().toString();
                    String branch = branchSerial.getText().toString();

                    Map<String, Object> user = new HashMap<>();
                    user.put("firstName", first);
                    user.put("lastName", last);
                    user.put("emailAddress", userEmail);
                    user.put("branchSl", branch);

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(UserDetails.this, "Data Is Not Inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(UserDetails.this, "All Fields are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}