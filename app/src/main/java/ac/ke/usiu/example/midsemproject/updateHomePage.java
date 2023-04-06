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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class updateHomePage extends AppCompatActivity {
    String storageName;
    FirebaseAuth firestoreAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_home_page);
        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        EditText studentID = findViewById(R.id.studentId);
        EditText course = findViewById(R.id.courseId);
        course.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        FirebaseFirestore myDatabase = FirebaseFirestore.getInstance();
        firestoreAuth= FirebaseAuth.getInstance();
        Button updateLabScore = findViewById(R.id.labUpdate);
        Button updateQuizScore = findViewById(R.id.quizUpdate);
        Button updateTermPaperScore = findViewById(R.id.termPaperUpdate);
        Button updateAssignmentScore = findViewById(R.id.assignmentUpdate);
        Button updateExamProject = findViewById(R.id.examProjectUpdate);

        updateLabScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student = studentID.getText().toString().trim();
                String courseID = course.getText().toString().trim();
                if(student.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Student ID is needed", Toast.LENGTH_LONG).show();
                    studentID.setError("This field is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("This field is required");
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
                    storageName = student + courseID;
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firestoreAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(storageName)
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
                                                                Intent send = new Intent(getApplicationContext(), labUpdate.class);
                                                                send.putExtra(labUpdate.storage,storageName);
                                                                startActivity(send);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot update records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to update records for courses you do not teach");
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

        updateQuizScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student = studentID.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(student.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Student ID is needed", Toast.LENGTH_LONG).show();
                    studentID.setError("This field is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("This field is required");
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
                    storageName = student + courseID;
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firestoreAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(storageName)
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
                                                                Intent sendScore = new Intent(getApplicationContext(), quizUpdate.class);
                                                                sendScore.putExtra(quizUpdate.scoreStorage,storageName);
                                                                startActivity(sendScore);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot update records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to update records for courses you do not teach");
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

        updateTermPaperScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student = studentID.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(student.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Student ID is needed", Toast.LENGTH_LONG).show();
                    studentID.setError("This field is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("This field is required");
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
                    storageName = student + courseID;
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firestoreAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(storageName)
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
                                                                Intent send = new Intent(getApplicationContext(), termPaperUpdate.class);
                                                                send.putExtra(termPaperUpdate.data,storageName);
                                                                startActivity(send);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot update records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to update records for courses you do not teach");
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
        updateAssignmentScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student = studentID.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(student.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Student ID is needed", Toast.LENGTH_LONG).show();
                    studentID.setError("This field is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("This field is required");
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
                    storageName = student + courseID;
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firestoreAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(storageName)
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
                                                                Intent sendAssignment = new Intent(getApplicationContext(), assignmentUpdate.class);
                                                                sendAssignment.putExtra(assignmentUpdate.database,storageName);
                                                                startActivity(sendAssignment);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot update records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to update records for courses you do not teach");
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
        updateExamProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student = studentID.getText().toString().trim();
                String courseID = course.getText().toString().trim();

                if(student.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Student ID is needed", Toast.LENGTH_LONG).show();
                    studentID.setError("This field is required");
                    return;
                }
                else if(courseID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Course ID is needed", Toast.LENGTH_LONG).show();
                    course.setError("This field is required");
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
                    storageName = student + courseID;
                    Map<String, String> users = new HashMap<>();
                    myDatabase.collection("AppUsers")
                            .document(firestoreAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot lec = task.getResult();
                                    if(lec.exists())
                                    {
                                        users.put("LecID",lec.get("ID").toString());
                                        String lecturer = users.get("LecID");

                                        myDatabase.collection("CourseInformation")
                                                .document(storageName)
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
                                                                Intent send = new Intent(getApplicationContext(), examProjectUpdate.class);
                                                                send.putExtra(examProjectUpdate.dbName,storageName);
                                                                startActivity(send);
                                                            }
                                                            else if(!lecturer.contentEquals(correctID))
                                                            {
                                                                Toast.makeText(getApplicationContext(),"You cannot update records for a course you do not teach",Toast.LENGTH_LONG).show();
                                                                course.setError("You are not allowed to update records for courses you do not teach");
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