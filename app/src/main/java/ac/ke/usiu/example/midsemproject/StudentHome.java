package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentHome extends AppCompatActivity {

    String studentIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        FirebaseAuth identityCheck = FirebaseAuth.getInstance();
        FirebaseFirestore dataStore = FirebaseFirestore.getInstance();
        EditText course = findViewById(R.id.courseID);
        course.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Button viewGradeData = findViewById(R.id.viewGrade);

        viewGradeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseIdNo = course.getText().toString().trim();

                Map<String,String> studentNumber = new HashMap<>();
                dataStore.collection("AppUsers")
                        .document(identityCheck.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot myDocument = task.getResult();
                                if(myDocument.exists())
                                {
                                    studentNumber.put("ID",myDocument.get("ID").toString());
                                    String IDNumber = studentNumber.get("ID");
                                    String documentName = IDNumber + courseIdNo;

                                    dataStore.collection("CourseData")
                                            .document(documentName)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if(documentSnapshot.exists())
                                                    {
                                                        Intent send = new Intent(getApplicationContext(),OverallGrades.class);
                                                        send.putExtra(OverallGrades.dbName,documentName);
                                                        startActivity(send);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Your record for this course was not found in the scores database",Toast.LENGTH_LONG).show();
                                                        course.setError("Course ID may be incorrect");
                                                    }
                                                }
                                            });
                                }
                            }
                        });

            }
        });


    }
}