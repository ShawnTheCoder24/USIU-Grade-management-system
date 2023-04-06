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
import java.util.Locale;
import java.util.Map;

public class quizUpdate extends AppCompatActivity {
    public static String scoreStorage;
    String key1 = "Quiz 1";
    String key2 = "Quiz 2";
    String key3 = "Quiz 3";
    String key4 = "Quiz 4";
    String key5 = "Quiz 5";

    int quiz1Score;
    int quiz2Score;
    int quiz3Score;
    int quiz4Score;
    int quiz5Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_update);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseFirestore scoreStore = FirebaseFirestore.getInstance();

        EditText firstQuiz = findViewById(R.id.quiz1);
        EditText secondQuiz = findViewById(R.id.quiz2);
        EditText thirdQuiz = findViewById(R.id.quiz3);
        EditText fourthQuiz = findViewById(R.id.quiz4);
        EditText fifthQuiz = findViewById(R.id.quiz5);
        Bundle documentName = getIntent().getExtras();
        Button updateQuiz = findViewById(R.id.updateStudentQuizzes);
        Button homePage = findViewById(R.id.backButton);

        updateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myStorage = documentName.getString(scoreStorage);
                String quizOne = firstQuiz.getText().toString().trim();
                String quizTwo = secondQuiz.getText().toString().trim();
                String quizThree = thirdQuiz.getText().toString().trim();
                String quizFour = fourthQuiz.getText().toString().trim();
                String quizFive = fifthQuiz.getText().toString().trim();

                int quiz1Change;
                if(quizOne.isEmpty())
                {
                    quiz1Change = 0;
                    quiz1Score = quiz1Change + 102;
                }
                else
                {
                    quiz1Change = Integer.parseInt(quizOne);
                    quiz1Score = quiz1Change;
                }
                if(quiz1Change>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for quiz 1 is incorrect", Toast.LENGTH_LONG).show();
                    firstQuiz.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int quiz2Change;
                if(quizTwo.isEmpty())
                {
                    quiz2Change = 0;
                    quiz2Score = quiz2Change + 102;
                }
                else
                {
                    quiz2Change = Integer.parseInt(quizTwo);
                    quiz2Score = quiz2Change;
                }
                if(quiz2Change>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for quiz 2 is incorrect", Toast.LENGTH_LONG).show();
                    secondQuiz.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int quiz3Change;
                if(quizThree.isEmpty())
                {
                    quiz3Change = 0;
                    quiz3Score = quiz3Change + 102;
                }
                else
                {
                    quiz3Change = Integer.parseInt(quizThree);
                    quiz3Score = quiz3Change;
                }
                if(quiz3Change>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for quiz 3 is incorrect", Toast.LENGTH_LONG).show();
                    thirdQuiz.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int quiz4Change;
                if(quizFour.isEmpty())
                {
                    quiz4Change = 0;
                    quiz4Score = quiz4Change + 102;
                }
                else
                {
                    quiz4Change = Integer.parseInt(quizFour);
                    quiz4Score = quiz4Change;
                }
                if(quiz4Change>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for quiz 4 is incorrect", Toast.LENGTH_LONG).show();
                    fourthQuiz.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                int quiz5Change;
                if(quizFive.isEmpty())
                {
                    quiz5Change = 0;
                    quiz5Score = quiz5Change + 102;
                }
                else
                {
                    quiz5Change = Integer.parseInt(quizFive);
                    quiz5Score = quiz5Change;
                }
                if(quiz5Change>100)
                {
                    Toast.makeText(getApplicationContext(),"The inserted score for quiz 5 is incorrect", Toast.LENGTH_LONG).show();
                    fifthQuiz.setError("The score must be in percentage format(from 0 to 100 only)");
                    return;
                }

                Map<String,Object>updateQuizScore = new HashMap<>();
                updateQuizScore.put(key1,quiz1Score);
                updateQuizScore.put(key2,quiz2Score);
                updateQuizScore.put(key3,quiz3Score);
                updateQuizScore.put(key4,quiz4Score);
                updateQuizScore.put(key5,quiz5Score);

                scoreStore.collection("CourseData")
                        .document(myStorage)
                        .update(updateQuizScore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Quiz scores have been updated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Quiz scores could not be updated",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}