package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mainactivity.R;


public class MainActivity extends AppCompatActivity {

    private TextView txt_signin;
    private  TextView txt_signup;
  //  DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  db = new DatabaseConnection(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_signin = (TextView) findViewById(R.id.txt_signin);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
    }}

 /*       txt_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });


    }
}
*/
