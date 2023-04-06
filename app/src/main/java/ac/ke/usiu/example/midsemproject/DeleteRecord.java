package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DeleteRecord extends AppCompatActivity {
    String checkIfRecordExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore myDatabase = FirebaseFirestore.getInstance();

        //FirebaseAuth verify = FirebaseAuth.getInstance();
        // - Will be used during validation of role (as shown is bookroom)

        //Hiding the name bar on the app
        ActionBar appName = getSupportActionBar();
        appName.hide();

        EditText studentId = findViewById(R.id.studentIDArea);
        EditText courseId = findViewById(R.id.courseIDArea);
        courseId.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Button clear = findViewById(R.id.deletionButton);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentIdNumber = studentId.getText().toString().trim();
                String courseIdNumber = courseId.getText().toString().trim();
                String savedField = studentIdNumber+courseIdNumber;

                if(studentIdNumber.isEmpty())
                {
                    studentId.setError("Please insert the student's ID number");
                    return;
                }
                else if(studentIdNumber.length()!=6)
                {
                    studentId.setError("The student ID must have 6 digits only");
                    return;
                }
                else if(courseIdNumber.isEmpty())
                {
                    courseId.setError("Please insert the course ID");
                    return;
                }
                else if(courseIdNumber.length()!=7)
                {
                    courseId.setError("The course ID must have 7 characters e.g. SET9900");
                    return;
                }
                else
                {
                    //Check if the database document exists
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(savedField)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                myDatabase.collection("CourseData")
                                                                        .document(savedField)
                                                                        .get()
                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                if(task.isSuccessful())
                                                                                {
                                                                                    DocumentSnapshot saver = task.getResult();
                                                                                    if(saver.exists())
                                                                                    {
                                                                                        //if the database document exists, delete the record
                                                                                        myDatabase.collection("CourseData")
                                                                                                .document(savedField)
                                                                                                .delete()
                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Void unused) {
                                                                                                        Toast.makeText(getApplicationContext(), "The record has been deleted", Toast.LENGTH_LONG).show();

                                                                                                    }
                                                                                                })
                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_LONG).show();
                                                                                                    }
                                                                                                });

                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        Toast.makeText(getApplicationContext(), "The record for the given student and course IDs does not exist", Toast.LENGTH_LONG).show();
                                                                                    }
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot delete records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                courseId.setError("You are not allowed to delete records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            courseId.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }

            }
        });

    }
}