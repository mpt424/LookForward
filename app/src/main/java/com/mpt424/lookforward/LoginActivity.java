package com.mpt424.lookforward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivity";
    /*gui fields*/
    TextInputEditText in_user;
    TextInputEditText in_pwd;
    /*authenticator*/
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //askPermissions();
        in_user = (TextInputEditText)(findViewById(R.id.in_user));
        in_pwd = (TextInputEditText)(findViewById(R.id.in_pwd));

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToMain();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * If needed , ask the permissions for the app
     * @return True if permissions are given, else False
     */
    private boolean askPermissions() {
        //TODO: implement
        return true;
    }


    /**
     * Sign in button press handle
     * @param v
     */
    protected void onSignIn(View v){
        String user = in_user.getText().toString();
        String pwd = in_pwd.getText().toString();

        mAuth.signInWithEmailAndPassword(user, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Authentication success!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });


    }

    /**
     * Register button press handle
     * @param v
     */
    protected void onRegister(View v){
        String user = in_user.getText().toString();
        String pwd = in_pwd.getText().toString();

        //TODO:go to register activity and add other fields
        mAuth.createUserWithEmailAndPassword(user, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


        /*open register activity*/
        Intent reg_intent = new Intent(LoginActivity.this,RegisterActivity.class);
        reg_intent.putExtra("user",user);
        reg_intent.putExtra("pwd","pwd");
        startActivity(reg_intent);
    }
}
