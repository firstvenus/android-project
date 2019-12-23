package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {
    private Button btn_register;
    private EditText txt_name, txt_surname, txt_email, txt_password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_surname = (EditText) findViewById(R.id.txt_surname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount() {
        String name = txt_name.getText().toString();
        String surname = txt_surname.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name here", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(surname)){
            Toast.makeText(this, "Please write your surname here", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email name here", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Create a password", Toast.LENGTH_SHORT).show();
        }
        else {
            ValidateEmail(name, surname, email, password);
        }


    }

    private void ValidateEmail(final String name, final String surname, final String email, final String password) {

    final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //check from database if same email appears for another user
                if((!dataSnapshot.child("User_Info").child(email).exists()))
                {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("surname",surname);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);

                    RootRef.child("User_Info").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Congratulations, your account is created", Toast.LENGTH_SHORT).show();

                                        Intent intent= new Intent(SignUpActivity.this, SignInActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Network Connection Error: PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });



                } else
                    Toast.makeText(SignUpActivity.this, "This" + email+ "already exists", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}




















