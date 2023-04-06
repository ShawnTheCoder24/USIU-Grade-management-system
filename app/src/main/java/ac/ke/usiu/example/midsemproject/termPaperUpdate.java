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

public class termPaperUpdate extends AppCompatActivity {
    public static String data;
    String key1 = "Term Paper 1";
    String key2 = "Term Paper 2";
    String key3 = "Term Paper 3";

    int paper1Score;
    int paper2Score;
    int paper3Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_paper_update);

        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore store = FirebaseFirestore.getInstance();
        EditText firstTermPaper = findViewById(R.id.termPaper1);
        EditText secondTermPaper = findViewById(R.id.termPaper2);
        EditText thirdTermPaper = findViewById(R.id.termPaper3);
        Button updateTermPapers = findViewById(R.id.updateStudentTermPapers);
        Button goBack = findViewById(R.id.backToUpdatePage);
        Bundle save = getIntent().getExtras();

        updateTermPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documentName = save.getString(data);
                String paperOne = firstTermPaper.getText().toString().trim();
                String paperTwo = secondTermPaper.getText().toString().trim();
                String paperThree = thirdTermPaper.getText().toString().trim();

                int paper1Change;
                if(paperOne.isEmpty())
                {
                    paper1Change = 0;
                    paper1Score = paper1Change + 102;
                }
                else
                {
                    paper1Change = Integer.parseInt(paperOne);
                    paper1Score = paper1Change;
                }
                if(paper1Change > 100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Term Paper 1 is incorrect", Toast.LENGTH_LONG).show();
                    firstTermPaper.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int paper2Change;
                if(paperTwo.isEmpty())
                {
                    paper2Change = 0;
                    paper2Score = paper2Change + 102;
                }
                else
                {
                    paper2Change = Integer.parseInt(paperTwo);
                    paper2Score = paper2Change;
                }
                if(paper2Change > 100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Term Paper 2 is incorrect", Toast.LENGTH_LONG).show();
                    secondTermPaper.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int paper3Change;
                if(paperThree.isEmpty())
                {
                    paper3Change = 0;
                    paper3Score = paper3Change + 102;
                }
                else
                {
                    paper3Change = Integer.parseInt(paperThree);
                    paper3Score = paper3Change;
                }
                if(paper3Change > 100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for Term Paper 3 is incorrect", Toast.LENGTH_LONG).show();
                    thirdTermPaper.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                Map<String,Object>updatePaperScore = new HashMap<>();
                updatePaperScore.put(key1,paper1Score);
                updatePaperScore.put(key2,paper2Score);
                updatePaperScore.put(key3,paper3Score);

                store.collection("CourseData")
                        .document(documentName)
                        .update(updatePaperScore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Term Paper scores have been updated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error: The term paper scores have not been updated", Toast.LENGTH_LONG).show();
                            }
                        });

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