package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String IDKey = "ID";
    String NameKey = "Name";
    String PasswordKey = "Password";
    String RoleKey = "Role";
    String EmailKey = "Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //Hiding the action bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        FirebaseFirestore userDatabase = FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();

        EditText userIDno = findViewById(R.id.userID);
        EditText userName = findViewById(R.id.newUser);
        EditText password = findViewById(R.id.newPassword);
        EditText role = findViewById(R.id.newRole);
        EditText email = findViewById(R.id.newEmail);
        Button addUser = findViewById(R.id.UserCreation);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIDno.getText().toString().trim();
                String name = userName.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userRole = role.getText().toString().trim();
                String userEmail = email.getText().toString().trim();

                if(userId.isEmpty())
                {
                    userIDno.setError("Please insert an ID for the user");
                    return;
                }
                else if(userId.length()!=6)
                {
                    userIDno.setError("User Ids must have 6 numbers");
                    return;
                }
                else if(name.isEmpty())
                {
                    userName.setError("Please insert the user's name");
                    return;
                }
                else if(userPassword.isEmpty())
                {
                    password.setError("Please insert a password for the user");
                    return;
                }
                else if(userPassword.length()<6)
                {
                    password.setError("The password must have 6 or more characters");
                    return;
                }
                else if(userRole.isEmpty())
                {
                    role.setError("Please insert the user's role");
                    return;
                }
                else if(userEmail.isEmpty())
                {
                    email.setError("Please insert the user's email");
                    return;
                }
                else if(! Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
                {
                    email.setError("The email format used is not correct");
                    return;
                }

                else
                {
                    Map<String,String> userRecord = new HashMap<>();
                    userRecord.put(IDKey,userId);
                    userRecord.put(NameKey,name);
                    userRecord.put(PasswordKey,userPassword);
                    userRecord.put(RoleKey,userRole);
                    userRecord.put(EmailKey,userEmail);

                    firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"User can now login",Toast.LENGTH_LONG).show();
                                userDatabase.collection("AppUsers")
                                        .document(userEmail)
                                        .set(userRecord)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(),"User has been created",Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Error: Unable to create user",Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}