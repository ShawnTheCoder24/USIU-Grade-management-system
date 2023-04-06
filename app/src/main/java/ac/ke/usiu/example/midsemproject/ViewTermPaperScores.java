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

public class ViewTermPaperScores extends AppCompatActivity {
    public static  String dataStorage;
    String storageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term_paper_scores);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore store = FirebaseFirestore.getInstance();
        TextView paper1Score = findViewById(R.id.viewPaperScore1);
        TextView paper2Score = findViewById(R.id.viewPaperScore2);
        TextView paper3Score = findViewById(R.id.viewPaperScore3);
        Button homeButton = findViewById(R.id.homeReturnButton);
        Bundle saveName = getIntent().getExtras();

        storageName = saveName.getString(dataStorage);

        Map<String,String> paperScore = new HashMap<>();

        store.collection("CourseData")
                .document(storageName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentScores = task.getResult();
                        if(documentScores.exists())
                        {
                            paperScore.put("Paper1 Score", documentScores.get("Term Paper 1").toString());
                            paperScore.put("Paper2 Score", documentScores.get("Term Paper 2").toString());
                            paperScore.put("Paper3 Score", documentScores.get("Term Paper 3").toString());

                            String termPaper1 = paperScore.get("Paper1 Score");
                            String termPaper2 = paperScore.get("Paper2 Score");
                            String termPaper3 = paperScore.get("Paper3 Score");

                            if(termPaper1.contentEquals("102"))
                            {
                                paper1Score.setText("Not assigned");
                            }
                            else
                            {
                                paper1Score.setText(termPaper1);
                            }

                            if(termPaper2.contentEquals("102"))
                            {
                                paper2Score.setText("Not assigned");
                            }
                            else
                            {
                                paper2Score.setText(termPaper2);
                            }

                            if(termPaper3.contentEquals("102"))
                            {
                                paper3Score.setText("Not assigned");
                            }
                            else
                            {
                                paper3Score.setText(termPaper3);
                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error:Document was not found",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}