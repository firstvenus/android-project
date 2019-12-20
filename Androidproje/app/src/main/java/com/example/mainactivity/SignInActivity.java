package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.SystemUpdatePolicy;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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


public class SignInActivity extends AppCompatActivity {
    private EditText txt_email,txt_password;
    private Button btn_login;
    private TextView txt_goSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_goSignUp = (TextView) findViewById(R.id.txt_goSignUp);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

        txt_goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });


    }

    private void CreateAccount() {
        String user_email= txt_email.getText().toString();
        String user_password= txt_password.getText().toString();

        if(TextUtils.isEmpty(user_email)){
            Toast.makeText(this, "Email can not be blank", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(user_password)){
            Toast.makeText(this, "Password can not be blank", Toast.LENGTH_SHORT).show();

        } else {
            Validateuserinformation(user_email,user_password);
        }
    }

    private void Validateuserinformation(final String user_email, final String user_password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(user_email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("email", user_email);
                    userdataMap.put("password", user_password);

                    RootRef.child("Users").child(user_email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "Congratulations, your account is successfully created", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Error. (try again)", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                } else {

                    Toast.makeText(SignInActivity.this, "This" + user_email + "already exists", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
