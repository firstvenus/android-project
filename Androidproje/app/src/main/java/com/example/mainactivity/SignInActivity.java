package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private EditText txt_email;
    private EditText txt_password;
    private Button btn_login;
    private TextView txt_goSignUp;

    //FirebaseAuth girisyetkisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        //girisyetkisi=FirebaseAuth.getInstance();
        txt_goSignUp=(TextView)findViewById(R.id.txt_goSignUp);

        txt_goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        final DatabaseConnection db = new DatabaseConnection(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pdGiris=new ProgressDialog(SignInActivity.this);
                pdGiris.setMessage("Logging In..");
                pdGiris.show();
                String str_email = "";
                String str_password = "";
                if(txt_email.getText() != null && !txt_email.getText().toString().isEmpty() && txt_password.getText() != null && !txt_password.getText().toString().isEmpty()){
                    str_email=txt_email.getText().toString();
                    str_password=txt_password.getText().toString();
                }

                // Log.d("email","e"+str_email+"p"+str_password);
                if(str_email.trim().isEmpty() || str_password.trim().isEmpty()){
                    Toast.makeText(SignInActivity.this,"Please fill in all fields.",Toast.LENGTH_LONG).show();
                }
                else{
                    //giris yapma kodları

                    if(db.authenticateUser(str_email,str_password)){
                        Toast.makeText(SignInActivity.this,"Login correct.",Toast.LENGTH_LONG).show();
                        System.err.println(db.authenticateUser(str_email,str_password));
                    }

                    /*girisyetkisi.signInWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        DatabaseReference yolGiris= FirebaseDatabase.getInstance().getReference().child("users").child(girisyetkisi.getCurrentUser().getUid());

                                        yolGiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                pdGiris.dismiss();
                                                Intent intent=new Intent(SignInActivity.this,Category.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();//geri tuşuna bastığında aynı yere dönmesin diye
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                pdGiris.dismiss();

                                            }
                                        });
                                    }
                                    else{
                                        pdGiris.dismiss();
                                        Toast.makeText(SignInActivity.this,"Login failed.",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });*/
                }

            }
        });



    }
}
