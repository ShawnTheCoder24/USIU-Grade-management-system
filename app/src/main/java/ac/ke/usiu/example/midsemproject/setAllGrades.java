package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
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

public class setAllGrades extends AppCompatActivity {
    FirebaseAuth firestoreAuth;
    String KEY_StudentID ="Student ID" ;
    String KEY_CourseID ="Course ID" ;
    String KEY_assignmentOne = "Assignment 1" ;
    String KEY_assignmentTwo= "Assignment 2" ;
    String KEY_assignmentThree = "Assignment 3" ;
    String KEY_assignmentFour = "Assignment 4";
    String KEY_assignmentFive = "Assignment 5";

    String KEY_labOne = "Lab 1" ;
    String KEY_labTwo = "Lab 2" ;
    String KEY_labThree = "Lab 3" ;
    String KEY_labFour = "Lab 4";
    String KEY_labFive = "Lab 5";

    String KEY_quizOne = "Quiz 1" ;
    String KEY_quizTwo = "Quiz 2" ;
    String KEY_quizThree = "Quiz 3" ;
    String KEY_quizFour = "Quiz 4";
    String KEY_quizFive = "Quiz 5";

    String KEY_termPaperOne = "Term Paper 1" ;
    String KEY_termPaperTwo = "Term Paper 2" ;
    String KEY_termPaperThree = "Term Paper 3" ;

    String KEY_projectOne = "Project 1" ;
    String KEY_projectTwo = "Project 2" ;

    String KEY_midExam = "Mid Exam" ;

    String KEY_endExam = "End Exam";

    String KEY_comments = "Comments";

    int assignmentOneScore;
    int assignmentTwoScore;
    int assignmentThreeScore;
    int assignmentFourScore;
    int assignmentFiveScore;

    int labOneScore;
    int labTwoScore;
    int labThreeScore;
    int labFourScore;
    int labFiveScore;

    int quizOneScore;
    int quizTwoScore;
    int quizThreeScore;
    int quizFourScore;
    int quizFiveScore;

    int termPaperOneScore;
    int termPaperTwoScore;
    int termPaperThreeScore;

    int projectOneScore;
    int projectTwoScore;

    int midExamStudentScore;
    int endExamStudentScore;

    String substituteComment;
    String databaseDocumentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_all_grades);

        //Hiding the top bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        FirebaseFirestore courseDatabase = FirebaseFirestore.getInstance();
        firestoreAuth= FirebaseAuth.getInstance();

        EditText studId = findViewById(R.id.studentIdNumber);
        EditText courseId = findViewById(R.id.courseIdNumber);
        courseId.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        EditText assignment1 = findViewById(R.id.assignmentOne);
        EditText assignment2 = findViewById(R.id.assignmentTwo);
        EditText assignment3 = findViewById(R.id.assignmentThree);
        EditText assignment4 = findViewById(R.id.assignmentFour);
        EditText assignment5 = findViewById(R.id.assignmentFive);

        EditText lab1 = findViewById(R.id.labOne);
        EditText lab2 = findViewById(R.id.labTwo);
        EditText lab3 = findViewById(R.id.labThree);
        EditText lab4 = findViewById(R.id.labFour);
        EditText lab5 = findViewById(R.id.labFive);

        EditText quiz1 = findViewById(R.id.quizOne);
        EditText quiz2 = findViewById(R.id.quizTwo);
        EditText quiz3 = findViewById(R.id.quizThree);
        EditText quiz4 = findViewById(R.id.quizFour);
        EditText quiz5 = findViewById(R.id.quizFive);

        EditText termPaper1 = findViewById(R.id.termPaperOne);
        EditText termPaper2 = findViewById(R.id.termPaperTwo);
        EditText termPaper3 = findViewById(R.id.termPaperThree);


        EditText project1 = findViewById(R.id.projectOne);
        EditText project2 = findViewById(R.id.projectTwo);

        EditText midExamScore = findViewById(R.id.midExamGrade);

        EditText endExamScore = findViewById(R.id.endExamGrade);

        EditText comment = findViewById(R.id.lecComments);

        Button storeData = findViewById(R.id.storeAllData);
        storeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = studId.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Student ID not given", Toast.LENGTH_LONG).show();
                    studId.setError("Please insert a student ID");
                    return;
                }
                else if(studentID.length() != 6)
                {
                    Toast.makeText(getApplicationContext(), "Student ID is in the incorrect format", Toast.LENGTH_LONG).show();
                    studId.setError("Student ID can only be 6 digits long");
                    return;
                }
                String courseID = courseId.getText().toString().trim();

                if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Course ID not given", Toast.LENGTH_LONG).show();
                    courseId.setError("Please insert a course ID");
                    return;
                }
                else if(courseID.length() != 7)
                {
                    Toast.makeText(getApplicationContext(), "Course ID is in the incorrect format", Toast.LENGTH_LONG).show();
                    courseId.setError("Course ID can only be 7 characters long e.g EXA0000");
                    return;
                }
                databaseDocumentName = studentID+courseID;

                String assignmentOneText = assignment1.getText().toString().trim();
                String assignmentTwoText = assignment2.getText().toString().trim();
                String assignmentThreeText = assignment3.getText().toString().trim();
                String assignmentFourText = assignment4.getText().toString().trim();
                String assignmentFiveText = assignment5.getText().toString().trim();

                String labOneText = lab1.getText().toString().trim();
                String labTwoText = lab2.getText().toString().trim();
                String labThreeText= lab3.getText().toString().trim();
                String labFourText = lab4.getText().toString().trim();
                String labFiveText = lab5.getText().toString().trim();

                String quizOneText = quiz1.getText().toString().trim();
                String quizTwoText = quiz2.getText().toString().trim();
                String quizThreeText = quiz3.getText().toString().trim();
                String quizFourText = quiz4.getText().toString().trim();
                String quizFiveText = quiz5.getText().toString().trim();

                String termPaperOneText = termPaper1.getText().toString().trim();
                String termPaperTwoText = termPaper2.getText().toString().trim();
                String termPaperThreeText = termPaper3.getText().toString().trim();

                String projectOneText = project1.getText().toString().trim();
                String projectTwoText = project2.getText().toString().trim();

                String midExamText = midExamScore.getText().toString().trim();
                String endExamText = endExamScore.getText().toString().trim();
                String comments = comment.getText().toString().trim();

                if(comments.isEmpty())
                {
                    substituteComment="No comment";
                }
                else
                {
                    substituteComment=comments;
                }

                //Ensuring the values in the fields are valid
                int assignmentOne;
                if(assignmentOneText.isEmpty())
                {
                    assignmentOne = 0;
                    assignmentOneScore = assignmentOne + 102;
                }
                else {
                    assignmentOne = Integer.parseInt(assignmentOneText);
                    assignmentOneScore = assignmentOne;
                }
                if(assignmentOne >100)
                {
                    Toast.makeText(getApplicationContext(), "Assignment one input has an issue", Toast.LENGTH_LONG).show();
                    assignment1.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int assignmentTwo;
                if(assignmentTwoText.isEmpty())
                {
                    assignmentTwo = 0;
                    assignmentTwoScore = assignmentTwo + 102;
                }
                else {
                    assignmentTwo = Integer.parseInt(assignmentTwoText);
                    assignmentTwoScore = assignmentTwo;
                }

                if(assignmentTwo >100)
                {
                    Toast.makeText(getApplicationContext(), "Assignment two input has an issue", Toast.LENGTH_LONG).show();
                    assignment2.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int assignmentThree;
                if(assignmentThreeText.isEmpty())
                {
                    assignmentThree = 0;
                    assignmentThreeScore = assignmentThree + 102;
                }
                else {
                    assignmentThree = Integer.parseInt(assignmentThreeText);
                    assignmentThreeScore = assignmentThree;
                }

                if(assignmentThree >100)
                {
                    Toast.makeText(getApplicationContext(), "Assignment three input has an issue", Toast.LENGTH_LONG).show();
                    assignment3.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int assignmentFour;
                if(assignmentFourText.isEmpty())
                {
                    assignmentFour = 0;
                    assignmentFourScore = assignmentFour + 102;
                }
                else {
                    assignmentFour = Integer.parseInt(assignmentFourText);
                    assignmentFourScore = assignmentFour;
                }

                if(assignmentFour >100)
                {
                    Toast.makeText(getApplicationContext(), "Assignment four input has an issue", Toast.LENGTH_LONG).show();
                    assignment4.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int assignmentFive;
                if(assignmentFiveText.isEmpty())
                {
                    assignmentFive = 0;
                    assignmentFiveScore = assignmentFive + 102;
                }
                else {
                    assignmentFive = Integer.parseInt(assignmentFiveText);
                    assignmentFiveScore = assignmentFive;
                }

                if(assignmentFive >100)
                {
                    Toast.makeText(getApplicationContext(), "Assignment five input has an issue", Toast.LENGTH_LONG).show();
                    assignment5.setError("Please ensure the value inserted is out of 100");
                    return;
                }


                int labOne;
                if(labOneText.isEmpty())
                {
                    labOne=0;
                    labOneScore = labOne+ 102;
                }
                else {
                    labOne = Integer.parseInt(labOneText);
                    labOneScore= labOne;
                }
                if(labOne>100)
                {
                    Toast.makeText(getApplicationContext(), "Lab one input has an issue", Toast.LENGTH_LONG).show();
                    lab1.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int labTwo;
                if(labTwoText.isEmpty())
                {
                    labTwo=0;
                    labTwoScore = labTwo+ 102;
                }
                else {
                    labTwo = Integer.parseInt(labTwoText);
                    labTwoScore= labTwo;
                }
                if(labTwo>100)
                {
                    Toast.makeText(getApplicationContext(), "Lab two input has an issue", Toast.LENGTH_LONG).show();
                    lab2.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int labThree;
                if(labThreeText.isEmpty())
                {
                    labThree=0;
                    labThreeScore = labThree+ 102;
                }
                else {
                    labThree = Integer.parseInt(labThreeText);
                    labThreeScore= labThree;
                }
                if(labThree>100)
                {
                    Toast.makeText(getApplicationContext(), "Lab three input has an issue", Toast.LENGTH_LONG).show();
                    lab3.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int labFour;
                if(labFourText.isEmpty())
                {
                    labFour=0;
                    labFourScore = labFour+102;
                }
                else {
                    labFour = Integer.parseInt(labFourText);
                    labFourScore= labFour;
                }
                if(labFour>100)
                {
                    Toast.makeText(getApplicationContext(), "Lab four input has an issue", Toast.LENGTH_LONG).show();
                    lab4.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int labFive;
                if(labFiveText.isEmpty())
                {
                    labFive=0;
                    labFiveScore = labFive+102;
                }
                else {
                    labFive = Integer.parseInt(labFiveText);
                    labFiveScore= labFive;
                }
                if(labFive>100)
                {
                    Toast.makeText(getApplicationContext(), "Lab five input has an issue", Toast.LENGTH_LONG).show();
                    lab5.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int quizOne;
                if(quizOneText.isEmpty())
                {
                    quizOne = 0;
                    quizOneScore = quizOne + 102;
                }
                else{
                    quizOne = Integer.parseInt(quizOneText);
                    quizOneScore=quizOne;
                }
                if(quizOne>100)
                {
                    Toast.makeText(getApplicationContext(), "Quiz one input has an issue", Toast.LENGTH_LONG).show();
                    quiz1.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int quizTwo;
                if(quizTwoText.isEmpty())
                {
                    quizTwo = 0;
                    quizTwoScore = quizTwo + 102;
                }
                else{
                    quizTwo = Integer.parseInt(quizTwoText);
                    quizTwoScore=quizTwo;
                }
                if(quizTwo>100)
                {
                    Toast.makeText(getApplicationContext(), "Quiz two input has an issue", Toast.LENGTH_LONG).show();
                    quiz2.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int quizThree;
                if(quizThreeText.isEmpty())
                {
                    quizThree = 0;
                    quizThreeScore = quizThree + 102;
                }
                else{
                    quizThree= Integer.parseInt(quizThreeText);
                    quizThreeScore=quizThree;
                }
                if(quizThree>100)
                {
                    Toast.makeText(getApplicationContext(), "Quiz three input has an issue", Toast.LENGTH_LONG).show();
                    quiz3.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int quizFour;
                if(quizFourText.isEmpty())
                {
                    quizFour = 0;
                    quizFourScore = quizFour + 102;
                }
                else{
                    quizFour = Integer.parseInt(quizFourText);
                    quizFourScore=quizFour;
                }
                if(quizFour>100)
                {
                    Toast.makeText(getApplicationContext(), "Quiz four input has an issue", Toast.LENGTH_LONG).show();
                    quiz4.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int quizFive;
                if(quizFiveText.isEmpty())
                {
                    quizFive = 0;
                    quizFiveScore = quizFive + 102;
                }
                else{
                    quizFive = Integer.parseInt(quizFiveText);
                    quizFiveScore=quizFive;
                }
                if(quizFive>100)
                {
                    Toast.makeText(getApplicationContext(), "Quiz five input has an issue", Toast.LENGTH_LONG).show();
                    quiz5.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int termPaperOne;
                if(termPaperOneText.isEmpty())
                {
                    termPaperOne = 0;
                    termPaperOneScore = termPaperOne + 102;
                }
                else{
                    termPaperOne = Integer.parseInt(termPaperOneText);
                    termPaperOneScore=termPaperOne;
                }
                if(termPaperOne>100)
                {
                    Toast.makeText(getApplicationContext(), "Term paper one input has an issue", Toast.LENGTH_LONG).show();
                    termPaper1.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int termPaperTwo;
                if(termPaperTwoText.isEmpty())
                {
                    termPaperTwo = 0;
                    termPaperTwoScore = termPaperTwo + 102;
                }
                else{
                    termPaperTwo = Integer.parseInt(termPaperTwoText);
                    termPaperTwoScore=termPaperTwo;
                }
                if(termPaperTwo>100)
                {
                    Toast.makeText(getApplicationContext(), "Term paper two input has an issue", Toast.LENGTH_LONG).show();
                    termPaper2.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int termPaperThree;
                if(termPaperThreeText.isEmpty())
                {
                    termPaperThree = 0;
                    termPaperThreeScore = termPaperThree + 102;
                }
                else{
                    termPaperThree = Integer.parseInt(termPaperThreeText);
                    termPaperThreeScore=termPaperThree;
                }
                if(termPaperThree>100)
                {
                    Toast.makeText(getApplicationContext(), "Term paper three input has an issue", Toast.LENGTH_LONG).show();
                    termPaper3.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int projectOne;
                if(projectOneText.isEmpty())
                {
                    projectOne = 0;
                    projectOneScore = projectOne + 102;
                }
                else{
                    projectOne = Integer.parseInt(projectOneText);
                    projectOneScore=projectOne;
                }
                if(projectOne>100)
                {
                    Toast.makeText(getApplicationContext(), "Project one field has an issue", Toast.LENGTH_LONG).show();
                    project1.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int projectTwo;
                if(projectTwoText.isEmpty())
                {
                    projectTwo = 0;
                    projectTwoScore = projectTwo + 102;
                }
                else{
                    projectTwo = Integer.parseInt(projectTwoText);
                    projectTwoScore=projectTwo;
                }
                if(projectTwo>100)
                {
                    Toast.makeText(getApplicationContext(), "Project two input has an issue", Toast.LENGTH_LONG).show();
                    project2.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                int midExam;
                if(midExamText.isEmpty())
                {
                    midExam = 0;
                    midExamStudentScore = midExam + 102;
                }
                else{
                    midExam = Integer.parseInt(midExamText);
                    midExamStudentScore=midExam;
                }
                if(midExam>100)
                {
                    Toast.makeText(getApplicationContext(), "Mid examination input has an issue", Toast.LENGTH_LONG).show();
                    midExamScore.setError("Please ensure the value inserted is out of 100");
                    return;
                }
                int endExam;
                if(endExamText.isEmpty())
                {
                    endExam = 0;
                    endExamStudentScore = endExam + 102;
                }
                else{
                    endExam = Integer.parseInt(endExamText);
                    endExamStudentScore=endExam;
                }
                if(endExam>100)
                {
                    Toast.makeText(getApplicationContext(), "End examination input has an issue", Toast.LENGTH_LONG).show();
                    endExamScore.setError("Please ensure the value inserted is out of 100");
                    return;
                }

                //Send data to the firebase database
                Map<String, Object> gradeRecord = new HashMap<>();
                gradeRecord.put(KEY_StudentID, studentID);
                gradeRecord.put(KEY_CourseID, courseID);
                gradeRecord.put(KEY_assignmentOne, assignmentOneScore);
                gradeRecord.put(KEY_assignmentTwo, assignmentTwoScore);
                gradeRecord.put(KEY_assignmentThree, assignmentThreeScore);
                gradeRecord.put(KEY_assignmentFour, assignmentFourScore);
                gradeRecord.put(KEY_assignmentFive, assignmentFiveScore);

                gradeRecord.put(KEY_labOne, labOneScore);
                gradeRecord.put(KEY_labTwo, labTwoScore);
                gradeRecord.put(KEY_labThree, labThreeScore);
                gradeRecord.put(KEY_labFour, labFourScore);
                gradeRecord.put(KEY_labFive, labFiveScore);

                gradeRecord.put(KEY_quizOne, quizOneScore);
                gradeRecord.put(KEY_quizTwo, quizTwoScore);
                gradeRecord.put(KEY_quizThree, quizThreeScore);
                gradeRecord.put(KEY_quizFour, quizFourScore);
                gradeRecord.put(KEY_quizFive, quizFiveScore);

                gradeRecord.put(KEY_termPaperOne, termPaperOneScore);
                gradeRecord.put(KEY_termPaperTwo, termPaperTwoScore);
                gradeRecord.put(KEY_termPaperThree, termPaperThreeScore);

                gradeRecord.put(KEY_projectOne, projectOneScore);
                gradeRecord.put(KEY_projectTwo, projectTwoScore);

                gradeRecord.put(KEY_midExam, midExamStudentScore);

                gradeRecord.put(KEY_endExam, endExamStudentScore);

                gradeRecord.put(KEY_comments, substituteComment);

                //Get current lecturer ID
                Map<String,String> courseUsers = new HashMap<String,String>();
                courseDatabase.collection("AppUsers")
                        .document(firestoreAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lecturer = task.getResult();
                                    if(lecturer.exists())
                                    {
                                        courseUsers.put("Lecturer ID",lecturer.get("ID").toString());
                                        String lecturerID = courseUsers.get("Lecturer ID");

                                        //Get the information on lecturer and student ID in course information
                                        courseDatabase.collection("CourseInformation")
                                                .document(databaseDocumentName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInfo = task.getResult();
                                                        if(userInfo.exists())
                                                        {
                                                            //courseUsers.put("Student",userInfo.get("Student ID").toString());
                                                            courseUsers.put("Lecturer", userInfo.get("Lecturer ID").toString());

                                                            String lecID = courseUsers.get("Lecturer");
                                                            //String studID = courseUsers.get("Student");

                                                       //compare the received data with what the lecturer ID is
                                                            if(lecturerID.contentEquals(lecID))
                                                            {
                                                                courseDatabase.collection("CourseData")
                                                                        .document(databaseDocumentName)
                                                                        .set(gradeRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                Toast.makeText(getApplicationContext(), "All data has been successfully stored", Toast.LENGTH_LONG).show();


                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getApplicationContext(), "Error: Failed to send data to the Database", Toast.LENGTH_LONG).show();

                                                                            }
                                                                        });
                                                            }
                                                            else if(!lecturerID.contentEquals(lecID))
                                                            {
                                                                Toast.makeText(getApplicationContext(), "There is an error on the course ID input area", Toast.LENGTH_LONG).show();
                                                                courseId.setError("You are not allowed to store grades for this course as you do not teach it");
                                                                return;
                                                            }
                                                        }
                                                        // if the document was not found, check if the error could be because the course ID used in the name for the document is not equal to what is given
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(), "The record could not be found. Please check the course ID given", Toast.LENGTH_LONG).show();
                                                            courseId.setError("The specified course record for the student could not be found. Please check the course ID given.");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                            }
                        });

            }
        });


    }
}