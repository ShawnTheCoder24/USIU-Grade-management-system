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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ViewQuizScores extends AppCompatActivity {
    public static String scoreDocument;
    String storageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_scores);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore storeDatabase = FirebaseFirestore.getInstance();
        TextView quizScore1 = findViewById(R.id.viewQuizScore1);
        TextView quizScore2 = findViewById(R.id.viewQuizScore2);
        TextView quizScore3 = findViewById(R.id.viewQuizScore3);
        TextView quizScore4 = findViewById(R.id.viewQuizScore4);
        TextView quizScore5 = findViewById(R.id.viewQuizScore5);
        Button goBack = findViewById(R.id.homePageReturn);
        Bundle savedName = getIntent().getExtras();

        storageName = savedName.getString(scoreDocument);
        Map<String,String> quizData = new HashMap<>();

        storeDatabase.collection("CourseData")
                .document(storageName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot score = task.getResult();
                        if(score.exists())
                        {
                            quizData.put("Quiz1 Score", score.get("Quiz 1").toString());
                            quizData.put("Quiz2 Score", score.get("Quiz 2").toString());
                            quizData.put("Quiz3 Score", score.get("Quiz 3").toString());
                            quizData.put("Quiz4 Score", score.get("Quiz 4").toString());
                            quizData.put("Quiz5 Score", score.get("Quiz 5").toString());

                            String quiz1 = quizData.get("Quiz1 Score");
                            String quiz2 = quizData.get("Quiz2 Score");
                            String quiz3 = quizData.get("Quiz3 Score");
                            String quiz4 = quizData.get("Quiz4 Score");
                            String quiz5 = quizData.get("Quiz5 Score");

                            if(quiz1.contentEquals("102"))
                            {
                                quizScore1.setText("Not assigned");
                            }
                            else
                            {
                                quizScore1.setText(quiz1);
                            }

                            if(quiz2.contentEquals("102"))
                            {
                                quizScore2.setText("Not assigned");
                            }
                            else
                            {
                                quizScore2.setText(quiz2);
                            }

                            if(quiz3.contentEquals("102"))
                            {
                                quizScore3.setText("Not assigned");
                            }
                            else
                            {
                                quizScore3.setText(quiz3);
                            }

                            if(quiz4.contentEquals("102"))
                            {
                                quizScore4.setText("Not assigned");
                            }
                            else
                            {
                                quizScore4.setText(quiz4);
                            }

                            if(quiz5.contentEquals("102"))
                            {
                                quizScore5.setText("Not assigned");
                            }
                            else
                            {
                                quizScore5.setText(quiz5);
                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error: Could not find document",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}