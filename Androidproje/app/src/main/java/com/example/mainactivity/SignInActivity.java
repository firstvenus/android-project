package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;
import com.rey.material.widget.CheckBox;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
// import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Model.User;
import com.example.mainactivity.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {


private EditText txt_password,txt_email;
private Button btn_login;
private String parentDatabaseName ="User_Info";
private CheckBox rememberMeBox;
private TextView admin_panel, not_admin_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        rememberMeBox = (CheckBox) findViewById(R.id.remember_me_chkb);
        admin_panel = (TextView) findViewById(R.id.admin_panel);
        not_admin_panel = (TextView) findViewById(R.id.not_admin_panel);
        Paper.init(this);

    btn_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         loginUser();   
        }
    });

    admin_panel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btn_login.setText("Admin Login");
            admin_panel.setVisibility(View.INVISIBLE);
            not_admin_panel.setVisibility(View.VISIBLE);
            parentDatabaseName="Admin";

        }
    });

    not_admin_panel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btn_login.setText("Login");
            admin_panel.setVisibility(View.VISIBLE);
            not_admin_panel.setVisibility(View.INVISIBLE);
            parentDatabaseName="User_Info";
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


        if(rememberMeBox.isChecked()){

            Paper.book().write(Prevalent.UserEmailKey,email);
            Paper.book().write(Prevalent.UserPasswordKey,password);

        }




        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDatabaseName).child(email).exists()){

                    User userData = dataSnapshot.child(parentDatabaseName).child(email).getValue(User.class);
                    if(userData.getEmail().equals(email)){

                        if(userData.getPassword().equals(password)){
                            if(parentDatabaseName.equals("Admin")){
                                Toast.makeText(SignInActivity.this, "Logged in successfully as an admin", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignInActivity.this, AdminAddNewProductActivity.class);
                                startActivity(intent);
                            } else if (parentDatabaseName.equals("User_Info")){
                                Toast.makeText(SignInActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
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

