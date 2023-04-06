package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class examProjectUpdate extends AppCompatActivity {
    public static String dbName;
    String key1 = "Mid Exam";
    String key2 = "End Exam";
    String key3 = "Project 1";
    String key4 = "Project 2";

    int exam1Score;
    int exam2Score;
    int project1Score;
    int project2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_project_update);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore examProjectUpdate = FirebaseFirestore.getInstance();
        Bundle sentData = getIntent().getExtras();
        EditText midExam = findViewById(R.id.exam1);
        EditText endExam = findViewById(R.id.exam2);
        EditText firstProject = findViewById(R.id.project1);
        EditText secondProject = findViewById(R.id.project2);
        Button sendUpdatedData = findViewById(R.id.updateStudentExamScores);
        Button back = findViewById(R.id.backToPrevious);

        sendUpdatedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myDbName = sentData.getString(dbName);
                String examOne = midExam.getText().toString().trim();
                String examTwo = endExam.getText().toString().trim();
                String projectOne = firstProject.getText().toString().trim();
                String projectTwo = secondProject.getText().toString().trim();

                int exam1Update;
                if(examOne.isEmpty())
                {
                    exam1Update = 0;
                    exam1Score = exam1Update + 102;
                }
                else
                {
                    exam1Update=Integer.parseInt(examOne);
                    exam1Score = exam1Update;
                }
                if(exam1Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for the Mid Exam is incorrect", Toast.LENGTH_LONG).show();
                    midExam.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int exam2Update;
                if(examTwo.isEmpty())
                {
                    exam2Update = 0;
                    exam2Score = exam2Update + 102;
                }
                else
                {
                    exam2Update=Integer.parseInt(examTwo);
                    exam2Score = exam2Update;
                }
                if(exam2Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for the End Exam is incorrect", Toast.LENGTH_LONG).show();
                    endExam.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int project1Update;
                if(projectOne.isEmpty())
                {
                    project1Update = 0;
                    project1Score = project1Update + 102;
                }
                else
                {
                    project1Update=Integer.parseInt(projectOne);
                    project1Score = project1Update;
                }
                if(project1Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for project 1 is incorrect", Toast.LENGTH_LONG).show();
                    firstProject.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int project2Update;
                if(projectTwo.isEmpty())
                {
                    project2Update = 0;
                    project2Score = project2Update + 102;
                }
                else
                {
                    project2Update=Integer.parseInt(projectTwo);
                    project2Score = project2Update;
                }
                if(project2Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for project 2 is incorrect", Toast.LENGTH_LONG).show();
                    secondProject.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                Map<String, Object>updateExamProject = new HashMap<>();
                updateExamProject.put(key1,exam1Score);
                updateExamProject.put(key2,exam2Score);
                updateExamProject.put(key3,project1Score);
                updateExamProject.put(key4,project2Score);

                examProjectUpdate.collection("CourseData")
                        .document(myDbName)
                        .update(updateExamProject)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Exam and Project scores have been updated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error: No scores have been updated", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });


    }
}