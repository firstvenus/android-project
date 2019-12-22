package com.example.proje_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private EditText txt_name;
    private EditText txt_surname;
    private EditText txt_email;
    private EditText txt_password;
    private Button btn_register;
    private TextView txt_goSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_surname = (EditText) findViewById(R.id.txt_surname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_goSignIn = (TextView) findViewById(R.id.txt_goSignIn);

        txt_goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });




    }
}
