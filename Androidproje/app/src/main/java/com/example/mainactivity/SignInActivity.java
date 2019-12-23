package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;


public class SignInActivity extends AppCompatActivity {


private  EditText txt_password,txt_email;
private Button btn_login;
private String parentDatabaseName ="User_Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

    btn_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         loginUser();   
        }
    });

    }

    private void loginUser() {
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

         if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email name here", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password here", Toast.LENGTH_SHORT).show();
        }
        else
         {
             AllowAccessToAccount(email,password);
         }

    }

    private void AllowAccessToAccount(final String email, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDatabaseName).child(email).exists()){

                    User userData = dataSnapshot.child(parentDatabaseName).child(email).getValue(User.class);
                    if(userData.getEmail().equals(email)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(SignInActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(SignInActivity.this, "Account with this email:" + email + "do not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

