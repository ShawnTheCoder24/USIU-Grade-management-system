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

public class ViewLabScores extends AppCompatActivity {
    public static String storeName;
    String databaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lab_scores);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        FirebaseFirestore myDb = FirebaseFirestore.getInstance();
        TextView score1 = findViewById(R.id.viewLabScore1);
        TextView score2 = findViewById(R.id.viewLabScore2);
        TextView score3 = findViewById(R.id.viewLabScore3);
        TextView score4 = findViewById(R.id.viewLabScore4);
        TextView score5 = findViewById(R.id.viewLabScore5);
        Button back = findViewById(R.id.returnToPreviousPage);
        Bundle store = getIntent().getExtras();

        databaseName = store.getString(storeName);

        Map<String,String> labData = new HashMap<String,String>();

        myDb.collection("CourseData")
                .document(databaseName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot labScores = task.getResult();
                        if(labScores.exists())
                        {
                            labData.put("Lab1 Score", labScores.get("Lab 1").toString());
                            labData.put("Lab2 Score", labScores.get("Lab 2").toString());
                            labData.put("Lab3 Score", labScores.get("Lab 3").toString());
                            labData.put("Lab4 Score", labScores.get("Lab 4").toString());
                            labData.put("Lab5 Score", labScores.get("Lab 5").toString());
                            //Place data in a string variable
                            String lab1 = labData.get("Lab1 Score");
                            String lab2 = labData.get("Lab2 Score");
                            String lab3 = labData.get("Lab3 Score");
                            String lab4 = labData.get("Lab4 Score");
                            String lab5 = labData.get("Lab5 Score");

                            //Set the data as the textview text if the value is not equal to 102
                            if(lab1.contentEquals("102"))
                            {
                                score1.setText("Not assigned");
                            }
                            else
                            {
                                score1.setText(lab1);
                            }

                            if(lab2.contentEquals("102"))
                            {
                                score2.setText("Not assigned");
                            }
                            else
                            {
                                score2.setText(lab2);
                            }

                            if(lab3.contentEquals("102"))
                            {
                                score3.setText("Not assigned");
                            }
                            else
                            {
                                score3.setText(lab3);
                            }

                            if(lab4.contentEquals("102"))
                            {
                                score4.setText("Not assigned");
                            }
                            else
                            {
                                score4.setText(lab4);
                            }

                            if(lab5.contentEquals("102"))
                            {
                                score5.setText("Not assigned");
                            }
                            else
                            {
                                score5.setText(lab5);
                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error: Could not find document",Toast.LENGTH_LONG).show();
                        }
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