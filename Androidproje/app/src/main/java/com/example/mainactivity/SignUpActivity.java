package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText txt_name;
    private EditText txt_surname;
    private EditText txt_email;
    private EditText txt_password;
    private Button btn_register;
    private TextView txt_goSignIn;

    //FirebaseAuth yetki;
    //  DatabaseReference yol;

    ProgressDialog pd;


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

        // yetki=FirebaseAuth.getInstance();

        txt_goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        final DatabaseConnection db = new DatabaseConnection(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(SignUpActivity.this);
                pd.setMessage("Please Wait..");
                pd.show();

                String str_name = txt_name.getText().toString();
                String str_surname = txt_surname.getText().toString();
                String str_email = txt_email.getText().toString();
                String str_password = txt_password.getText().toString();

                if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_surname)
                        || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    //yeni kullanıcı kaydetme kodlarını çağır
                    //register(str_name,str_surname,str_email,str_password);
                    db.insertUser(str_name, str_surname, str_email, str_password);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                }, 1000);
            }
        });
    }
}

   /* private void register(final String name, final String surname, String email, String password){
        //yeni kullanıcı kaydetme kodlarını buraya yaz

        yetki.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=yetki.getCurrentUser();
                            String userId=firebaseUser.getUid();
                            yol= FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                            HashMap<String, Object> hashMap= new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("Name",name);
                            hashMap.put("Surname",surname);

                            yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent=new Intent(SignUpActivity.this,Category.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);


                                    }

                                }
                            });
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this,"Registration failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
}
*/