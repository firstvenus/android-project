package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mainactivity.Model.User;
import com.example.mainactivity.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private Button btn_signin, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        Paper.init(this);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        String UserEmailKey = Paper.book().read(Prevalent.UserEmailKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserEmailKey!="" && UserPasswordKey!=null){
            if(!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserEmailKey, UserPasswordKey);
            }

        }



    }

    private void AllowAccess(final String email,final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("User_Info").child(email).exists()){

                    User userData = dataSnapshot.child("User_Info").child(email).getValue(User.class);
                    if(userData.getEmail().equals(email)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Account with this email:" + email + "do not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

