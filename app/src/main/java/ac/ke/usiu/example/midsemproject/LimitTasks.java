package ac.ke.usiu.example.midsemproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LimitTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_tasks);
        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        EditText assignment = findViewById(R.id.assignment);
        EditText labs = findViewById(R.id.labs);
        EditText project = findViewById(R.id.projects);
        EditText termPaper = findViewById(R.id.termPaper);
        EditText quiz = findViewById(R.id.quiz);
        EditText midExam = findViewById(R.id.midExam);
        EditText endExam = findViewById(R.id.endExam);
        Button setMark= findViewById(R.id.setScore);

        setMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String assignmentLimit = assignment.getText().toString();
                String labLimit = labs.getText().toString();
                String termPaperLimit = termPaper.getText().toString();
                String projectLimit = project.getText().toString();
                String quizLimit = quiz.getText().toString();
                String midExamLimit = midExam.getText().toString();
                String endExamLimit = endExam.getText().toString();

                //Sample of how the if conditions would be to ensure max limit is not passed
                int assignLimit = Integer.parseInt(assignmentLimit);
                int labLimits = Integer.parseInt(labLimit);
                int termPaperLimits = Integer.parseInt(termPaperLimit);
                int projectLimits = Integer.parseInt(projectLimit);
                int quizLimits = Integer.parseInt(quizLimit);
                int midExamLimits = Integer.parseInt(midExamLimit);
                int endExamLimits = Integer.parseInt(endExamLimit);

                //Make a new condition to limit all fields (maybe like the previous if else methods)
                //Add another checker to check if given value is empty in which case it makes is zero

                if (assignLimit<=10 ) {

                    Intent sendLimits = new Intent(LimitTasks.this, SetAssignmentScores.class);
                    sendLimits.putExtra(SetAssignmentScores.Assignment, assignmentLimit);
                    sendLimits.putExtra(SetAssignmentScores.Labs,labLimit );
                    sendLimits.putExtra(SetAssignmentScores.TermPapers, termPaperLimit);
                    sendLimits.putExtra(SetAssignmentScores.Projects, projectLimit);
                    sendLimits.putExtra(SetAssignmentScores.Quizzes, quizLimit);
                    sendLimits.putExtra(SetAssignmentScores.MidExam, midExamLimit);
                    sendLimits.putExtra(SetAssignmentScores.EndExam, endExamLimit);
                    startActivity(sendLimits);
                }
                else{
                    assignment.setError("The maximum number of assignments expected is 10");
                    return;
                }

            }
        });

    }
}