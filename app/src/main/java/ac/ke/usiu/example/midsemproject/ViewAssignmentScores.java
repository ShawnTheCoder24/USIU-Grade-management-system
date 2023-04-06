package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViewAssignmentScores extends AppCompatActivity {
    public static String store;
    String dbName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignment_scores);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore fireStoreDb = FirebaseFirestore.getInstance();
        TextView checkScore1 = findViewById(R.id.viewAssignmentScore1);
        TextView checkScore2 = findViewById(R.id.viewAssignmentScore2);
        TextView checkScore3 = findViewById(R.id.viewAssignmentScore3);
        TextView checkScore4 = findViewById(R.id.viewAssignmentScore4);
        TextView checkScore5 = findViewById(R.id.viewAssignmentScore5);
        Button previous = findViewById(R.id.backButton2);
        Bundle storeName = getIntent().getExtras();

        dbName = storeName.getString(store);

        Map<String,String> assignmentData = new HashMap<String,String>();

        fireStoreDb.collection("CourseData")
                .document(dbName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot assignmentScores = task.getResult();
                        if(assignmentScores.exists())
                        {
                           assignmentData.put("Score 1", assignmentScores.get("Assignment 1").toString());
                           assignmentData.put("Score 2", assignmentScores.get("Assignment 2").toString());
                           assignmentData.put("Score 3", assignmentScores.get("Assignment 3").toString());
                           assignmentData.put("Score 4", assignmentScores.get("Assignment 4").toString());
                           assignmentData.put("Score 5", assignmentScores.get("Assignment 5").toString());
                           //Place data in a string variable
                           String assignment1 = assignmentData.get("Score 1");
                           String assignment2 = assignmentData.get("Score 2");
                           String assignment3 = assignmentData.get("Score 3");
                           String assignment4 = assignmentData.get("Score 4");
                           String assignment5 = assignmentData.get("Score 5");

                           //Set the data as the textview text if not equal to 102
                            if(assignment1.contentEquals("102"))
                            {
                                checkScore1.setText("Not assigned");
                            }
                            else
                            {
                                checkScore1.setText(assignment1);
                            }

                            if(assignment2.contentEquals("102"))
                            {
                                checkScore2.setText("Not assigned");
                            }
                            else
                            {
                                checkScore2.setText(assignment2);
                            }

                            if(assignment3.contentEquals("102"))
                            {
                                checkScore3.setText("Not assigned");
                            }
                            else
                            {
                                checkScore3.setText(assignment3);
                            }

                            if(assignment4.contentEquals("102"))
                            {
                                checkScore4.setText("Not assigned");
                            }
                            else
                            {
                                checkScore4.setText(assignment4);
                            }

                            if(assignment5.contentEquals("102"))
                            {
                                checkScore5.setText("Not assigned");
                            }
                            else
                            {
                                checkScore5.setText(assignment5);
                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error: Could not find document",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}