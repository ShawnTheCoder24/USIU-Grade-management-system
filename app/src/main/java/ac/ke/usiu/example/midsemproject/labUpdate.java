package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class labUpdate extends AppCompatActivity {
    public static String storage;
    String key1 = "Lab 1";
    String key2 = "Lab 2";
    String key3 = "Lab 3";
    String key4 = "Lab 4";
    String key5 = "Lab 5";

    int lab1Score;
    int lab2Score;
    int lab3Score;
    int lab4Score;
    int lab5Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_update);
        //Hiding the app name
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore myStore = FirebaseFirestore.getInstance();

        EditText firstLab = findViewById(R.id.lab1);
        EditText secondLab = findViewById(R.id.lab2);
        EditText thirdLab = findViewById(R.id.lab3);
        EditText fourthLab = findViewById(R.id.lab4);
        EditText fifthLab = findViewById(R.id.lab5);
        Button goBack = findViewById(R.id.goBack);
        Button update = findViewById(R.id.updateStudentLabs);
        Bundle extraData = getIntent().getExtras();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object>updateLabRecord = new HashMap<>();
                String storageName = extraData.getString(storage);

                //Storing the inserted values into variables
                String labOne = firstLab.getText().toString().trim();
                String labTwo = secondLab.getText().toString().trim();
                String labThree = thirdLab.getText().toString().trim();
                String labFour = fourthLab.getText().toString().trim();
                String labFive = fifthLab.getText().toString().trim();

                //Conditions to ensure user inserted values are valid
                int lab1update;
                if(labOne.isEmpty())
                {
                    lab1update = 0;
                    lab1Score = lab1update+102;
                }
                else
                {
                    lab1update=Integer.parseInt(labOne);
                    lab1Score = lab1update;
                }
                if(lab1update>100)
                {
                    Toast.makeText(getApplicationContext(),"There is an issue with lab one's inserted score",Toast.LENGTH_LONG).show();
                    firstLab.setError("The score must be in percentage form (from 0 to 100 only)");
                    return;
                }

                int lab2update;
                if(labTwo.isEmpty())
                {
                    lab2update = 0;
                    lab2Score = lab2update + 102;
                }
                else
                {
                    lab2update=Integer.parseInt(labTwo);
                    lab2Score = lab2update;
                }
                if(lab2update>100)
                {
                    Toast.makeText(getApplicationContext(),"There is an issue with lab two's inserted score",Toast.LENGTH_LONG).show();
                    secondLab.setError("The score must be in percentage form (from 0 to 100 only)");
                    return;
                }

                int lab3update;
                if(labThree.isEmpty())
                {
                    lab3update = 0;
                    lab3Score = lab3update + 102;
                }
                else
                {
                    lab3update=Integer.parseInt(labThree);
                    lab3Score = lab3update;
                }
                if(lab3update>100)
                {
                    Toast.makeText(getApplicationContext(),"There is an issue with lab three's inserted score",Toast.LENGTH_LONG).show();
                    thirdLab.setError("The score must be in percentage form (from 0 to 100 only)");
                    return;
                }

                int lab4update;
                if(labFour.isEmpty())
                {
                    lab4update = 0;
                    lab4Score = lab4update + 102;
                }
                else
                {
                    lab4update=Integer.parseInt(labFour);
                    lab4Score = lab4update;
                }
                if(lab4update>100)
                {
                    Toast.makeText(getApplicationContext(),"There is an issue with lab four's inserted score",Toast.LENGTH_LONG).show();
                    fourthLab.setError("The score must be in percentage form (from 0 to 100 only)");
                    return;
                }

                int lab5update;
                if(labFive.isEmpty())
                {
                    lab5update = 0;
                    lab5Score = lab5update + 102;
                }
                else
                {
                    lab5update=Integer.parseInt(labFive);
                    lab5Score = lab5update;
                }
                if(lab5update>100)
                {
                    Toast.makeText(getApplicationContext(),"There is an issue with lab five's inserted score",Toast.LENGTH_LONG).show();
                    fifthLab.setError("The score must be in percentage form (from 0 to 100 only)");
                    return;
                }


                //Mapping the data to the key
                updateLabRecord.put(key1,lab1Score);
                updateLabRecord.put(key2,lab2Score);
                updateLabRecord.put(key3,lab3Score);
                updateLabRecord.put(key4,lab4Score);
                updateLabRecord.put(key5,lab5Score);

                //updating the scores
                myStore.collection("CourseData")
                        .document(storageName)
                        .update(updateLabRecord)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Lab data has been updated", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Lab data could not be updated",Toast.LENGTH_LONG).show();
                            }
                        });
            }

        });

        //Defining what happens when the back button is clicked
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}