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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OverallGrades extends AppCompatActivity {
    public static String dbName;
    String store;


    //counters (to be used for division in order to get the overall percentage)
    int givenAssignments = 0;
    int givenLabs = 0;
    int givenQuizzes = 0;
    int givenMidExam = 0;
    int givenEndExam = 0;
    int givenTermPapers = 0;
    int givenProjects = 0;
    int allGiven = 0;

    //variables for totals
    int assignmentTotal;
    int labTotal;
    int quizTotal;
    int midExamTotal;
    int endExamTotal;
    int termPaperTotal;
    int projectTotal;
    int grandTotal;

    //Variable to hold the final grade for the course
    int finalGrade;

    //Variable for quality points
    float qualityPoint;

    //Variable for GPA credits
    float GPAcredits;

    //Variable for course GPA
    float GPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_grades);

        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseAuth authenticate = FirebaseAuth.getInstance();
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

        TextView labs = findViewById(R.id.labScores);
        TextView quizzes = findViewById(R.id.quizScore);
        TextView termPapers = findViewById(R.id.paperScore);
        TextView assignments = findViewById(R.id.assignmentScore);
        TextView midExam = findViewById(R.id.exam1Score);
        TextView endExam = findViewById(R.id.exam2Score);
        TextView projects = findViewById(R.id.projectScore);
        TextView total = findViewById(R.id.grandTotalScore);
        TextView gpaScore = findViewById(R.id.courseGPAScore);
        TextView comments = findViewById(R.id.Comments);
        Button goBack = findViewById(R.id.backToSearch);
        Bundle storeName = getIntent().getExtras();

        store = storeName.getString(dbName);

        Map<String,String> scores = new HashMap<>();

        firestoreDB.collection("CourseData")
                .document(store)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot gradeStorage = task.getResult();
                        if(gradeStorage.exists()) {
                            //For labs and comments
                            scores.put("Comments", gradeStorage.get("Comments").toString());
                            scores.put("Lab1", gradeStorage.get("Lab 1").toString());
                            scores.put("Lab2", gradeStorage.get("Lab 2").toString());
                            scores.put("Lab3", gradeStorage.get("Lab 3").toString());
                            scores.put("Lab4", gradeStorage.get("Lab 4").toString());
                            scores.put("Lab5", gradeStorage.get("Lab 5").toString());

                            String lecComment = scores.get("Comments");
                            String lab1 = scores.get("Lab1");
                            String lab2 = scores.get("Lab2");
                            String lab3 = scores.get("Lab3");
                            String lab4 = scores.get("Lab4");
                            String lab5 = scores.get("Lab5");

                            comments.setText(lecComment);

                            //Check the scores received and turn them to integers if they are not 102, if they are 102 change them to zero
                            int labOne;
                            if (!lab1.contentEquals("102")) {
                                labOne = Integer.parseInt(lab1);
                                givenLabs = givenLabs + 1;
                            } else {
                                labOne = 0;
                            }
                            int labTwo;
                            if (!lab2.contentEquals("102")) {
                                labTwo = Integer.parseInt(lab2);
                                givenLabs = givenLabs + 1;
                            } else {
                                labTwo = 0;
                            }
                            int labThree;
                            if (!lab3.contentEquals("102")) {
                                labThree = Integer.parseInt(lab3);
                                givenLabs = givenLabs + 1;
                            } else {
                                labThree = 0;
                            }
                            int labFour;
                            if (!lab4.contentEquals("102")) {
                                labFour = Integer.parseInt(lab4);
                                givenLabs = givenLabs + 1;
                            } else {
                                labFour = 0;
                            }
                            int labFive;
                            if (!lab5.contentEquals("102")) {
                                labFive = Integer.parseInt(lab5);
                                givenLabs = givenLabs + 1;
                            } else {
                                labFive = 0;
                            }

                            if (givenLabs == 0) {
                                labs.setText("Not assigned");
                            } else {
                                labTotal = (labOne + labTwo + labThree + labFour + labFive) / givenLabs;

                                //Add one to the total counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (labTotal > 89 && labTotal < 101) {
                                    labs.setText("A");
                                } else if (labTotal > 86 && labTotal < 90) {
                                    labs.setText("A-");
                                } else if (labTotal > 83 && labTotal < 87) {
                                    labs.setText("B+");
                                } else if (labTotal > 79 && labTotal < 84) {
                                    labs.setText("B");
                                } else if (labTotal > 76 && labTotal < 80) {
                                    labs.setText("B-");
                                } else if (labTotal > 73 && labTotal < 77) {
                                    labs.setText("C+");
                                } else if (labTotal > 69 && labTotal < 74) {
                                    labs.setText("C");
                                } else if (labTotal > 66 && labTotal < 70) {
                                    labs.setText("C-");
                                } else if (labTotal > 63 && labTotal < 67) {
                                    labs.setText("D+");
                                } else if (labTotal > 61 && labTotal < 64) {
                                    labs.setText("D");
                                } else if (labTotal > 59 && labTotal < 62) {
                                    labs.setText("D-");
                                } else {
                                    labs.setText("F");
                                }
                            }

                            //For quizzes
                            scores.put("Quiz1", gradeStorage.get("Quiz 1").toString());
                            scores.put("Quiz2", gradeStorage.get("Quiz 2").toString());
                            scores.put("Quiz3", gradeStorage.get("Quiz 3").toString());
                            scores.put("Quiz4", gradeStorage.get("Quiz 4").toString());
                            scores.put("Quiz5", gradeStorage.get("Quiz 5").toString());

                            String quiz1 = scores.get("Quiz1");
                            String quiz2 = scores.get("Quiz2");
                            String quiz3 = scores.get("Quiz3");
                            String quiz4 = scores.get("Quiz4");
                            String quiz5 = scores.get("Quiz5");

                            //Check the scores received and turn them to integers if they are not 102, if they are 102 change them to zero
                            int quizOne;
                            if (!quiz1.contentEquals("102")) {
                                quizOne = Integer.parseInt(quiz1);
                                givenQuizzes = givenQuizzes + 1;
                            } else {
                                quizOne = 0;
                            }
                            int quizTwo;
                            if (!quiz2.contentEquals("102")) {
                                quizTwo = Integer.parseInt(quiz2);
                                givenQuizzes = givenQuizzes + 1;
                            } else {
                                quizTwo = 0;
                            }
                            int quizThree;
                            if (!quiz3.contentEquals("102")) {
                                quizThree = Integer.parseInt(quiz3);
                                givenQuizzes = givenQuizzes + 1;
                            } else {
                                quizThree = 0;
                            }
                            int quizFour;
                            if (!quiz4.contentEquals("102")) {
                                quizFour = Integer.parseInt(quiz4);
                                givenQuizzes = givenQuizzes + 1;
                            } else {
                                quizFour = 0;
                            }
                            int quizFive;
                            if (!quiz5.contentEquals("102")) {
                                quizFive = Integer.parseInt(quiz5);
                                givenQuizzes = givenQuizzes + 1;
                            } else {
                                quizFive = 0;
                            }

                            if (givenQuizzes == 0) {
                                quizzes.setText("Not assigned");
                            } else {
                                quizTotal = (quizOne + quizTwo + quizThree + quizFour + quizFive) / givenQuizzes;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (quizTotal > 89 && quizTotal < 101) {
                                    quizzes.setText("A");
                                } else if (quizTotal > 86 && quizTotal < 90) {
                                    quizzes.setText("A-");
                                } else if (quizTotal > 83 && quizTotal < 87) {
                                    quizzes.setText("B+");
                                } else if (quizTotal > 79 && quizTotal < 84) {
                                    quizzes.setText("B");
                                } else if (quizTotal > 76 && quizTotal < 80) {
                                    quizzes.setText("B-");
                                } else if (quizTotal > 73 && quizTotal < 77) {
                                    quizzes.setText("C+");
                                } else if (quizTotal > 69 && quizTotal < 74) {
                                    quizzes.setText("C");
                                } else if (quizTotal > 66 && quizTotal < 70) {
                                    quizzes.setText("C-");
                                } else if (quizTotal > 63 && quizTotal < 67) {
                                    quizzes.setText("D+");
                                } else if (quizTotal > 61 && quizTotal < 64) {
                                    quizzes.setText("D");
                                } else if (quizTotal > 59 && quizTotal < 62) {
                                    quizzes.setText("D-");
                                } else {
                                    quizzes.setText("F");
                                }
                            }

                            //For term papers
                            scores.put("Paper1",gradeStorage.get("Term Paper 1").toString());
                            scores.put("Paper2",gradeStorage.get("Term Paper 2").toString());
                            scores.put("Paper3",gradeStorage.get("Term Paper 3").toString());

                            String paper1 = scores.get("Paper1");
                            String paper2 = scores.get("Paper2");
                            String paper3 = scores.get("Paper3");

                            int termPaperOne;
                            if(!paper1.contentEquals("102"))
                            {
                                termPaperOne = Integer.parseInt(paper1);
                                givenTermPapers = givenTermPapers+1;
                            }
                            else
                            {
                                termPaperOne = 0;
                            }

                            int termPaperTwo;
                            if(!paper2.contentEquals("102"))
                            {
                                termPaperTwo = Integer.parseInt(paper2);
                                givenTermPapers = givenTermPapers+1;
                            }
                            else
                            {
                                termPaperTwo = 0;
                            }

                            int termPaperThree;
                            if(!paper3.contentEquals("102"))
                            {
                                termPaperThree = Integer.parseInt(paper3);
                                givenTermPapers = givenTermPapers+1;
                            }
                            else
                            {
                                termPaperThree = 0;
                            }

                            if(givenTermPapers==0)
                            {
                                termPapers.setText("Not assigned");
                            }

                            else
                            {
                                termPaperTotal = (termPaperOne + termPaperTwo+ termPaperThree)/givenTermPapers;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                if (termPaperTotal > 89 && termPaperTotal < 101)
                                {
                                    termPapers.setText("A");
                                }
                                else if (termPaperTotal > 86 && termPaperTotal < 90)
                                {
                                    termPapers.setText("A-");
                                }
                                else if (termPaperTotal > 83 && termPaperTotal < 87)
                                {
                                    termPapers.setText("B+");
                                }
                                else if (termPaperTotal > 79 && termPaperTotal < 84)
                                {
                                    termPapers.setText("B");
                                }
                                else if (termPaperTotal > 76 && termPaperTotal < 80)
                                {
                                    termPapers.setText("B-");
                                }
                                else if (termPaperTotal > 73 && termPaperTotal < 77)
                                {
                                    termPapers.setText("C+");
                                }
                                else if (termPaperTotal > 69 && termPaperTotal < 74)
                                {
                                    termPapers.setText("C");
                                }
                                else if (termPaperTotal > 66 && termPaperTotal < 70)
                                {
                                    termPapers.setText("C-");
                                }
                                else if (termPaperTotal > 63 && termPaperTotal < 67)
                                {
                                    termPapers.setText("D+");
                                }
                                else if (termPaperTotal > 61 && termPaperTotal < 64)
                                {
                                    termPapers.setText("D");
                                }
                                else if (termPaperTotal > 59 && termPaperTotal < 62)
                                {
                                    termPapers.setText("D-");
                                }
                                else
                                {
                                    termPapers.setText("F");
                                }

                            }
                            //For assignments
                            scores.put("Assignment1", gradeStorage.get("Assignment 1").toString());
                            scores.put("Assignment2", gradeStorage.get("Assignment 2").toString());
                            scores.put("Assignment3", gradeStorage.get("Assignment 3").toString());
                            scores.put("Assignment4", gradeStorage.get("Assignment 4").toString());
                            scores.put("Assignment5", gradeStorage.get("Assignment 5").toString());

                            String assignment1 = scores.get("Assignment1");
                            String assignment2 = scores.get("Assignment2");
                            String assignment3 = scores.get("Assignment3");
                            String assignment4 = scores.get("Assignment4");
                            String assignment5 = scores.get("Assignment5");

                            //Check the scores received and turn them to integers if they are not 102, if they are 102 change them to zero
                            int assignmentOne;
                            if (!assignment1.contentEquals("102")) {
                                assignmentOne = Integer.parseInt(assignment1);
                                givenAssignments = givenAssignments + 1;
                            } else {
                                assignmentOne = 0;
                            }
                            int assignmentTwo;
                            if (!assignment2.contentEquals("102")) {
                                assignmentTwo = Integer.parseInt(assignment2);
                                givenAssignments = givenAssignments + 1;
                            } else {
                                assignmentTwo = 0;
                            }
                            int assignmentThree;
                            if (!assignment3.contentEquals("102")) {
                                assignmentThree = Integer.parseInt(assignment3);
                                givenAssignments = givenAssignments + 1;
                            } else {
                                assignmentThree = 0;
                            }
                            int assignmentFour;
                            if (!assignment4.contentEquals("102")) {
                                assignmentFour = Integer.parseInt(assignment4);
                                givenAssignments = givenAssignments + 1;
                            } else {
                                assignmentFour = 0;
                            }
                            int assignmentFive;
                            if (!assignment5.contentEquals("102")) {
                                assignmentFive = Integer.parseInt(assignment5);
                                givenAssignments = givenAssignments + 1;
                            } else {
                                assignmentFive = 0;
                            }

                            if (givenAssignments == 0)
                            {
                                assignments.setText("Not assigned");
                            }

                            else
                            {
                                assignmentTotal = (assignmentOne + assignmentTwo + assignmentThree + assignmentFour + assignmentFive) / givenAssignments;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (assignmentTotal > 89 && assignmentTotal < 101)
                                {
                                    assignments.setText("A");
                                }
                                else if (assignmentTotal > 86 && assignmentTotal < 90)
                                {
                                    assignments.setText("A-");
                                }
                                else if (assignmentTotal > 83 && assignmentTotal < 87)
                                {
                                    assignments.setText("B+");
                                }
                                else if (assignmentTotal > 79 && assignmentTotal < 84)
                                {
                                    assignments.setText("B");
                                }
                                else if (assignmentTotal > 76 && assignmentTotal < 80)
                                {
                                    assignments.setText("B-");
                                }
                                else if (assignmentTotal > 73 && assignmentTotal < 77)
                                {
                                    assignments.setText("C+");
                                }
                                else if (assignmentTotal > 69 && assignmentTotal < 74)
                                {
                                    assignments.setText("C");
                                }
                                else if (assignmentTotal > 66 && assignmentTotal < 70)
                                {
                                    assignments.setText("C-");
                                }
                                else if (assignmentTotal > 63 && assignmentTotal < 67)
                                {
                                    assignments.setText("D+");
                                }
                                else if (assignmentTotal > 61 && assignmentTotal < 64)
                                {
                                    assignments.setText("D");
                                }
                                else if (assignmentTotal > 59 && assignmentTotal < 62)
                                {
                                    assignments.setText("D-");
                                }
                                else
                                {
                                    assignments.setText("F");
                                }
                                //Actually add counter for every tasks( for easier calculation later on)
                                // Put quality points and GPA points after you do full total of all tasks, then send them via extras
                                //for full total, check the variables in a similar way to the check/ or think of another fix
                                //They flow from 12 to 1

                            }
                            //For mid exam
                            scores.put("MidExam",gradeStorage.get("Mid Exam").toString());
                            String exam1 = scores.get("MidExam");

                            int Exam1;
                            if(!exam1.contentEquals("102"))
                            {
                                Exam1= Integer.parseInt(exam1);
                                givenMidExam = givenMidExam + 1;
                            }
                            else
                            {
                                Exam1 = 0;
                            }

                            if(givenMidExam == 0)
                            {
                                midExam.setText("Not assigned");
                            }
                            else
                            {
                                midExamTotal = Exam1;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (midExamTotal > 89 && midExamTotal < 101)
                                {
                                    midExam.setText("A");
                                }
                                else if (midExamTotal > 86 && midExamTotal < 90)
                                {
                                    midExam.setText("A-");
                                }
                                else if (midExamTotal > 83 && midExamTotal < 87)
                                {
                                    midExam.setText("B+");
                                }
                                else if (midExamTotal > 79 && midExamTotal < 84)
                                {
                                    midExam.setText("B");
                                }
                                else if (midExamTotal > 76 && midExamTotal < 80)
                                {
                                    midExam.setText("B-");
                                }
                                else if (midExamTotal > 73 && midExamTotal < 77)
                                {
                                    midExam.setText("C+");
                                }
                                else if (midExamTotal > 69 && midExamTotal < 74)
                                {
                                    midExam.setText("C");
                                }
                                else if (midExamTotal > 66 && midExamTotal < 70)
                                {
                                    midExam.setText("C-");
                                }
                                else if (midExamTotal > 63 && midExamTotal < 67)
                                {
                                    midExam.setText("D+");
                                }
                                else if (midExamTotal > 61 && midExamTotal < 64)
                                {
                                    midExam.setText("D");
                                }
                                else if (midExamTotal > 59 && midExamTotal < 62)
                                {
                                    midExam.setText("D-");
                                }
                                else
                                {
                                    midExam.setText("F");
                                }
                            }

                            //For end exam
                            scores.put("EndExam",gradeStorage.get("End Exam").toString());
                            String exam2 = scores.get("EndExam");

                            int Exam2;
                            if(!exam2.contentEquals("102"))
                            {
                                Exam2= Integer.parseInt(exam2);
                                givenEndExam = givenEndExam + 1;
                            }
                            else
                            {
                                Exam2 = 0;
                            }

                            if(givenEndExam == 0)
                            {
                                endExam.setText("Not assigned");
                            }
                            else
                            {
                                endExamTotal = Exam2;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (endExamTotal > 89 && endExamTotal < 101)
                                {
                                    endExam.setText("A");
                                }
                                else if (endExamTotal > 86 && endExamTotal < 90)
                                {
                                    endExam.setText("A-");
                                }
                                else if (endExamTotal > 83 && endExamTotal < 87)
                                {
                                    endExam.setText("B+");
                                }
                                else if (endExamTotal > 79 && endExamTotal < 84)
                                {
                                    endExam.setText("B");
                                }
                                else if (endExamTotal > 76 && endExamTotal < 80)
                                {
                                    endExam.setText("B-");
                                }
                                else if (endExamTotal > 73 && endExamTotal < 77)
                                {
                                    endExam.setText("C+");
                                }
                                else if (endExamTotal > 69 && endExamTotal < 74)
                                {
                                    endExam.setText("C");
                                }
                                else if (endExamTotal > 66 && endExamTotal < 70)
                                {
                                    endExam.setText("C-");
                                }
                                else if (endExamTotal > 63 && endExamTotal < 67)
                                {
                                    endExam.setText("D+");
                                }
                                else if (endExamTotal > 61 && endExamTotal < 64)
                                {
                                    endExam.setText("D");
                                }
                                else if (endExamTotal > 59 && endExamTotal < 62)
                                {
                                    endExam.setText("D-");
                                }
                                else
                                {
                                    endExam.setText("F");
                                }
                            }

                            //For projects
                            scores.put("Project1",gradeStorage.get("Project 1").toString());
                            scores.put("Project2",gradeStorage.get("Project 2").toString());
                            String project1 = scores.get("Project1");
                            String project2 = scores.get("Project2");


                            int Project1;
                            if(!project1.contentEquals("102"))
                            {
                                Project1= Integer.parseInt(project1);
                                givenProjects = givenProjects + 1;
                            }
                            else
                            {
                                Project1 = 0;
                            }

                            int Project2;
                            if(!project2.contentEquals("102"))
                            {
                                Project2= Integer.parseInt(project2);
                                givenProjects = givenProjects + 1;
                            }
                            else
                            {
                                Project2 = 0;
                            }

                            if(givenProjects == 0)
                            {
                                projects.setText("Not assigned");
                            }
                            else
                            {
                                projectTotal = (Project1 + Project2)/givenProjects;

                                //Add one to the total's counter since there is a valid grade here
                                allGiven = allGiven+1;

                                //Check what grade the overall assignment score falls under
                                if (projectTotal > 89 && projectTotal < 101)
                                {
                                    projects.setText("A");
                                }
                                else if (projectTotal > 86 && projectTotal < 90)
                                {
                                    projects.setText("A-");
                                }
                                else if (projectTotal > 83 && projectTotal < 87)
                                {
                                    projects.setText("B+");
                                }
                                else if (projectTotal > 79 && projectTotal < 84)
                                {
                                    projects.setText("B");
                                }
                                else if (projectTotal > 76 && projectTotal < 80)
                                {
                                    projects.setText("B-");
                                }
                                else if (projectTotal > 73 && projectTotal < 77)
                                {
                                    projects.setText("C+");
                                }
                                else if (projectTotal > 69 && projectTotal < 74)
                                {
                                    projects.setText("C");
                                }
                                else if (projectTotal > 66 && projectTotal < 70)
                                {
                                    projects.setText("C-");
                                }
                                else if (projectTotal > 63 && projectTotal < 67)
                                {
                                    projects.setText("D+");
                                }
                                else if (projectTotal > 61 && projectTotal < 64)
                                {
                                    projects.setText("D");
                                }
                                else if (projectTotal > 59 && projectTotal < 62)
                                {
                                    projects.setText("D-");
                                }
                                else
                                {
                                    projects.setText("F");
                                }
                            }

                            //Finding the overall total of all
                            grandTotal = assignmentTotal+labTotal+quizTotal+midExamTotal+endExamTotal+termPaperTotal+projectTotal;

                            if(allGiven == 0)
                            {
                                projects.setText("No tasks assigned yet");
                            }
                            else {
                                finalGrade = grandTotal / allGiven;
                                GPAcredits = 3;

                                //Check what grade the overall assignment score falls under
                                if (finalGrade > 89 && finalGrade < 101) {
                                    total.setText("A");
                                    qualityPoint = 12;
                                } else if (finalGrade > 86 && finalGrade < 90) {
                                    total.setText("A-");
                                    qualityPoint = 11;
                                } else if (finalGrade > 83 && finalGrade < 87) {
                                    total.setText("B+");
                                    qualityPoint = 10;
                                } else if (finalGrade > 79 && finalGrade < 84) {
                                    total.setText("B");
                                    qualityPoint = 9;
                                } else if (finalGrade > 76 && finalGrade < 80) {
                                    total.setText("B-");
                                    qualityPoint = 8;
                                } else if (finalGrade > 73 && finalGrade < 77) {
                                    total.setText("C+");
                                    qualityPoint = 7;
                                } else if (finalGrade > 69 && finalGrade < 74) {
                                    total.setText("C");
                                    qualityPoint = 6;
                                } else if (finalGrade > 66 && finalGrade < 70) {
                                    total.setText("C-");
                                    qualityPoint = 5;
                                } else if (finalGrade > 63 && finalGrade < 67) {
                                    total.setText("D+");
                                    qualityPoint = 4;
                                } else if (finalGrade > 61 && finalGrade < 64) {
                                    total.setText("D");
                                    qualityPoint = 3;
                                } else if (finalGrade > 59 && finalGrade < 62) {
                                    total.setText("D-");
                                    qualityPoint = 2;
                                } else {
                                    total.setText("F");
                                    qualityPoint = 1;
                                }

                                GPA = qualityPoint / GPAcredits;
                                String gpaString = String.valueOf(GPA);
                                gpaScore.setText(gpaString);

                            }
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