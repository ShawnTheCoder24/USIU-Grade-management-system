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

public class ViewExamProjectScores extends AppCompatActivity {
    public static String documentName;
    String myDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_project_scores);
        //Hiding the action bar
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore myData = FirebaseFirestore.getInstance();
        TextView midExamScore = findViewById(R.id.viewMidExamScore);
        TextView endExamScore = findViewById(R.id.viewEndExamScore);
        TextView project1Score = findViewById(R.id.viewProjectScore1);
        TextView project2Score = findViewById(R.id.viewProjectScore2);
        Button returnToPreviousPage = findViewById(R.id.backHomeButton);
        Bundle saveDbName = getIntent().getExtras();

        myDocument= saveDbName.getString(documentName);

        Map<String,String> documentSave = new HashMap<>();

        myData.collection("CourseData")
                .document(myDocument)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot scoreSnap = task.getResult();
                        if(scoreSnap.exists())
                        {
                            documentSave.put("midExam",scoreSnap.get("Mid Exam").toString());
                            documentSave.put("endExam",scoreSnap.get("End Exam").toString());
                            documentSave.put("project1",scoreSnap.get("Project 1").toString());
                            documentSave.put("project2",scoreSnap.get("Project 2").toString());

                            String exam1 = documentSave.get("midExam");
                            String exam2 = documentSave.get("endExam");
                            String project1 = documentSave.get("project1");
                            String project2 = documentSave.get("project2");

                            if(exam1.contentEquals("102"))
                            {
                                midExamScore.setText("Not assigned");
                            }
                            else
                            {
                                midExamScore.setText(exam1);
                            }

                            if(exam2.contentEquals("102"))
                            {
                                endExamScore.setText("Not assigned");
                            }
                            else
                            {
                                endExamScore.setText(exam2);
                            }

                            if(project1.contentEquals("102"))
                            {
                                project1Score.setText("Not assigned");
                            }
                            else
                            {
                                project1Score.setText(project1);
                            }

                            if(project2.contentEquals("102"))
                            {
                                project2Score.setText("Not assigned");
                            }
                            else
                            {
                                project2Score.setText(project2);
                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error: The document could not be found",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        returnToPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}