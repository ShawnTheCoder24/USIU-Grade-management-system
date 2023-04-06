package ac.ke.usiu.example.midsemproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetAssignmentScores extends AppCompatActivity {

    LinearLayout layout;
    public static String Assignment ="assignments";
    public static String Labs = "labs";
    public static String Quizzes = "quizzes";
    public static String Projects = "projects";
    public static String MidExam = "midexam";
    public static String EndExam= "endexam";
    public static String TermPapers = "termpapers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_assignment_scores);

        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        Bundle extras = getIntent().getExtras();
        layout = findViewById(R.id.linearStyle);

        String limit = extras.getString(Assignment);
        String lablimit = extras.getString(Labs);
        String projectlimit = extras.getString(Projects);
        String termpaperlimit = extras.getString(TermPapers);
        String midlimit = extras.getString(MidExam);
        String endlimit = extras.getString(EndExam);
        String quizlimit = extras.getString(Quizzes);

        int assignmentLimits = Integer.parseInt(limit);
        int labLimits = Integer.parseInt(lablimit);
        int projectLimits = Integer.parseInt(projectlimit);
        int termPaperLimits = Integer.parseInt(termpaperlimit);
        int midLimits = Integer.parseInt(midlimit);
        int endLimits = Integer.parseInt(endlimit);
        int quizLimits = Integer.parseInt(quizlimit);

        TextView myText = new TextView(this);
        LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        myText.setLayoutParams(parameter);
        myText.setText("Assignment Scores:");
        layout.addView(myText);
        List<EditText>allFields = new ArrayList<EditText>();

        for(int limiter=1; limiter<=assignmentLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String saver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(saver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Assignment" + myEdit.getId());
            allFields.add(myEdit);
            //Find out how to place spacing between the fields
            layout.addView(myEdit);

            // To do next:
            //Create a text view to show what scores are being inserted in that field
            //Add a "comments" edit text.
            // Do the code for calculating grade from the scores as a different method
            //Create an textview for showing the calculated grade
             // Create a new activity to accommodate for all fields (because of the name)

            //Sending data to DB is like this so variables are needed to store the data
            //Hmm...
            //This? https://www.youtube.com/watch?v=fcvdtwu0O3k It has a solution.. watch it.
            // May have to keep a limit on the amount of fields someone can have, maybe to a max of 10 for assignments and labs?
            //Then projects,quizzes, term papers and the two exams have their own limits not surpassing 5 (this is considering quizzes)


                /*
            Map<String, Object> user = new HashMap<>();
            user.put(KEY_NAME, Name);
            user.put(KEY_ID, ID);
            user.put(KEY_EMAIL, email);
            user.put(KEY_ROOM, room);
            user.put(KEY_PASS, password);
            user.put(KEY_IN, "notset");
            user.put(KEY_OUT, "notset");*/

        }

        TextView labText = new TextView(this);
        LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        labText.setLayoutParams(parameters);
        labText.setText("Lab Scores:");
        layout.addView(labText);

        for(int limiter=1; limiter<=labLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Lab" + myEdit.getId());
            layout.addView(myEdit);
        }

        TextView projectText = new TextView(this);
        LinearLayout.LayoutParams projparameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        labText.setLayoutParams(projparameters);
        labText.setText("Project Scores:");
        layout.addView(projectText);

        for(int limiter=1; limiter<=projectLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Project" + myEdit.getId());
            layout.addView(myEdit);
        }

        for(int limiter=1; limiter<=quizLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Quiz" + myEdit.getId());
            layout.addView(myEdit);
        }

        for(int limiter=1; limiter<=termPaperLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Term Paper" + myEdit.getId());
            layout.addView(myEdit);
        }

        for(int limiter=1; limiter<=midLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("Mid Examination" + myEdit.getId());
            layout.addView(myEdit);
        }

        for(int limiter=1; limiter<=endLimits; limiter++)
        {
            EditText myEdit = new EditText(this);
            String mySaver = String.valueOf(limiter);
            myEdit.setId(Integer.parseInt(mySaver));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            myEdit.setLayoutParams(params);
            myEdit.setHint("End Examinations" + myEdit.getId());
            layout.addView(myEdit);
        }

        Button storeGrades = new Button(this);
        storeGrades.setText("Store Data");
        LinearLayout.LayoutParams myParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        storeGrades.setLayoutParams(myParameters);
        layout.addView(storeGrades);


    }
}