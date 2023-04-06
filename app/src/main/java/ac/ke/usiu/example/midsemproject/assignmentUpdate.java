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

public class assignmentUpdate extends AppCompatActivity {
    public static String database;
    String key1 = "Assignment 1";
    String key2 = "Assignment 2";
    String key3 = "Assignment 3";
    String key4 = "Assignment 4";
    String key5 = "Assignment 5";

    int assignment1Score;
    int assignment2Score;
    int assignment3Score;
    int assignment4Score;
    int assignment5Score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_update);
        //Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore assignmentScore = FirebaseFirestore.getInstance();

        EditText firstAssignment = findViewById(R.id.assignment1);
        EditText secondAssignment = findViewById(R.id.assignment2);
        EditText thirdAssignment = findViewById(R.id.assignment3);
        EditText fourthAssignment = findViewById(R.id.assignment4);
        EditText fifthAssignment = findViewById(R.id.assignment5);
        Bundle name = getIntent().getExtras();
        Button updateAssignment = findViewById(R.id.updateStudentAssignments);
        Button previousPage = findViewById(R.id.returnButton);

        updateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myDatabase = name.getString(database);
                String assignmentOne = firstAssignment.getText().toString().trim();
                String assignmentTwo = secondAssignment.getText().toString().trim();
                String assignmentThree = thirdAssignment.getText().toString().trim();
                String assignmentFour = fourthAssignment.getText().toString().trim();
                String assignmentFive = fifthAssignment.getText().toString().trim();

                int assignment1Update;
                if(assignmentOne.isEmpty())
                {
                    assignment1Update=0;
                    assignment1Score = assignment1Update + 102;
                }
                else
                {
                    assignment1Update = Integer.parseInt(assignmentOne);
                    assignment1Score = assignment1Update;
                }
                if(assignment1Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Assignment 1 is incorrect", Toast.LENGTH_LONG).show();
                    firstAssignment.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int assignment2Update;
                if(assignmentTwo.isEmpty())
                {
                    assignment2Update=0;
                    assignment2Score = assignment2Update + 102;
                }
                else
                {
                    assignment2Update = Integer.parseInt(assignmentTwo);
                    assignment2Score = assignment2Update;
                }
                if(assignment2Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Assignment 2 is incorrect", Toast.LENGTH_LONG).show();
                    secondAssignment.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int assignment3Update;
                if(assignmentThree.isEmpty())
                {
                    assignment3Update=0;
                    assignment3Score = assignment3Update + 102;
                }
                else
                {
                    assignment3Update = Integer.parseInt(assignmentThree);
                    assignment3Score = assignment3Update;
                }
                if(assignment3Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Assignment 3 is incorrect", Toast.LENGTH_LONG).show();
                    thirdAssignment.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int assignment4Update;
                if(assignmentFour.isEmpty())
                {
                    assignment4Update=0;
                    assignment4Score = assignment4Update + 102;
                }
                else
                {
                    assignment4Update = Integer.parseInt(assignmentFour);
                    assignment4Score = assignment4Update;
                }
                if(assignment4Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Assignment 4 is incorrect", Toast.LENGTH_LONG).show();
                    fourthAssignment.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int assignment5Update;
                if(assignmentFive.isEmpty())
                {
                    assignment5Update=0;
                    assignment5Score = assignment5Update + 102;
                }
                else
                {
                    assignment5Update = Integer.parseInt(assignmentFive);
                    assignment5Score = assignment5Update;
                }
                if(assignment5Update>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Assignment 5 is incorrect", Toast.LENGTH_LONG).show();
                    fifthAssignment.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                Map<String,Object> updateAssignmentScore = new HashMap<>();
                updateAssignmentScore.put(key1,assignment1Score);
                updateAssignmentScore.put(key2,assignment2Score);
                updateAssignmentScore.put(key3,assignment3Score);
                updateAssignmentScore.put(key4,assignment4Score);
                updateAssignmentScore.put(key5,assignment5Score);

                assignmentScore.collection("CourseData")
                        .document(myDatabase)
                        .update(updateAssignmentScore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Assignment scores have been updated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error: Assignment scores have not been updated", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}