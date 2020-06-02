package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class RegistryActivity extends AppCompatActivity {
    EditText mEmailEt,mPasswordEt;
    Button mRegisterBtn;
    TextView mloginTV ;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lets Start Socializing");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mEmailEt=findViewById(R.id.emailEt);
        mPasswordEt=findViewById(R.id.passwordEt);
        mRegisterBtn=findViewById(R.id.registerbtn);
        mloginTV=findViewById(R.id.loginTV);
        mAuth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("we are creating a social life for you ...");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("OUPS! Wrong Email..");
                    mEmailEt.setFocusable(true);
                }
                else if(password.length()<6){
                    mPasswordEt.setError("waw less then 6 characters!!");
                    mPasswordEt.setFocusable(true);
                }
                else {
                    registerUser(email, password);
                }
            }
        });

        mloginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistryActivity.this,LoginActivity.class));
            }
        });
    }

    private void registerUser(String email, String password) {
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(RegistryActivity.this, "Registered...\n"+user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistryActivity.this,ProfileActivity.class));
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegistryActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
            public void onFailure(@NonNull Exception e){
                        progressDialog.dismiss();
                       Toast.makeText(RegistryActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        });

    }


    @Override
public  boolean onSupportNavigateUp(){
        onBackPressed();
        return  super.onSupportNavigateUp();

}




}
