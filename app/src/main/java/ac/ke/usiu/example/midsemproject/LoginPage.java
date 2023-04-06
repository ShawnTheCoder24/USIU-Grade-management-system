package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LoginPage extends AppCompatActivity {
    FirebaseAuth verify;
    FirebaseFirestore myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        EditText loginName = findViewById(R.id.userName);
        EditText loginPassword = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        ProgressBar loader = findViewById(R.id.loadBar);
        verify = FirebaseAuth.getInstance();
        myDatabase = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = loginName.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(userName))
                {
                    loginName.setError("Please insert your user name");
                    return;
                }
                else if(TextUtils.isEmpty(password))
                {
                    loginPassword.setError("Please insert your password");
                    return;
                }
                else if(password.length()<6)
                {
                    loginPassword.setError("The password must have 6 or more characters");
                    return;
                }
                //Confirm email structure
                else if(! Patterns.EMAIL_ADDRESS.matcher(userName).matches())
                {
                    loginName.setError("The email format used is not correct");
                    return;
                }
                loader.setVisibility(View.VISIBLE);

                if(userName.contentEquals("admin@usiu.ac.ke")&& password.contentEquals("myadmin"))
                {
                    startActivity(new Intent(getApplicationContext(),AdminHome.class));
                    Toast.makeText(LoginPage.this, "Logged in as Admin", Toast.LENGTH_SHORT).show();
                    loader.setVisibility((View.GONE));
                }

                else {

                    //confirm the given details
                    verify.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "User verified", Toast.LENGTH_SHORT).show();
                                loader.setVisibility((View.GONE));

                                Map<String, String> myUsers = new HashMap<>();
                                myDatabase.collection("AppUsers")
                                        .document(verify.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot userDocument = task.getResult();

                                                if (userDocument.exists()) {
                                                    myUsers.put("Role", userDocument.get("Role").toString());
                                                    String userRole = myUsers.get("Role");
                                                    // direct user to the correct page based on role and if their credentials are correct

                                                    if (userRole.contentEquals("Lecturer")) {
                                                        startActivity(new Intent(getApplicationContext(), LecturerHome.class));
                                                    } else if (userRole.contentEquals("Student")) {
                                                        startActivity(new Intent(getApplicationContext(), StudentHome.class));
                                                    }
                                                }
                                                //if the document does not exist on Firestore
                                                else {
                                                    Log.d("Data not found", "This document could not be found");
                                                }
                                            }
                                        });
                            }
                            //If the user's credentials were invalid
                            else {
                                Toast.makeText(LoginPage.this, "Error: Invalid username or password", Toast.LENGTH_LONG).show();
                                loader.setVisibility((View.GONE));
                            }


                        }
                    });
                }

            }
        });
    }
}