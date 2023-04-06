package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViewStudentScores extends AppCompatActivity {
    String databaseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_scores);
        //Hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseAuth checkAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText student = findViewById(R.id.studID);
        EditText course = findViewById(R.id.courseCode);
        course.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Button checkLab = findViewById(R.id.checkLabs);
        Button checkQuiz = findViewById(R.id.checkQuizzes);
        Button checkPaper = findViewById(R.id.checkTermPapers);
        Button checkAssignment = findViewById(R.id.checkAssignments);
        Button checkExamProject = findViewById(R.id.checkExamProject);

        checkLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = student.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A student ID is needed", Toast.LENGTH_LONG).show();
                    student.setError("Student ID is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("Course ID is required");
                    return;
                }
                else if(courseID.length()!=7)
                {
                    Toast.makeText(getApplicationContext(),"The course ID given is in the incorrect format", Toast.LENGTH_LONG).show();
                    course.setError("Course ID must have 7 characters only e.g. ABC3000");
                    return;
                }
                else
                {
                    databaseName = studentID+courseID;
                    Map<String, String> users = new HashMap<>();
                    db.collection("AppUsers")
                            .document(checkAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        db.collection("CourseInformation")
                                                .document(databaseName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                Intent sendDbName = new Intent(getApplicationContext(),ViewLabScores.class);
                                                                sendDbName.putExtra(ViewLabScores.storeName,databaseName);
                                                                startActivity(sendDbName);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot view records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to view the records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            course.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

        checkQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = student.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A student ID is needed", Toast.LENGTH_LONG).show();
                    student.setError("Student ID is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("Course ID is required");
                    return;
                }
                else if(courseID.length()!=7)
                {
                    Toast.makeText(getApplicationContext(),"The course ID given is in the incorrect format", Toast.LENGTH_LONG).show();
                    course.setError("Course ID must have 7 characters only e.g. ABC3000");
                    return;
                }
                else
                {
                    databaseName = studentID+courseID;
                    Map<String, String> users = new HashMap<>();
                    db.collection("AppUsers")
                            .document(checkAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        db.collection("CourseInformation")
                                                .document(databaseName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                Intent sendDbName = new Intent(getApplicationContext(),ViewQuizScores.class);
                                                                sendDbName.putExtra(ViewQuizScores.scoreDocument,databaseName);
                                                                startActivity(sendDbName);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot view records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to view the records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            course.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

        checkPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = student.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A student ID is needed", Toast.LENGTH_LONG).show();
                    student.setError("Student ID is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("Course ID is required");
                    return;
                }
                else if(courseID.length()!=7)
                {
                    Toast.makeText(getApplicationContext(),"The course ID given is in the incorrect format", Toast.LENGTH_LONG).show();
                    course.setError("Course ID must have 7 characters only e.g. ABC3000");
                    return;
                }
                else
                {
                    databaseName = studentID+courseID;
                    Map<String, String> users = new HashMap<>();
                    db.collection("AppUsers")
                            .document(checkAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        db.collection("CourseInformation")
                                                .document(databaseName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                Intent sendDbName = new Intent(getApplicationContext(),ViewTermPaperScores.class);
                                                                sendDbName.putExtra(ViewTermPaperScores.dataStorage,databaseName);
                                                                startActivity(sendDbName);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot view records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to view the records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            course.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
        checkAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = student.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A student ID is needed", Toast.LENGTH_LONG).show();
                    student.setError("Student ID is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("Course ID is required");
                    return;
                }
                else if(courseID.length()!=7)
                {
                    Toast.makeText(getApplicationContext(),"The course ID given is in the incorrect format", Toast.LENGTH_LONG).show();
                    course.setError("Course ID must have 7 characters only e.g. ABC3000");
                    return;
                }
                else
                {
                     databaseName = studentID+courseID;
                    Map<String, String> users = new HashMap<>();
                    db.collection("AppUsers")
                            .document(checkAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        db.collection("CourseInformation")
                                                .document(databaseName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                Intent sendDbName = new Intent(getApplicationContext(),ViewAssignmentScores.class);
                                                                sendDbName.putExtra(ViewAssignmentScores.store,databaseName);
                                                                startActivity(sendDbName);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot view records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to view the records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            course.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

        checkExamProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = student.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(studentID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A student ID is needed", Toast.LENGTH_LONG).show();
                    student.setError("Student ID is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"A course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("Course ID is required");
                    return;
                }
                else if(courseID.length()!=7)
                {
                    Toast.makeText(getApplicationContext(),"The course ID given is in the incorrect format", Toast.LENGTH_LONG).show();
                    course.setError("Course ID must have 7 characters only e.g. ABC3000");
                    return;
                }
                else
                {
                    databaseName = studentID+courseID;
                    Map<String, String> users = new HashMap<>();
                    db.collection("AppUsers")
                            .document(checkAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        db.collection("CourseInformation")
                                                .document(databaseName)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot userInformation = task.getResult();
                                                        if(userInformation.exists())
                                                        {
                                                            users.put("Lecturer",userInformation.get("Lecturer ID").toString());
                                                            String correctID = users.get("Lecturer");

                                                            if(lecturer.contentEquals(correctID))
                                                            {
                                                                Intent sendDbName = new Intent(getApplicationContext(),ViewExamProjectScores.class);
                                                                sendDbName.putExtra(ViewExamProjectScores.documentName,databaseName);
                                                                startActivity(sendDbName);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot view records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to view the records for courses you do not teach");
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(),"Error: Record for specified course and student not found",Toast.LENGTH_LONG).show();
                                                            course.setError("Could not find an existing record for the specified course for this student");
                                                            return;
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

    }
}